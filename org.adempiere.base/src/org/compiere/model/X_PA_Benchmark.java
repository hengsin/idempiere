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

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.KeyNamePair;

/** Generated Model for PA_Benchmark
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="PA_Benchmark")
public class X_PA_Benchmark extends PO implements I_PA_Benchmark, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20241222L;

    /** Standard Constructor */
    public X_PA_Benchmark (Properties ctx, int PA_Benchmark_ID, String trxName)
    {
      super (ctx, PA_Benchmark_ID, trxName);
      /** if (PA_Benchmark_ID == 0)
        {
			setAccumulationType (null);
			setName (null);
			setPA_Benchmark_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_PA_Benchmark (Properties ctx, int PA_Benchmark_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, PA_Benchmark_ID, trxName, virtualColumns);
      /** if (PA_Benchmark_ID == 0)
        {
			setAccumulationType (null);
			setName (null);
			setPA_Benchmark_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_PA_Benchmark (Properties ctx, String PA_Benchmark_UU, String trxName)
    {
      super (ctx, PA_Benchmark_UU, trxName);
      /** if (PA_Benchmark_UU == null)
        {
			setAccumulationType (null);
			setName (null);
			setPA_Benchmark_ID (0);
        } */
    }

    /** Standard Constructor */
    public X_PA_Benchmark (Properties ctx, String PA_Benchmark_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, PA_Benchmark_UU, trxName, virtualColumns);
      /** if (PA_Benchmark_UU == null)
        {
			setAccumulationType (null);
			setName (null);
			setPA_Benchmark_ID (0);
        } */
    }

    /** Load Constructor */
    public X_PA_Benchmark (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 6 - System - Client
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
      StringBuilder sb = new StringBuilder ("X_PA_Benchmark[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

	/** AccumulationType AD_Reference_ID=370 */
	public static final int ACCUMULATIONTYPE_AD_Reference_ID=370;
	/** Average = A */
	public static final String ACCUMULATIONTYPE_Average = "A";
	/** Sum = S */
	public static final String ACCUMULATIONTYPE_Sum = "S";
	/** Set Accumulation Type.
		@param AccumulationType How to accumulate data on time axis
	*/
	public void setAccumulationType (String AccumulationType)
	{

		set_Value (COLUMNNAME_AccumulationType, AccumulationType);
	}

	/** Get Accumulation Type.
		@return How to accumulate data on time axis
	  */
	public String getAccumulationType()
	{
		return (String)get_Value(COLUMNNAME_AccumulationType);
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

	/** Set Comment/Help.
		@param Help Comment or Hint
	*/
	public void setHelp (String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Comment/Help.
		@return Comment or Hint
	  */
	public String getHelp()
	{
		return (String)get_Value(COLUMNNAME_Help);
	}

	/** Set Name.
		@param Name Alphanumeric identifier of the entity
	*/
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName()
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair()
    {
        return new KeyNamePair(get_ID(), getName());
    }

	/** Set Benchmark.
		@param PA_Benchmark_ID Performance Benchmark
	*/
	public void setPA_Benchmark_ID (int PA_Benchmark_ID)
	{
		if (PA_Benchmark_ID < 1)
			set_ValueNoCheck (COLUMNNAME_PA_Benchmark_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_PA_Benchmark_ID, Integer.valueOf(PA_Benchmark_ID));
	}

	/** Get Benchmark.
		@return Performance Benchmark
	  */
	public int getPA_Benchmark_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PA_Benchmark_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set PA_Benchmark_UU.
		@param PA_Benchmark_UU PA_Benchmark_UU
	*/
	public void setPA_Benchmark_UU (String PA_Benchmark_UU)
	{
		set_Value (COLUMNNAME_PA_Benchmark_UU, PA_Benchmark_UU);
	}

	/** Get PA_Benchmark_UU.
		@return PA_Benchmark_UU	  */
	public String getPA_Benchmark_UU()
	{
		return (String)get_Value(COLUMNNAME_PA_Benchmark_UU);
	}
}