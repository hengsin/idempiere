/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.print.layout;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.base.Core;
import org.compiere.model.AttachmentData;
import org.compiere.model.MAttachment;
import org.compiere.model.MImage;
import org.compiere.print.MPrintFormatItem;
import org.compiere.print.PrintDataElement;
import org.compiere.util.CCache;
import org.compiere.util.Env;

/**
 *	Image Element
 *
 * 	@author 	Jorg Janke
 * 	@version 	$Id: ImageElement.java,v 1.3 2006/07/30 00:53:02 jjanke Exp $
 */
public class ImageElement extends PrintElement
{	
	/**
	 * generated serial id
	 */
	private static final long serialVersionUID = 905615948952506059L;

	/**
	 *	Create Image element from URL
	 *	@param imageURLString image url
	 *	@return image element
	 */
	public static ImageElement get (String imageURLString)
	{
		if (imageURLString == null)
			return null;
		Object key = imageURLString;
		ImageElement image = (ImageElement)s_cache.get(key);
		if (image == null)
		{
			image = new ImageElement(imageURLString);
			s_cache.put(key, image);
		}
		return new ImageElement(image.getImage());
	}	//	get
	
	/**
	 *	Create Image element from URL
	 *  @param imageURL image url
	 *	@return image element
	 */
	public static ImageElement get (URL imageURL)
	{
		Object key = imageURL;
		ImageElement image = (ImageElement)s_cache.get(key);
		if (image == null)
		{
			image = new ImageElement(imageURL);
			s_cache.put(key, image);
		}
		return new ImageElement(image.getImage());
	}	//	get
	
	/**
	 *	Create Image element from Attachment of a print format item record
	 * 	@param AD_PrintFormatItem_ID record id
	 *	@return image element
	 */
	public static ImageElement get (int AD_PrintFormatItem_ID)
	{
		Object key = Integer.valueOf(AD_PrintFormatItem_ID);
		ImageElement image = (ImageElement)s_cache.get(key);
		if (image == null)
		{
			image = new ImageElement(AD_PrintFormatItem_ID);
			s_cache.put(key, image);
		}
		return new ImageElement(image.getImage());
	}	//	get

	/**
	 *	Create Image element from AD_Image
	 *	@param data the PrintDataElement, containing the reference to AD_Image
	 *	@param imageURLString ignore
	 *	@return image element
	 */
	public static ImageElement get(PrintDataElement data, String imageURLString)
	{
		BigDecimal imkeybd = null;
		if (data.getValue() instanceof Integer)
			imkeybd = BigDecimal.valueOf((Integer) data.getValue());
		else
			imkeybd = (BigDecimal) data.getValue();
		ImageElement image = (ImageElement)s_cache.get(imkeybd);
		if (image == null)
		{
			int imkeyint = 0;
			if (imkeybd != null)
				imkeyint = imkeybd.intValue();
			image = new ImageElement(imkeyint, false);
			s_cache.put(imkeybd, image);
		}
		return new ImageElement(image.getImage());
	}	//	get
	
	/**	10 minute Cache						*/
	private static CCache<Object,ImageElement>	s_cache 
		= new CCache<Object,ImageElement>(null, "ImageElement", 10, 10, false);
	
	/**
	 *	Create from existing Image
	 *  @param image image
	 */
	public ImageElement(Image image)
	{
		m_image = image;
		if (m_image != null) {
			if (log.isLoggable(Level.FINE)) log.fine("Image=" + image);
		} else {
			log.log(Level.WARNING, "Image is NULL");
		}
	}	//	ImageElement

	/**
	 *	Create Image element from URL
	 *	@param imageURLstring image url
	 */
	private ImageElement(String imageURLstring)
	{
		if (MAttachment.isAttachmentURLPath(imageURLstring))
		{
			AttachmentData imageData = MAttachment.getDataFromAttachmentURLPath(imageURLstring);
			if (imageData != null && imageData.data() != null)
				m_image = Toolkit.getDefaultToolkit().createImage(imageData.data());
		}
		else
		{
			URL imageURL = getURL(imageURLstring);
			if (imageURL != null)
			{
				m_image = Toolkit.getDefaultToolkit().createImage(imageURL);
				if (m_image != null) {
					if (log.isLoggable(Level.FINE)) log.fine("URL=" + imageURL);
				} else {
					log.log(Level.WARNING, "Not loaded - URL=" + imageURL);
				}
			}
			else
				log.log(Level.WARNING, "Invalid URL=" + imageURLstring);
		}
	}	//	ImageElement

