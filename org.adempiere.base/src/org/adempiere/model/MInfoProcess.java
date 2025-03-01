/******************************************************************************
 * Product: iDempiere ERP & CRM Smart Business Solution                       *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/

package org.adempiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.model.MInfoColumn;
import org.compiere.model.MProcess;
import org.compiere.model.X_AD_InfoProcess;
import org.compiere.util.DefaultEvaluatee;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluator;
import org.idempiere.cache.ImmutablePOSupport;

/**
 * Contain details of process in info window, 
 * including ad_process_id, image name and name.
 * @author hieplq
 */
public class MInfoProcess extends X_AD_InfoProcess implements IInfoColumn, ImmutablePOSupport {
	
	/**
	 * generated serial id
	 */
	private static final long serialVersionUID = 7833442401205258074L;

    /**
     * UUID based Constructor
     * @param ctx  Context
     * @param AD_InfoProcess_UU  UUID key
     * @param trxName Transaction
     */
    public MInfoProcess(Properties ctx, String AD_InfoProcess_UU, String trxName) {
        super(ctx, AD_InfoProcess_UU, trxName);
    }

	/**
	 * @param ctx
	 * @param AD_InfoProcess_ID
	 * @param trxName
	 */
	public MInfoProcess(Properties ctx, int AD_InfoProcess_ID, String trxName) {
		super(ctx, AD_InfoProcess_ID, trxName);

	}

	/**
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MInfoProcess(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);

	}
	
	/**
	 * Copy constructor
	 * @param copy
	 */
	public MInfoProcess(MInfoProcess copy) {
		this(Env.getCtx(), 0, (String)null);
		copyPO(copy);
		this.m_viewIDName = copy.m_viewIDName;
	}
	
	protected String m_viewIDName;
	
	/**
	 *	Is the Column Visible ? Evaluated based on display logic expression and context of this PO.
	 *  @return true, if visible
	 */
	public boolean isDisplayed (final int windowNo)
	{
		return isDisplayed(this.getCtx(), windowNo);
}
	
	 /**
	 * Is the Column Visible ? Evaluated based on display logic expression and context of this PO.
	 * @param ctx
	 * @return true, if visible
	 */
	public boolean isDisplayed(final Properties ctx, final int windowNo) {		
		if (getDisplayLogic() == null || getDisplayLogic().trim().length() == 0)
			return true;
		
		Evaluatee evaluatee = (variableName) -> {
			DefaultEvaluatee de = new DefaultEvaluatee(null, windowNo, -1, true);
			return de.get_ValueAsString(ctx, variableName);
		};
		
		boolean retValue = Evaluator.evaluateLogic(evaluatee, getDisplayLogic());
		if (log.isLoggable(Level.FINEST)) log.finest(MProcess.get(getCtx(), getAD_Process_ID()).getName() 
					+ " (" + getDisplayLogic() + ") => " + retValue);
		return retValue;
	}

	/**
	 * just forward call to {@link #getAD_InfoColumn_ID()}
	 */
	@Override
	public int getInfoColumnID() {
		return getAD_InfoColumn_ID();
	}

	/**
	 * Just forward call to {@link #getAD_InfoColumn ()}
	 */
	@Override
	public MInfoColumn getAD_InfoColumn (){
		return (MInfoColumn)super.getAD_InfoColumn();
	}

	@Override
	public MInfoProcess markImmutable() {
		if (is_Immutable())
			return this;
		
		makeImmutable();
		
		return this;
	}
	
}
