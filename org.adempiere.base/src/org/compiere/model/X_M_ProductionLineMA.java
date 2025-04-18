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
import org.compiere.util.ValueNamePair;

/** Generated Model for M_ProductionLineMA
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="M_ProductionLineMA")
public class X_M_ProductionLineMA extends PO implements I_M_ProductionLineMA, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20241222L;

    /** Standard Constructor */
    public X_M_ProductionLineMA (Properties ctx, int M_ProductionLineMA_ID, String trxName)
    {
      super (ctx, M_ProductionLineMA_ID, trxName);
      /** if (M_ProductionLineMA_ID == 0)
        {
			setM_AttributeSetInstance_ID (0);
			setM_ProductionLine_ID (0);
			setMovementQty (Env.ZERO);
        } */
    }

    /** Standard Constructor */
    public X_M_ProductionLineMA (Properties ctx, int M_ProductionLineMA_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, M_ProductionLineMA_ID, trxName, virtualColumns);
      /** if (M_ProductionLineMA_ID == 0)
        {
			setM_AttributeSetInstance_ID (0);
			setM_ProductionLine_ID (0);
			setMovementQty (Env.ZERO);
        } */
    }

    /** Standard Constructor */
    public X_M_ProductionLineMA (Properties ctx, String M_ProductionLineMA_UU, String trxName)
    {
      super (ctx, M_ProductionLineMA_UU, trxName);
      /** if (M_ProductionLineMA_UU == null)
        {
			setM_AttributeSetInstance_ID (0);
			setM_ProductionLine_ID (0);
			setMovementQty (Env.ZERO);
        } */
    }

    /** Standard Constructor */
    public X_M_ProductionLineMA (Properties ctx, String M_ProductionLineMA_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, M_ProductionLineMA_UU, trxName, virtualColumns);
      /** if (M_ProductionLineMA_UU == null)
        {
			setM_AttributeSetInstance_ID (0);
			setM_ProductionLine_ID (0);
			setMovementQty (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_M_ProductionLineMA (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_M_ProductionLineMA[")
        .append(get_UUID()).append("]");
      return sb.toString();
    }

	/** Set Date  Material Policy.
		@param DateMaterialPolicy Time used for LIFO and FIFO Material Policy
	*/
	public void setDateMaterialPolicy (Timestamp DateMaterialPolicy)
	{
		set_ValueNoCheck (COLUMNNAME_DateMaterialPolicy, DateMaterialPolicy);
	}

	/** Get Date  Material Policy.
		@return Time used for LIFO and FIFO Material Policy
	  */
	public Timestamp getDateMaterialPolicy()
	{
		return (Timestamp)get_Value(COLUMNNAME_DateMaterialPolicy);
	}

	public I_M_AttributeSetInstance getM_AttributeSetInstance() throws RuntimeException
	{
		return (I_M_AttributeSetInstance)MTable.get(getCtx(), I_M_AttributeSetInstance.Table_ID)
			.getPO(getM_AttributeSetInstance_ID(), get_TrxName());
	}

	/** Set Attribute Set Instance.
		@param M_AttributeSetInstance_ID Product Attribute Set Instance
	*/
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0)
			set_ValueNoCheck (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
	}

	/** Get Attribute Set Instance.
		@return Product Attribute Set Instance
	  */
	public int getM_AttributeSetInstance_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_AttributeSetInstance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set M_ProductionLineMA_UU.
		@param M_ProductionLineMA_UU M_ProductionLineMA_UU
	*/
	public void setM_ProductionLineMA_UU (String M_ProductionLineMA_UU)
	{
		set_Value (COLUMNNAME_M_ProductionLineMA_UU, M_ProductionLineMA_UU);
	}

	/** Get M_ProductionLineMA_UU.
		@return M_ProductionLineMA_UU	  */
	public String getM_ProductionLineMA_UU()
	{
		return (String)get_Value(COLUMNNAME_M_ProductionLineMA_UU);
	}

	public org.compiere.model.I_M_ProductionLine getM_ProductionLine() throws RuntimeException
	{
		return (org.compiere.model.I_M_ProductionLine)MTable.get(getCtx(), org.compiere.model.I_M_ProductionLine.Table_ID)
			.getPO(getM_ProductionLine_ID(), get_TrxName());
	}

	/** Set Production Line.
		@param M_ProductionLine_ID Document Line representing a production
	*/
	public void setM_ProductionLine_ID (int M_ProductionLine_ID)
	{
		if (M_ProductionLine_ID < 1)
			set_ValueNoCheck (COLUMNNAME_M_ProductionLine_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_M_ProductionLine_ID, Integer.valueOf(M_ProductionLine_ID));
	}

	/** Get Production Line.
		@return Document Line representing a production
	  */
	public int getM_ProductionLine_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ProductionLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record UU/ColumnName
        @return UU/ColumnName pair
      */
    public ValueNamePair getValueNamePair()
    {
        return new ValueNamePair(get_UUID(), String.valueOf(getM_ProductionLine_ID()));
    }

	/** Set Movement Quantity.
		@param MovementQty Quantity of a product moved.
	*/
	public void setMovementQty (BigDecimal MovementQty)
	{
		set_Value (COLUMNNAME_MovementQty, MovementQty);
	}

	/** Get Movement Quantity.
		@return Quantity of a product moved.
	  */
	public BigDecimal getMovementQty()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MovementQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}