	/**
	 *	Create Image element from URL
	 *  @param imageURL image url
	 */
	private ImageElement(URL imageURL)
	{
		if (imageURL != null)
		{
			m_image = Toolkit.getDefaultToolkit().createImage(imageURL);
			if (m_image != null) {
				if (log.isLoggable(Level.FINE)) log.fine("URL=" + imageURL);
			} else {
				log.log(Level.WARNING, "Not loaded - URL=" + imageURL);
			}
		}
		else
			log.severe ("ImageURL is NULL");
	}	//	ImageElement

	/**
	 *	Create Image element from Attachment of a print format item record
	 * 	@param AD_PrintFormatItem_ID record id
	 */
	private ImageElement(int AD_PrintFormatItem_ID)
	{
		loadAttachment(AD_PrintFormatItem_ID);
	}	//	ImageElement

	/**
	 *	Create Image element from print format item Attachment or AD_Image
	 * 	@param record_ID record id of print format item or AD_Image
	 * 	@param isAttachment flag to indicate if is print format item attachment or is AD_Image
	 */
	public ImageElement(int record_ID, boolean isAttachment)
	{
		if (isAttachment)
			loadAttachment(record_ID);
		else
			loadFromDB(record_ID);
	}	//	ImageElement

	/**	The Image			*/
	private Image	m_image = null;
	/** Scale				*/
	private double	m_scaleFactor = 1;

	/**
	 * 	Get URL from String
	 *  @param urlString url of resource
	 *  @return URL or null
	 */
	private URL getURL (String urlString)
	{
		URL url = null;
		if (urlString == null)
			return null;
		//	not a URL - may be a resource
		if (urlString.indexOf("://") == -1)
		{
			ClassLoader cl = getClass().getClassLoader();
			url = cl.getResource(urlString);
			if (url != null)
				return url;
			url = Core.getResourceFinder().getResource(urlString);
			if (url != null)
				return url;
			log.log(Level.WARNING, "Not found - " + urlString);
			return null;
		}
		//	load URL
		try
		{
			url = new URL (urlString);
		}
		catch (MalformedURLException ex)
		{
			log.log(Level.WARNING, urlString, ex);
		}
		return url;
	}	//	getURL;

	/**
	 * 	Load from AD_Image
	 * 	@param record_ID record id
	 */
	private void loadFromDB(int record_ID)
	{
		MImage mimage = MImage.get(Env.getCtx(), record_ID);
		if (mimage == null)
		{
			log.log(Level.WARNING, "No Image - record_ID=" + record_ID);
			return;
		}

		byte[] imageData = mimage.getData();
		if (imageData != null)
			m_image = Toolkit.getDefaultToolkit().createImage(imageData);
		if (m_image != null) {
			if (log.isLoggable(Level.FINE)) log.fine(mimage.toString() 
				+ " - Size=" + imageData.length);
		} else {
			log.log(Level.WARNING, mimage.toString()
					+ " - not loaded (must be gif or jpg) - record_ID=" + record_ID);
		}
	}	//	loadFromDB

	
	/**
	 * 	Load Attachment (assume first attachment item is image)
	 * 	@param AD_PrintFormatItem_ID record id
	 */
	private void loadAttachment(int AD_PrintFormatItem_ID)
	{
		try (MAttachment attachment = MAttachment.get(Env.getCtx(), MPrintFormatItem.Table_ID, AD_PrintFormatItem_ID, null, null);) {
		if (attachment == null)
		{
			log.log(Level.WARNING, "No Attachment - AD_PrintFormatItem_ID=" + AD_PrintFormatItem_ID);
			return;
		}
		if (attachment.getEntryCount() != 1)
		{
			log.log(Level.WARNING, "Need just 1 Attachment Entry = " + attachment.getEntryCount());
			return;
		}
		byte[] imageData = attachment.getEntryData(0);
		if (imageData != null)
			m_image = Toolkit.getDefaultToolkit().createImage(imageData);
		if (m_image != null) {
			if (log.isLoggable(Level.FINE)) log.fine(attachment.getEntryName(0) 
				+ " - Size=" + imageData.length);
		} else {
			log.log(Level.WARNING, attachment.getEntryName(0)
					+ " - not loaded (must be gif or jpg) - AD_PrintFormatItem_ID=" + AD_PrintFormatItem_ID);
		}}
	}	//	loadAttachment

