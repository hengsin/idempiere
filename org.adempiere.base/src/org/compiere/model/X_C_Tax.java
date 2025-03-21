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

/** Generated Model for C_Tax
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="C_Tax")
public class X_C_Tax extends PO implements I_C_Tax, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20241222L;

    /** Standard Constructor */
    public X_C_Tax (Properties ctx, int C_Tax_ID, String trxName)
    {
      super (ctx, C_Tax_ID, trxName);
      /** if (C_Tax_ID == 0)
        {
			setC_TaxCategory_ID (0);
			setC_Tax_ID (0);
			setIsDefault (false);
			setIsDocumentLevel (false);
			setIsSalesTax (false);
// N
			setIsSummary (false);
			setIsTaxExempt (false);
			setName (null);
			setRate (Env.ZERO);
			setRequiresTaxCertificate (false);
			setSOPOType (null);
// B
			setValidFrom (new Timestamp( System.currentTimeMillis() ));
        } */
    }

    /** Standard Constructor */
    public X_C_Tax (Properties ctx, int C_Tax_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, C_Tax_ID, trxName, virtualColumns);
      /** if (C_Tax_ID == 0)
        {
			setC_TaxCategory_ID (0);
			setC_Tax_ID (0);
			setIsDefault (false);
			setIsDocumentLevel (false);
			setIsSalesTax (false);
// N
			setIsSummary (false);
			setIsTaxExempt (false);
			setName (null);
			setRate (Env.ZERO);
			setRequiresTaxCertificate (false);
			setSOPOType (null);
// B
			setValidFrom (new Timestamp( System.currentTimeMillis() ));
        } */
    }

    /** Standard Constructor */
    public X_C_Tax (Properties ctx, String C_Tax_UU, String trxName)
    {
      super (ctx, C_Tax_UU, trxName);
      /** if (C_Tax_UU == null)
        {
			setC_TaxCategory_ID (0);
			setC_Tax_ID (0);
			setIsDefault (false);
			setIsDocumentLevel (false);
			setIsSalesTax (false);
// N
			setIsSummary (false);
			setIsTaxExempt (false);
			setName (null);
			setRate (Env.ZERO);
			setRequiresTaxCertificate (false);
			setSOPOType (null);
// B
			setValidFrom (new Timestamp( System.currentTimeMillis() ));
        } */
    }

    /** Standard Constructor */
    public X_C_Tax (Properties ctx, String C_Tax_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, C_Tax_UU, trxName, virtualColumns);
      /** if (C_Tax_UU == null)
        {
			setC_TaxCategory_ID (0);
			setC_Tax_ID (0);
			setIsDefault (false);
			setIsDocumentLevel (false);
			setIsSalesTax (false);
// N
			setIsSummary (false);
			setIsTaxExempt (false);
			setName (null);
			setRate (Env.ZERO);
			setRequiresTaxCertificate (false);
			setSOPOType (null);
// B
			setValidFrom (new Timestamp( System.currentTimeMillis() ));
        } */
    }

    /** Load Constructor */
    public X_C_Tax (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 2 - Client
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
      StringBuilder sb = new StringBuilder ("X_C_Tax[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_Rule getAD_Rule() throws RuntimeException
	{
		return (org.compiere.model.I_AD_Rule)MTable.get(getCtx(), org.compiere.model.I_AD_Rule.Table_ID)
			.getPO(getAD_Rule_ID(), get_TrxName());
	}

	/** Set Rule.
		@param AD_Rule_ID Rule
	*/
	public void setAD_Rule_ID (int AD_Rule_ID)
	{
		if (AD_Rule_ID < 1)
			set_Value (COLUMNNAME_AD_Rule_ID, null);
		else
			set_Value (COLUMNNAME_AD_Rule_ID, Integer.valueOf(AD_Rule_ID));
	}

	/** Get Rule.
		@return Rule	  */
	public int getAD_Rule_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Rule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_CountryGroup getC_CountryGroupFrom() throws RuntimeException
	{
		return (org.compiere.model.I_C_CountryGroup)MTable.get(getCtx(), org.compiere.model.I_C_CountryGroup.Table_ID)
			.getPO(getC_CountryGroupFrom_ID(), get_TrxName());
	}

	/** Set Country Group From.
		@param C_CountryGroupFrom_ID Country Group From
	*/
	public void setC_CountryGroupFrom_ID (int C_CountryGroupFrom_ID)
	{
		if (C_CountryGroupFrom_ID < 1)
			set_Value (COLUMNNAME_C_CountryGroupFrom_ID, null);
		else
			set_Value (COLUMNNAME_C_CountryGroupFrom_ID, Integer.valueOf(C_CountryGroupFrom_ID));
	}

	/** Get Country Group From.
		@return Country Group From	  */
	public int getC_CountryGroupFrom_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_CountryGroupFrom_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_CountryGroup getC_CountryGroupTo() throws RuntimeException
	{
		return (org.compiere.model.I_C_CountryGroup)MTable.get(getCtx(), org.compiere.model.I_C_CountryGroup.Table_ID)
			.getPO(getC_CountryGroupTo_ID(), get_TrxName());
	}

	/** Set Country Group To.
		@param C_CountryGroupTo_ID Country Group To
	*/
	public void setC_CountryGroupTo_ID (int C_CountryGroupTo_ID)
	{
		if (C_CountryGroupTo_ID < 1)
			set_Value (COLUMNNAME_C_CountryGroupTo_ID, null);
		else
			set_Value (COLUMNNAME_C_CountryGroupTo_ID, Integer.valueOf(C_CountryGroupTo_ID));
	}

	/** Get Country Group To.
		@return Country Group To	  */
	public int getC_CountryGroupTo_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_CountryGroupTo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Country.
		@param C_Country_ID Country 
	*/
	public void setC_Country_ID (int C_Country_ID)
	{
		if (C_Country_ID < 1)
			set_Value (COLUMNNAME_C_Country_ID, null);
		else
			set_Value (COLUMNNAME_C_Country_ID, Integer.valueOf(C_Country_ID));
	}

	/** Get Country.
		@return Country 
	  */
	public int getC_Country_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Country_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Region getC_Region() throws RuntimeException
	{
		return (org.compiere.model.I_C_Region)MTable.get(getCtx(), org.compiere.model.I_C_Region.Table_ID)
			.getPO(getC_Region_ID(), get_TrxName());
	}

	/** Set Region.
		@param C_Region_ID Identifies a geographical Region
	*/
	public void setC_Region_ID (int C_Region_ID)
	{
		if (C_Region_ID < 1)
			set_Value (COLUMNNAME_C_Region_ID, null);
		else
			set_Value (COLUMNNAME_C_Region_ID, Integer.valueOf(C_Region_ID));
	}

	/** Get Region.
		@return Identifies a geographical Region
	  */
	public int getC_Region_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Region_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_TaxCategory getC_TaxCategory() throws RuntimeException
	{
		return (org.compiere.model.I_C_TaxCategory)MTable.get(getCtx(), org.compiere.model.I_C_TaxCategory.Table_ID)
			.getPO(getC_TaxCategory_ID(), get_TrxName());
	}

	/** Set Tax Category.
		@param C_TaxCategory_ID Tax Category
	*/
	public void setC_TaxCategory_ID (int C_TaxCategory_ID)
	{
		if (C_TaxCategory_ID < 1)
			set_Value (COLUMNNAME_C_TaxCategory_ID, null);
		else
			set_Value (COLUMNNAME_C_TaxCategory_ID, Integer.valueOf(C_TaxCategory_ID));
	}

	/** Get Tax Category.
		@return Tax Category
	  */
	public int getC_TaxCategory_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_TaxCategory_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_TaxProvider getC_TaxProvider() throws RuntimeException
	{
		return (org.compiere.model.I_C_TaxProvider)MTable.get(getCtx(), org.compiere.model.I_C_TaxProvider.Table_ID)
			.getPO(getC_TaxProvider_ID(), get_TrxName());
	}

	/** Set Tax Provider.
		@param C_TaxProvider_ID Tax Provider
	*/
	public void setC_TaxProvider_ID (int C_TaxProvider_ID)
	{
		if (C_TaxProvider_ID < 1)
			set_Value (COLUMNNAME_C_TaxProvider_ID, null);
		else
			set_Value (COLUMNNAME_C_TaxProvider_ID, Integer.valueOf(C_TaxProvider_ID));
	}

	/** Get Tax Provider.
		@return Tax Provider	  */
	public int getC_TaxProvider_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_TaxProvider_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Tax.
		@param C_Tax_ID Tax identifier
	*/
	public void setC_Tax_ID (int C_Tax_ID)
	{
		if (C_Tax_ID < 1)
			set_ValueNoCheck (COLUMNNAME_C_Tax_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_C_Tax_ID, Integer.valueOf(C_Tax_ID));
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

	/** Set C_Tax_UU.
		@param C_Tax_UU C_Tax_UU
	*/
	public void setC_Tax_UU (String C_Tax_UU)
	{
		set_Value (COLUMNNAME_C_Tax_UU, C_Tax_UU);
	}

	/** Get C_Tax_UU.
		@return C_Tax_UU	  */
	public String getC_Tax_UU()
	{
		return (String)get_Value(COLUMNNAME_C_Tax_UU);
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

	/** Set Default.
		@param IsDefault Default value
	*/
	public void setIsDefault (boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, Boolean.valueOf(IsDefault));
	}

	/** Get Default.
		@return Default value
	  */
	public boolean isDefault()
	{
		Object oo = get_Value(COLUMNNAME_IsDefault);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Document Level.
		@param IsDocumentLevel Tax is calculated on document level (rather than line by line)
	*/
	public void setIsDocumentLevel (boolean IsDocumentLevel)
	{
		set_Value (COLUMNNAME_IsDocumentLevel, Boolean.valueOf(IsDocumentLevel));
	}

	/** Get Document Level.
		@return Tax is calculated on document level (rather than line by line)
	  */
	public boolean isDocumentLevel()
	{
		Object oo = get_Value(COLUMNNAME_IsDocumentLevel);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Sales Tax.
		@param IsSalesTax This is a sales tax (i.e. not a value added tax)
	*/
	public void setIsSalesTax (boolean IsSalesTax)
	{
		set_Value (COLUMNNAME_IsSalesTax, Boolean.valueOf(IsSalesTax));
	}

	/** Get Sales Tax.
		@return This is a sales tax (i.e. not a value added tax)
	  */
	public boolean isSalesTax()
	{
		Object oo = get_Value(COLUMNNAME_IsSalesTax);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Summary Level.
		@param IsSummary This is a summary entity
	*/
	public void setIsSummary (boolean IsSummary)
	{
		set_Value (COLUMNNAME_IsSummary, Boolean.valueOf(IsSummary));
	}

	/** Get Summary Level.
		@return This is a summary entity
	  */
	public boolean isSummary()
	{
		Object oo = get_Value(COLUMNNAME_IsSummary);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set SO Tax exempt.
		@param IsTaxExempt Business partner is exempt from tax on sales
	*/
	public void setIsTaxExempt (boolean IsTaxExempt)
	{
		set_Value (COLUMNNAME_IsTaxExempt, Boolean.valueOf(IsTaxExempt));
	}

	/** Get SO Tax exempt.
		@return Business partner is exempt from tax on sales
	  */
	public boolean isTaxExempt()
	{
		Object oo = get_Value(COLUMNNAME_IsTaxExempt);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
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

	public org.compiere.model.I_C_Tax getParent_Tax() throws RuntimeException
	{
		return (org.compiere.model.I_C_Tax)MTable.get(getCtx(), org.compiere.model.I_C_Tax.Table_ID)
			.getPO(getParent_Tax_ID(), get_TrxName());
	}

	/** Set Parent Tax.
		@param Parent_Tax_ID Parent Tax indicates a tax that is made up of multiple taxes
	*/
	public void setParent_Tax_ID (int Parent_Tax_ID)
	{
		if (Parent_Tax_ID < 1)
			set_Value (COLUMNNAME_Parent_Tax_ID, null);
		else
			set_Value (COLUMNNAME_Parent_Tax_ID, Integer.valueOf(Parent_Tax_ID));
	}

	/** Get Parent Tax.
		@return Parent Tax indicates a tax that is made up of multiple taxes
	  */
	public int getParent_Tax_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Parent_Tax_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Rate.
		@param Rate Rate or Tax or Exchange
	*/
	public void setRate (BigDecimal Rate)
	{
		set_Value (COLUMNNAME_Rate, Rate);
	}

	/** Get Rate.
		@return Rate or Tax or Exchange
	  */
	public BigDecimal getRate()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Rate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Requires Tax Certificate.
		@param RequiresTaxCertificate This tax rate requires the Business Partner to be tax exempt
	*/
	public void setRequiresTaxCertificate (boolean RequiresTaxCertificate)
	{
		set_Value (COLUMNNAME_RequiresTaxCertificate, Boolean.valueOf(RequiresTaxCertificate));
	}

	/** Get Requires Tax Certificate.
		@return This tax rate requires the Business Partner to be tax exempt
	  */
	public boolean isRequiresTaxCertificate()
	{
		Object oo = get_Value(COLUMNNAME_RequiresTaxCertificate);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** SOPOType AD_Reference_ID=287 */
	public static final int SOPOTYPE_AD_Reference_ID=287;
	/** Both = B */
	public static final String SOPOTYPE_Both = "B";
	/** Purchase Tax = P */
	public static final String SOPOTYPE_PurchaseTax = "P";
	/** Sales Tax = S */
	public static final String SOPOTYPE_SalesTax = "S";
	/** Set SO/PO Type.
		@param SOPOType Sales Tax applies to sales situations, Purchase Tax to purchase situations
	*/
	public void setSOPOType (String SOPOType)
	{

		set_Value (COLUMNNAME_SOPOType, SOPOType);
	}

	/** Get SO/PO Type.
		@return Sales Tax applies to sales situations, Purchase Tax to purchase situations
	  */
	public String getSOPOType()
	{
		return (String)get_Value(COLUMNNAME_SOPOType);
	}

	/** Set Tax Indicator.
		@param TaxIndicator Short form for Tax to be printed on documents
	*/
	public void setTaxIndicator (String TaxIndicator)
	{
		set_Value (COLUMNNAME_TaxIndicator, TaxIndicator);
	}

	/** Get Tax Indicator.
		@return Short form for Tax to be printed on documents
	  */
	public String getTaxIndicator()
	{
		return (String)get_Value(COLUMNNAME_TaxIndicator);
	}

	/** TaxPostingIndicator AD_Reference_ID=200160 */
	public static final int TAXPOSTINGINDICATOR_AD_Reference_ID=200160;
	/** Separate Tax Posting = 0 */
	public static final String TAXPOSTINGINDICATOR_SeparateTaxPosting = "0";
	/** Distribute Tax with Relevant Expense = 1 */
	public static final String TAXPOSTINGINDICATOR_DistributeTaxWithRelevantExpense = "1";
	/** Set Posting Indicator.
		@param TaxPostingIndicator Type of input tax (deductible and non deductible)
	*/
	public void setTaxPostingIndicator (String TaxPostingIndicator)
	{

		set_Value (COLUMNNAME_TaxPostingIndicator, TaxPostingIndicator);
	}

	/** Get Posting Indicator.
		@return Type of input tax (deductible and non deductible)
	  */
	public String getTaxPostingIndicator()
	{
		return (String)get_Value(COLUMNNAME_TaxPostingIndicator);
	}

	/** Set To.
		@param To_Country_ID Receiving Country
	*/
	public void setTo_Country_ID (int To_Country_ID)
	{
		if (To_Country_ID < 1)
			set_Value (COLUMNNAME_To_Country_ID, null);
		else
			set_Value (COLUMNNAME_To_Country_ID, Integer.valueOf(To_Country_ID));
	}

	/** Get To.
		@return Receiving Country
	  */
	public int getTo_Country_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_To_Country_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Region getTo_Region() throws RuntimeException
	{
		return (org.compiere.model.I_C_Region)MTable.get(getCtx(), org.compiere.model.I_C_Region.Table_ID)
			.getPO(getTo_Region_ID(), get_TrxName());
	}

	/** Set To.
		@param To_Region_ID Receiving Region
	*/
	public void setTo_Region_ID (int To_Region_ID)
	{
		if (To_Region_ID < 1)
			set_Value (COLUMNNAME_To_Region_ID, null);
		else
			set_Value (COLUMNNAME_To_Region_ID, Integer.valueOf(To_Region_ID));
	}

	/** Get To.
		@return Receiving Region
	  */
	public int getTo_Region_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_To_Region_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Valid from.
		@param ValidFrom Valid from including this date (first day)
	*/
	public void setValidFrom (Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	/** Get Valid from.
		@return Valid from including this date (first day)
	  */
	public Timestamp getValidFrom()
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidFrom);
	}
}