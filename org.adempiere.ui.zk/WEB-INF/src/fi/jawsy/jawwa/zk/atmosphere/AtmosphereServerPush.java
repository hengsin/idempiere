/**
 
This software is licensed under the Apache 2 license, quoted below.

Copyright 2012 Joonas Javanainen <joonas@jawsy.fi>

Licensed under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License. You may obtain a copy of
the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
License for the specific language governing permissions and limitations under
the License.

 */
package fi.jawsy.jawwa.zk.atmosphere;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;

import org.atmosphere.cpr.AtmosphereResource;
import org.compiere.util.CLogger;
import org.zkoss.lang.Library;
import org.zkoss.zk.au.out.AuEcho;
import org.zkoss.zk.au.out.AuScript;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.DesktopUnavailableException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.impl.ExecutionCarryOver;
import org.zkoss.zk.ui.sys.DesktopCtrl;
import org.zkoss.zk.ui.sys.Scheduler;
import org.zkoss.zk.ui.sys.ServerPush;
import org.zkoss.zk.ui.util.Clients;

/**
 * ZK server push implementation based on Atmosphere.
 * Adapted from https://github.com/Gekkio/jawwa/tree/develop/zk-atmosphere version 0.3.1-SNAPSHOT 
 */
public class AtmosphereServerPush implements ServerPush {

    private static final String ATMOSPHERE_SERVER_PUSH_ECHO = "AtmosphereServerPush.Echo";

	private static final String ON_ACTIVATE_DESKTOP = "onActivateDesktop";

	/** default timeout of of 2 minutes **/
	public static final int DEFAULT_TIMEOUT = 1000 * 60 * 2;

    private final AtomicReference<Desktop> desktop = new AtomicReference<Desktop>();

    private final CLogger log = CLogger.getCLogger(getClass());
    /** asynchronous request reference as AtmosphereResource **/
    private final AtomicReference<AtmosphereResource> resource = new AtomicReference<AtmosphereResource>();
    private final int timeout;
    
    private ThreadInfo _active;
    private ExecutionCarryOver _carryOver;
    private final Object _mutex = new Object();
    private List<Schedule<Event>> schedules = new ArrayList<>();

    /**
     * default constructor
     */
    public AtmosphereServerPush() {
        String timeoutString = Library.getProperty("fi.jawsy.jawwa.zk.atmosphere.timeout");
        if (timeoutString == null || timeoutString.trim().length() == 0) {
            timeout = DEFAULT_TIMEOUT;
        } else {
            timeout = Integer.valueOf(timeoutString);
        }
    }