	/**
	 * 	Calculate Image Size.<br/>
	 * 	Set p_width and p_height.
	 * 	@return true if calculated
	 */
	protected boolean calculateSize()
	{
		p_width = 0;
		p_height = 0;
		if (m_image == null)
			return true;
		//	we have an image
		// if the image was not loaded, consider that there is no image - teo_sarca [ 1674706 ] 
		if (waitForLoad(m_image) && m_image != null)
		{
			p_width = m_image.getWidth(this);
			p_height = m_image.getHeight(this);
						
			if (p_width * p_height == 0)
				return true;	//	don't bother scaling and prevent div by 0

			// 0 = unlimited so scale to fit restricted dimension
			/* teo_sarca, [ 1673548 ] Image is not scaled in a report table cell
			if (p_maxWidth * p_maxHeight != 0)	// scale to maintain aspect ratio
			{
				if (p_width/p_height > p_maxWidth/p_maxHeight)
					// image "fatter" than available area
					m_scaleFactor = p_maxWidth/p_width;
				else
					m_scaleFactor = p_maxHeight/p_height;			
			}
			*/
			m_scaleFactor = 1;
			if (p_maxWidth != 0 && p_width > p_maxWidth)
				m_scaleFactor = p_maxWidth / p_width;
			if (p_maxHeight != 0 && p_height > p_maxHeight && p_maxHeight/p_height < m_scaleFactor)
				m_scaleFactor = p_maxHeight / p_height;
			
			p_width = (float) m_scaleFactor * p_width;
			p_height = (float) m_scaleFactor * p_height;
		}
		// If the image is not loaded set it to null.
		// This prevents SecurityException when invoking getWidth() or getHeight(). - teo_sarca [ 1674706 ]
		else {
			m_image = null;
		}
		
		return true;
	}	//	calculateSize

	/**
	 * 	Get the Image
	 *	@return image
	 */
	public Image getImage()
	{
		return m_image;
	}	//	getImage
	
	/**
	 * Get image scale factor.
	 * 
	 * @return scale factor
	 */
	public double getScaleFactor() {
		if (!p_sizeCalculated)
			p_sizeCalculated = calculateSize();
		return m_scaleFactor;
	}
	
	/**
	 * 	Paint Image
	 * 	@param g2D Graphics
	 *  @param pageNo page number for multi page support (0 = header/footer) - ignored
	 *  @param pageStart top left Location of page
	 *  @param ctx print context
	 *  @param isView true if online view (IDs are links)
	 */
	public void paint(Graphics2D g2D, int pageNo, Point2D pageStart, Properties ctx, boolean isView)
	{
		if (m_image == null)
			return;

		//	Position
		Point2D.Double location = getAbsoluteLocation(pageStart);
		int x = (int)location.x;
		if (MPrintFormatItem.FIELDALIGNMENTTYPE_TrailingRight.equals(p_FieldAlignmentType))
			x += p_maxWidth - p_width;
		else if (MPrintFormatItem.FIELDALIGNMENTTYPE_Center.equals(p_FieldAlignmentType))
			x += (p_maxWidth - p_width) / 2;
		int y = (int)location.y;

		// 	map a scaled and shifted version of the image to device space
		AffineTransform transform = new AffineTransform();
		transform.translate(x,y);
		transform.scale(m_scaleFactor, m_scaleFactor); 
		g2D.drawImage(m_image, transform, this);
	}	//	paint

}	//	ImageElement