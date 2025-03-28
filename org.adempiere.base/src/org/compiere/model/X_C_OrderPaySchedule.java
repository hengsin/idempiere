/******************************************************************************
 * Product: iDempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2012 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for C_OrderPaySchedule
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="C_OrderPaySchedule")
public class X_C_OrderPaySchedule extends PO implements I_C_OrderPaySchedule, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20241222L;

    /** Standard Constructor */
    public X_C_OrderPaySchedule (Properties ctx, int C_OrderPaySchedule_ID, String trxName)
    {
      super (ctx, C_OrderPaySchedule_ID, trxName);
      /** if (C_OrderPaySchedule_ID == 0)
        {
			setC_OrderPaySchedule_ID (0);
			setC_Order_ID (0);
			setDiscountAmt (Env.ZERO);
			setDiscountDate (new Timestamp( System.currentTimeMillis() ));
			setDueAmt (Env.ZERO);
			setDueDate (new Timestamp( System.currentTimeMillis() ));
			setIsValid (false);
			setProcessed (false);
        } */
    }

    /** Standard Constructor */
    public X_C_OrderPaySchedule (Properties ctx, int C_OrderPaySchedule_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, C_OrderPaySchedule_ID, trxName, virtualColumns);
      /** if (C_OrderPaySchedule_ID == 0)
        {
			setC_OrderPaySchedule_ID (0);
			setC_Order_ID (0);
			setDiscountAmt (Env.ZERO);
			setDiscountDate (new Timestamp( System.currentTimeMillis() ));
			setDueAmt (Env.ZERO);
			setDueDate (new Timestamp( System.currentTimeMillis() ));
			setIsValid (false);
			setProcessed (false);
        } */
    }

    /** Standard Constructor */
    public X_C_OrderPaySchedule (Properties ctx, String C_OrderPaySchedule_UU, String trxName)
    {
      super (ctx, C_OrderPaySchedule_UU, trxName);
      /** if (C_OrderPaySchedule_UU == null)
        {
			setC_OrderPaySchedule_ID (0);
			setC_Order_ID (0);
			setDiscountAmt (Env.ZERO);
			setDiscountDate (new Timestamp( System.currentTimeMillis() ));
			setDueAmt (Env.ZERO);
			setDueDate (new Timestamp( System.currentTimeMillis() ));
			setIsValid (false);
			setProcessed (false);
        } */
    }

    /** Standard Constructor */
    public X_C_OrderPaySchedule (Properties ctx, String C_OrderPaySchedule_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, C_OrderPaySchedule_UU, trxName, virtualColumns);
      /** if (C_OrderPaySchedule_UU == null)
        {
			setC_OrderPaySchedule_ID (0);
			setC_Order_ID (0);
			setDiscountAmt (Env.ZERO);
			setDiscountDate (new Timestamp( System.currentTimeMillis() ));
			setDueAmt (Env.ZERO);
			setDueDate (new Timestamp( System.currentTimeMillis() ));
			setIsValid (false);
			setProcessed (false);
        } */
    }

    /** Load Constructor */
    public X_C_OrderPaySchedule (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 1 - Org
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuilder sb = new StringBuilder ("X_C_OrderPaySchedule[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Order Payment Schedule.
		@param C_OrderPaySchedule_ID Order Payment Schedule
	*/
	public void setC_OrderPaySchedule_ID (int C_OrderPaySchedule_ID)
	{
		if (C_OrderPaySchedule_ID < 1)
			set_ValueNoCheck (COLUMNNAME_C_OrderPaySchedule_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_C_OrderPaySchedule_ID, Integer.valueOf(C_OrderPaySchedule_ID));
	}

	/** Get Order Payment Schedule.
		@return Order Payment Schedule	  */
	public int getC_OrderPaySchedule_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OrderPaySchedule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_OrderPaySchedule_UU.
		@param C_OrderPaySchedule_UU C_OrderPaySchedule_UU
	*/
	public void setC_OrderPaySchedule_UU (String C_OrderPaySchedule_UU)
	{
		set_Value (COLUMNNAME_C_OrderPaySchedule_UU, C_OrderPaySchedule_UU);
	}

	/** Get C_OrderPaySchedule_UU.
		@return C_OrderPaySchedule_UU	  */
	public String getC_OrderPaySchedule_UU()
	{
		return (String)get_Value(COLUMNNAME_C_OrderPaySchedule_UU);
	}

	public org.compiere.model.I_C_Order getC_Order() throws RuntimeException
	{
		return (org.compiere.model.I_C_Order)MTable.get(getCtx(), org.compiere.model.I_C_Order.Table_ID)
			.getPO(getC_Order_ID(), get_TrxName());
	}

	/** Set Order.
		@param C_Order_ID Order
	*/
	public void setC_Order_ID (int C_Order_ID)
	{
		if (C_Order_ID < 1)
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
	}

	/** Get Order.
		@return Order
	  */
	public int getC_Order_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_PaySchedule getC_PaySchedule() throws RuntimeException
	{
		return (org.compiere.model.I_C_PaySchedule)MTable.get(getCtx(), org.compiere.model.I_C_PaySchedule.Table_ID)
			.getPO(getC_PaySchedule_ID(), get_TrxName());
	}

	/** Set Payment Schedule.
		@param C_PaySchedule_ID Payment Schedule Template
	*/
	public void setC_PaySchedule_ID (int C_PaySchedule_ID)
	{
		if (C_PaySchedule_ID < 1)
			set_ValueNoCheck (COLUMNNAME_C_PaySchedule_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_C_PaySchedule_ID, Integer.valueOf(C_PaySchedule_ID));
	}

	/** Get Payment Schedule.
		@return Payment Schedule Template
	  */
	public int getC_PaySchedule_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PaySchedule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Discount Amount.
		@param DiscountAmt Calculated amount of discount
	*/
	public void setDiscountAmt (BigDecimal DiscountAmt)
	{
		set_Value (COLUMNNAME_DiscountAmt, DiscountAmt);
	}

	/** Get Discount Amount.
		@return Calculated amount of discount
	  */
	public BigDecimal getDiscountAmt()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DiscountAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Discount Date.
		@param DiscountDate Last Date for payments with discount
	*/
	public void setDiscountDate (Timestamp DiscountDate)
	{
		set_Value (COLUMNNAME_DiscountDate, DiscountDate);
	}

	/** Get Discount Date.
		@return Last Date for payments with discount
	  */
	public Timestamp getDiscountDate()
	{
		return (Timestamp)get_Value(COLUMNNAME_DiscountDate);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair()
    {
        return new KeyNamePair(get_ID(), String.valueOf(getDiscountDate()));
    }

	/** Set Amount due.
		@param DueAmt Amount of the payment due
	*/
	public void setDueAmt (BigDecimal DueAmt)
	{
		set_Value (COLUMNNAME_DueAmt, DueAmt);
	}

	/** Get Amount due.
		@return Amount of the payment due
	  */
	public BigDecimal getDueAmt()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DueAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Due Date.
		@param DueDate Date when the payment is due
	*/
	public void setDueDate (Timestamp DueDate)
	{
		set_Value (COLUMNNAME_DueDate, DueDate);
	}

	/** Get Due Date.
		@return Date when the payment is due
	  */
	public Timestamp getDueDate()
	{
		return (Timestamp)get_Value(COLUMNNAME_DueDate);
	}

	/** Set Valid.
		@param IsValid Element is valid
	*/
	public void setIsValid (boolean IsValid)
	{
		set_Value (COLUMNNAME_IsValid, Boolean.valueOf(IsValid));
	}

	/** Get Valid.
		@return Element is valid
	  */
	public boolean isValid()
	{
		Object oo = get_Value(COLUMNNAME_IsValid);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Processed.
		@param Processed The document has been processed
	*/
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed()
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Process Now.
		@param Processing Process Now
	*/
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing()
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}
}