    @Override
    public boolean activate(long timeout) throws InterruptedException, DesktopUnavailableException {
    	final Thread curr = Thread.currentThread();
    	if (_active != null && _active.thread.equals(curr)) { //re-activate
			++_active.nActive;
			return true;
		}
    	
		final ThreadInfo info = new ThreadInfo(curr);

		EventListener<Event> task = new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				if (event.getName().equals(ON_ACTIVATE_DESKTOP))
				{
					synchronized (_mutex) {
						_carryOver = new ExecutionCarryOver(desktop.get());
						
						synchronized (info) {
							info.nActive = 1; //granted
							info.notifyAll();
						}
						
						try {
							_mutex.wait(); //wait until the server push is done
						} catch (InterruptedException ex) {
							throw UiException.Aide.wrap(ex);
						}
					}
				}
			}
		};
		
		synchronized (info) {
			Executions.schedule(desktop.get(), task, new Event(ON_ACTIVATE_DESKTOP));
			if (info.nActive == 0)
				info.wait(timeout <= 0 ? 10*60*1000: timeout);
		}
    	
    	_carryOver.carryOver();
    	_active = info;
    	
    	return true;
    }

    /**
     * release current AtmosphereResource
     * @param resource
     */
    public void clearResource(AtmosphereResource resource) {
        this.resource.compareAndSet(resource, null);
    }

    /**
     * commit/resume response for current AtmosphereResource 
     * @return true if resource is available
     * @throws IOException
     */
    private boolean commitResponse() throws IOException {    	
        AtmosphereResource resource = this.resource.getAndSet(null);
        if (resource != null) {
        	resource.resume();
        	return true;
        } 
        return false;
    }

    @Override
    public boolean deactivate(boolean stop) {
    	boolean stopped = false;
		if (_active != null && Thread.currentThread().equals(_active.thread)) {
			if (--_active.nActive <= 0) {
				if (stop)
				{
					stop();
					stopped = true;
				}

				_carryOver.cleanup();
				_carryOver = null;
				_active.nActive = 0; //just in case
				_active = null;
								
				synchronized (_mutex) {
					_mutex.notifyAll();
				}
			}
		}
		return stopped;
    }

    @Override
    public boolean isActive() {
    	return _active != null && _active.nActive > 0;
    }

    @SuppressWarnings("unchecked")
	@Override
    public void onPiggyback() {    	
    	if (Executions.getCurrent() != null && Executions.getCurrent().getAttribute(ATMOSPHERE_SERVER_PUSH_ECHO) != null) {
    		//has pending serverpush echo, wait for next execution piggyback trigger by the pending serverpush echo
    		return;
    	}
    	
    	Schedule<Event>[] pendings = null;
    	synchronized (schedules) {
    		if (!schedules.isEmpty()) {
    			pendings = schedules.toArray(new Schedule[0]);
    			schedules = new ArrayList<>();
    		}
    	}
    	if (pendings != null && pendings.length > 0) {
    		for(Schedule<Event> p : pendings) {
    			//schedule and execute in desktop's onPiggyBack listener
    			p.scheduler.schedule(p.task, p.event);
    		}
    	}    	
    }

    private static class EventListenerWrapper<T extends Event> implements EventListener<T> {
    	private EventListener<T> wrappedListener;
    	private AtomicBoolean runOnce;

    	private EventListenerWrapper(EventListener<T> wrappedListener) {
    		this.wrappedListener=wrappedListener;
    		this.runOnce = new AtomicBoolean(false);
    	}

    	@Override
    	public void onEvent(T event) throws Exception {
    		//if the wrapped event listener throws exception, the scheduled listener is not clean up
    		//this atomic boolean help prevent repeated call when that happens
    		if (!runOnce.compareAndSet(false, true))
    			return;

    		wrappedListener.onEvent(event);
    	}

    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T extends Event> void schedule(EventListener<T> task, T event,
			Scheduler<T> scheduler) {    	
    	if (log.isLoggable(Level.FINE))
    		log.fine(event.toString());
    	
    	EventListenerWrapper<T> wrapper = new EventListenerWrapper<T>(task);
    	if (Executions.getCurrent() == null) {
    		//schedule and execute in desktop's onPiggyBack listener
    		scheduler.schedule(wrapper, event);
	        try {
	        	commitResponse();
			} catch (IOException e) {
				log.log(Level.SEVERE, e.getMessage(), e);
			}
    	} else {
    		// in event listener thread, use echo to execute async
    		synchronized (schedules) {
				schedules.add(new Schedule(wrapper, event, scheduler));
			}
    		if (Executions.getCurrent().getAttribute(ATMOSPHERE_SERVER_PUSH_ECHO) == null) {
    			Executions.getCurrent().setAttribute(ATMOSPHERE_SERVER_PUSH_ECHO, Boolean.TRUE);
    			Clients.response(new AuEcho());
    		}
    	}
    
    }

	@Override
    public void start(Desktop desktop) {
        Desktop oldDesktop = this.desktop.getAndSet(desktop);
        if (oldDesktop != null) {
            log.warning("Server push already started for desktop " + desktop.getId());
            return;
        }

        if (log.isLoggable(Level.FINE))
        	log.fine("Starting server push for " + desktop);
        startClientPush(desktop);
    }

	/**
	 * start serverpush request at client side
	 * @param desktop
	 */
	private void startClientPush(Desktop desktop) {
		Clients.response("jawwa.atmosphere.serverpush", new AuScript(null, "jawwa.atmosphere.startServerPush('" + desktop.getId() + "', " + timeout
                + ");"));
	}

    @Override
    public void stop() {
        Desktop desktop = this.desktop.getAndSet(null);
        if (desktop == null) {
            log.warning("Server push hasn't been started or has already stopped");
            return;
        }

        AtmosphereResource currentResource = this.resource.getAndSet(null);
        synchronized (schedules) {
        	schedules.clear();
		}
        
        if (currentResource != null ) {
        	try {
				currentResource.close();
			} catch (IOException e) {
			}
        }
                
        if (Executions.getCurrent() != null) {
	        if (log.isLoggable(Level.FINE))
	        	log.fine("Stopping server push for " + desktop);
	        Clients.response("jawwa.atmosphere.serverpush", new AuScript(null, "jawwa.atmosphere.stopServerPush('" + desktop.getId() + "');"));        
        }
    }

    /**
     * handle asynchronous server push request (long polling request)
     * @param resource
     */
    public void onRequest(AtmosphereResource resource) {
    	if (log.isLoggable(Level.FINEST)) {
	  		log.finest(resource.transport().name());
	  	}
    	
    	DesktopCtrl desktopCtrl = (DesktopCtrl) this.desktop.get();
        if (desktopCtrl == null) {
        	log.severe("No desktop available");
            return;
        }

        //suspend request for server push event
	  	if (!resource.isSuspended()) {
	  		//browser default timeout is 2 minutes
	  		resource.suspend(5, TimeUnit.MINUTES); 
	  	}
	  	AtmosphereResource oldResource = this.resource.getAndSet(resource);
	  	if (oldResource != null) {
	  		try {
	  			if (!oldResource.isCancelled())
	  				oldResource.close();
			} catch (Throwable e) {
			}
	  	}

    }

	private static class ThreadInfo {
		private final Thread thread;
		/** # of activate() was called. */
		private int nActive;
		private ThreadInfo(Thread thread) {
			this.thread = thread;
		}
		public String toString() {
			return "[" + thread + ',' + nActive + ']';
		}
	}

	@Override
	public void resume() {
		if (desktop == null || desktop.get() == null) {
			throw new IllegalStateException(
					"ServerPush cannot be resumed without desktop, or has been stopped!call #start(desktop)} instead");
		}
		startClientPush(desktop.get());
	}
	
	/**
	 * 
	 * @return true if it is holding an atmosphere resource
	 */
	public boolean hasAtmosphereResource() {
		return this.resource.get() != null;
	}
	
	private class Schedule<T extends Event> {
    	private EventListener<T> task;
		private T event;
		private Scheduler<T> scheduler;

		private Schedule(EventListener<T> task, T event, Scheduler<T> scheduler) {
    		this.task = task;
    		this.event = event;
    		this.scheduler = scheduler;
    	}
    }
}
