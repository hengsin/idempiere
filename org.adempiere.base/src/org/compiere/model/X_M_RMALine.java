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
import java.util.Properties;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for M_RMALine
 *  @author iDempiere (generated)
 *  @version Release 13 - $Id$ */
@org.adempiere.base.Model(table="M_RMALine")
public class X_M_RMALine extends PO implements I_M_RMALine, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20250307L;

    /** Standard Constructor */
    public X_M_RMALine (Properties ctx, int M_RMALine_ID, String trxName)
    {
      super (ctx, M_RMALine_ID, trxName);
      /** if (M_RMALine_ID == 0)
        {
			setC_Tax_ID (0);
			setM_RMALine_ID (0);
			setM_RMA_ID (0);
			setProcessed (false);
			setQty (Env.ZERO);
        } */
    }

    /** Standard Constructor */
    public X_M_RMALine (Properties ctx, int M_RMALine_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, M_RMALine_ID, trxName, virtualColumns);
      /** if (M_RMALine_ID == 0)
        {
			setC_Tax_ID (0);
			setM_RMALine_ID (0);
			setM_RMA_ID (0);
			setProcessed (false);
			setQty (Env.ZERO);
        } */
    }

    /** Standard Constructor */
    public X_M_RMALine (Properties ctx, String M_RMALine_UU, String trxName)
    {
      super (ctx, M_RMALine_UU, trxName);
      /** if (M_RMALine_UU == null)
        {
			setC_Tax_ID (0);
			setM_RMALine_ID (0);
			setM_RMA_ID (0);
			setProcessed (false);
			setQty (Env.ZERO);
        } */
    }

    /** Standard Constructor */
    public X_M_RMALine (Properties ctx, String M_RMALine_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, M_RMALine_UU, trxName, virtualColumns);
      /** if (M_RMALine_UU == null)
        {
			setC_Tax_ID (0);
			setM_RMALine_ID (0);
			setM_RMA_ID (0);
			setProcessed (false);
			setQty (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_M_RMALine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_M_RMALine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Amount.
		@param Amt Amount
	*/
	public void setAmt (BigDecimal Amt)
	{
		set_Value (COLUMNNAME_Amt, Amt);
	}

	/** Get Amount.
		@return Amount
	  */
	public BigDecimal getAmt()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_C_Charge getC_Charge() throws RuntimeException
	{
		return (org.compiere.model.I_C_Charge)MTable.get(getCtx(), org.compiere.model.I_C_Charge.Table_ID)
			.getPO(getC_Charge_ID(), get_TrxName());
	}

	/** Set Charge.
		@param C_Charge_ID Additional document charges
	*/
	public void setC_Charge_ID (int C_Charge_ID)
	{
		if (C_Charge_ID < 1)
			set_Value (COLUMNNAME_C_Charge_ID, null);
		else
			set_Value (COLUMNNAME_C_Charge_ID, Integer.valueOf(C_Charge_ID));
	}

	/** Get Charge.
		@return Additional document charges
	  */
	public int getC_Charge_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Charge_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_CostCenter getC_CostCenter() throws RuntimeException
	{
		return (org.compiere.model.I_C_CostCenter)MTable.get(getCtx(), org.compiere.model.I_C_CostCenter.Table_ID)
			.getPO(getC_CostCenter_ID(), get_TrxName());
	}

	/** Set Cost Center.
		@param C_CostCenter_ID Cost Center
	*/
	public void setC_CostCenter_ID (int C_CostCenter_ID)
	{
		if (C_CostCenter_ID < 1)
			set_Value (COLUMNNAME_C_CostCenter_ID, null);
		else
			set_Value (COLUMNNAME_C_CostCenter_ID, Integer.valueOf(C_CostCenter_ID));
	}

	/** Get Cost Center.
		@return Cost Center	  */
	public int getC_CostCenter_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_CostCenter_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Department getC_Department() throws RuntimeException
	{
		return (org.compiere.model.I_C_Department)MTable.get(getCtx(), org.compiere.model.I_C_Department.Table_ID)
			.getPO(getC_Department_ID(), get_TrxName());
	}

	/** Set Department.
		@param C_Department_ID Department
	*/
	public void setC_Department_ID (int C_Department_ID)
	{
		if (C_Department_ID < 1)
			set_Value (COLUMNNAME_C_Department_ID, null);
		else
			set_Value (COLUMNNAME_C_Department_ID, Integer.valueOf(C_Department_ID));
	}

	/** Get Department.
		@return Department	  */
	public int getC_Department_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Department_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Tax getC_Tax() throws RuntimeException
	{
		return (org.compiere.model.I_C_Tax)MTable.get(getCtx(), org.compiere.model.I_C_Tax.Table_ID)
			.getPO(getC_Tax_ID(), get_TrxName());
	}

	/** Set Tax.
		@param C_Tax_ID Tax identifier
	*/
	public void setC_Tax_ID (int C_Tax_ID)
	{
		if (C_Tax_ID < 1)
			set_Value (COLUMNNAME_C_Tax_ID, null);
		else
			set_Value (COLUMNNAME_C_Tax_ID, Integer.valueOf(C_Tax_ID));
	}

	/** Get Tax.
		@return Tax identifier
	  */
	public int getC_Tax_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Tax_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Description.
		@param Description Optional short description of the record
	*/
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription()
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set Line No.
		@param Line Unique line for this document
	*/
	public void setLine (int Line)
	{
		set_Value (COLUMNNAME_Line, Integer.valueOf(Line));
	}

	/** Get Line No.
		@return Unique line for this document
	  */
	public int getLine()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Line);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Line Amount.
		@param LineNetAmt Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	*/
	public void setLineNetAmt (BigDecimal LineNetAmt)
	{
		set_Value (COLUMNNAME_LineNetAmt, LineNetAmt);
	}

	/** Get Line Amount.
		@return Line Extended Amount (Quantity * Actual Price) without Freight and Charges
	  */
	public BigDecimal getLineNetAmt()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LineNetAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_M_InOutLine getM_InOutLine() throws RuntimeException
	{
		return (org.compiere.model.I_M_InOutLine)MTable.get(getCtx(), org.compiere.model.I_M_InOutLine.Table_ID)
			.getPO(getM_InOutLine_ID(), get_TrxName());
	}

	/** Set Shipment/Receipt Line.
		@param M_InOutLine_ID Line on Shipment or Receipt document
	*/
	public void setM_InOutLine_ID (int M_InOutLine_ID)
	{
		if (M_InOutLine_ID < 1)
			set_Value (COLUMNNAME_M_InOutLine_ID, null);
		else
			set_Value (COLUMNNAME_M_InOutLine_ID, Integer.valueOf(M_InOutLine_ID));
	}

	/** Get Shipment/Receipt Line.
		@return Line on Shipment or Receipt document
	  */
	public int getM_InOutLine_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOutLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
	{
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_ID)
			.getPO(getM_Product_ID(), get_TrxName());
	}

	/** Set Product.
		@param M_Product_ID Product, Service, Item
	*/
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1)
			set_Value (COLUMNNAME_M_Product_ID, null);
		else
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set RMA Line.
		@param M_RMALine_ID Return Material Authorization Line
	*/
	public void setM_RMALine_ID (int M_RMALine_ID)
	{
		if (M_RMALine_ID < 1)
			set_ValueNoCheck (COLUMNNAME_M_RMALine_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_M_RMALine_ID, Integer.valueOf(M_RMALine_ID));
	}

	/** Get RMA Line.
		@return Return Material Authorization Line
	  */
	public int getM_RMALine_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_RMALine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set M_RMALine_UU.
		@param M_RMALine_UU M_RMALine_UU
	*/
	public void setM_RMALine_UU (String M_RMALine_UU)
	{
		set_Value (COLUMNNAME_M_RMALine_UU, M_RMALine_UU);
	}

	/** Get M_RMALine_UU.
		@return M_RMALine_UU	  */
	public String getM_RMALine_UU()
	{
		return (String)get_Value(COLUMNNAME_M_RMALine_UU);
	}

	public org.compiere.model.I_M_RMA getM_RMA() throws RuntimeException
	{
		return (org.compiere.model.I_M_RMA)MTable.get(getCtx(), org.compiere.model.I_M_RMA.Table_ID)
			.getPO(getM_RMA_ID(), get_TrxName());
	}

	/** Set RMA.
		@param M_RMA_ID Return Material Authorization
	*/
	public void setM_RMA_ID (int M_RMA_ID)
	{
		if (M_RMA_ID < 1)
			set_ValueNoCheck (COLUMNNAME_M_RMA_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_M_RMA_ID, Integer.valueOf(M_RMA_ID));
	}

	/** Get RMA.
		@return Return Material Authorization
	  */
	public int getM_RMA_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_RMA_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair()
    {
        return new KeyNamePair(get_ID(), String.valueOf(getM_RMA_ID()));
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

	/** Set Quantity.
		@param Qty Quantity
	*/
	public void setQty (BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Quantity.
		@return Quantity
	  */
	public BigDecimal getQty()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Delivered Quantity.
		@param QtyDelivered Delivered Quantity
	*/
	public void setQtyDelivered (BigDecimal QtyDelivered)
	{
		set_Value (COLUMNNAME_QtyDelivered, QtyDelivered);
	}

	/** Get Delivered Quantity.
		@return Delivered Quantity
	  */
	public BigDecimal getQtyDelivered()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyDelivered);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Quantity Invoiced.
		@param QtyInvoiced Invoiced Quantity
	*/
	public void setQtyInvoiced (BigDecimal QtyInvoiced)
	{
		set_Value (COLUMNNAME_QtyInvoiced, QtyInvoiced);
	}

	/** Get Quantity Invoiced.
		@return Invoiced Quantity
	  */
	public BigDecimal getQtyInvoiced()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyInvoiced);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_M_RMALine getRef_RMALine() throws RuntimeException
	{
		return (org.compiere.model.I_M_RMALine)MTable.get(getCtx(), org.compiere.model.I_M_RMALine.Table_ID)
			.getPO(getRef_RMALine_ID(), get_TrxName());
	}

	/** Set Referenced RMA Line.
		@param Ref_RMALine_ID Referenced RMA Line
	*/
	public void setRef_RMALine_ID (int Ref_RMALine_ID)
	{
		if (Ref_RMALine_ID < 1)
			set_Value (COLUMNNAME_Ref_RMALine_ID, null);
		else
			set_Value (COLUMNNAME_Ref_RMALine_ID, Integer.valueOf(Ref_RMALine_ID));
	}

	/** Get Referenced RMA Line.
		@return Referenced RMA Line	  */
	public int getRef_RMALine_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Ref_RMALine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}