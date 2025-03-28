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

/** Generated Model for AD_Client
 *  @author iDempiere (generated)
 *  @version Release 12 - $Id$ */
@org.adempiere.base.Model(table="AD_Client")
public class X_AD_Client extends PO implements I_AD_Client, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20241222L;

    /** Standard Constructor */
    public X_AD_Client (Properties ctx, int AD_Client_ID, String trxName)
    {
      super (ctx, AD_Client_ID, trxName);
      /** if (AD_Client_ID == 0)
        {
			setAuthenticationType (null);
// AAS
			setAutoArchive (null);
// N
			setIsMultiLingualDocument (false);
			setIsPostImmediate (false);
// N
			setIsSecureSMTP (false);
// N
			setIsSmtpAuthorization (false);
// N
			setIsUseASP (false);
// N
			setIsUseBetaFunctions (true);
// Y
			setMMPolicy (null);
// F
			setName (null);
			setValue (null);
        } */
    }

    /** Standard Constructor */
    public X_AD_Client (Properties ctx, int AD_Client_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, AD_Client_ID, trxName, virtualColumns);
      /** if (AD_Client_ID == 0)
        {
			setAuthenticationType (null);
// AAS
			setAutoArchive (null);
// N
			setIsMultiLingualDocument (false);
			setIsPostImmediate (false);
// N
			setIsSecureSMTP (false);
// N
			setIsSmtpAuthorization (false);
// N
			setIsUseASP (false);
// N
			setIsUseBetaFunctions (true);
// Y
			setMMPolicy (null);
// F
			setName (null);
			setValue (null);
        } */
    }

    /** Standard Constructor */
    public X_AD_Client (Properties ctx, String AD_Client_UU, String trxName)
    {
      super (ctx, AD_Client_UU, trxName);
      /** if (AD_Client_UU == null)
        {
			setAuthenticationType (null);
// AAS
			setAutoArchive (null);
// N
			setIsMultiLingualDocument (false);
			setIsPostImmediate (false);
// N
			setIsSecureSMTP (false);
// N
			setIsSmtpAuthorization (false);
// N
			setIsUseASP (false);
// N
			setIsUseBetaFunctions (true);
// Y
			setMMPolicy (null);
// F
			setName (null);
			setValue (null);
        } */
    }

    /** Standard Constructor */
    public X_AD_Client (Properties ctx, String AD_Client_UU, String trxName, String ... virtualColumns)
    {
      super (ctx, AD_Client_UU, trxName, virtualColumns);
      /** if (AD_Client_UU == null)
        {
			setAuthenticationType (null);
// AAS
			setAutoArchive (null);
// N
			setIsMultiLingualDocument (false);
			setIsPostImmediate (false);
// N
			setIsSecureSMTP (false);
// N
			setIsSmtpAuthorization (false);
// N
			setIsUseASP (false);
// N
			setIsUseBetaFunctions (true);
// Y
			setMMPolicy (null);
// F
			setName (null);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_AD_Client (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_AD_Client[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

	/** Set AD_Client_UU.
		@param AD_Client_UU AD_Client_UU
	*/
	public void setAD_Client_UU (String AD_Client_UU)
	{
		set_Value (COLUMNNAME_AD_Client_UU, AD_Client_UU);
	}

	/** Get AD_Client_UU.
		@return AD_Client_UU	  */
	public String getAD_Client_UU()
	{
		return (String)get_Value(COLUMNNAME_AD_Client_UU);
	}

	/** AD_Language AD_Reference_ID=327 */
	public static final int AD_LANGUAGE_AD_Reference_ID=327;
	/** Set Language.
		@param AD_Language Language for this entity
	*/
	public void setAD_Language (String AD_Language)
	{

		set_Value (COLUMNNAME_AD_Language, AD_Language);
	}

	/** Get Language.
		@return Language for this entity
	  */
	public String getAD_Language()
	{
		return (String)get_Value(COLUMNNAME_AD_Language);
	}

	public org.compiere.model.I_AD_PasswordRule getAD_PasswordRule() throws RuntimeException
	{
		return (org.compiere.model.I_AD_PasswordRule)MTable.get(getCtx(), org.compiere.model.I_AD_PasswordRule.Table_ID)
			.getPO(getAD_PasswordRule_ID(), get_TrxName());
	}

	/** Set Password Policies.
		@param AD_PasswordRule_ID Password Policies
	*/
	public void setAD_PasswordRule_ID (int AD_PasswordRule_ID)
	{
		if (AD_PasswordRule_ID < 1)
			set_Value (COLUMNNAME_AD_PasswordRule_ID, null);
		else
			set_Value (COLUMNNAME_AD_PasswordRule_ID, Integer.valueOf(AD_PasswordRule_ID));
	}

	/** Get Password Policies.
		@return Password Policies	  */
	public int getAD_PasswordRule_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PasswordRule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_AD_ReplicationStrategy getAD_ReplicationStrategy() throws RuntimeException
	{
		return (org.compiere.model.I_AD_ReplicationStrategy)MTable.get(getCtx(), org.compiere.model.I_AD_ReplicationStrategy.Table_ID)
			.getPO(getAD_ReplicationStrategy_ID(), get_TrxName());
	}

	/** Set Replication Strategy.
		@param AD_ReplicationStrategy_ID Data Replication Strategy
	*/
	public void setAD_ReplicationStrategy_ID (int AD_ReplicationStrategy_ID)
	{
		if (AD_ReplicationStrategy_ID < 1)
			set_Value (COLUMNNAME_AD_ReplicationStrategy_ID, null);
		else
			set_Value (COLUMNNAME_AD_ReplicationStrategy_ID, Integer.valueOf(AD_ReplicationStrategy_ID));
	}

	/** Get Replication Strategy.
		@return Data Replication Strategy
	  */
	public int getAD_ReplicationStrategy_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_ReplicationStrategy_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** AuthenticationType AD_Reference_ID=200239 */
	public static final int AUTHENTICATIONTYPE_AD_Reference_ID=200239;
	/** Application and SSO = AAS */
	public static final String AUTHENTICATIONTYPE_ApplicationAndSSO = "AAS";
	/** Application Only = APO */
	public static final String AUTHENTICATIONTYPE_ApplicationOnly = "APO";
	/** SSO Only = SSO */
	public static final String AUTHENTICATIONTYPE_SSOOnly = "SSO";
	/** Set Authentication Type.
		@param AuthenticationType Authentication Type
	*/
	public void setAuthenticationType (String AuthenticationType)
	{

		set_Value (COLUMNNAME_AuthenticationType, AuthenticationType);
	}

	/** Get Authentication Type.
		@return Authentication Type	  */
	public String getAuthenticationType()
	{
		return (String)get_Value(COLUMNNAME_AuthenticationType);
	}

	/** AutoArchive AD_Reference_ID=334 */
	public static final int AUTOARCHIVE_AD_Reference_ID=334;
	/** All (Reports, Documents) = 1 */
	public static final String AUTOARCHIVE_AllReportsDocuments = "1";
	/** Documents = 2 */
	public static final String AUTOARCHIVE_Documents = "2";
	/** External Documents = 3 */
	public static final String AUTOARCHIVE_ExternalDocuments = "3";
	/** None = N */
	public static final String AUTOARCHIVE_None = "N";
	/** Set Auto Archive.
		@param AutoArchive Enable and level of automatic Archive of documents
	*/
	public void setAutoArchive (String AutoArchive)
	{

		set_Value (COLUMNNAME_AutoArchive, AutoArchive);
	}

	/** Get Auto Archive.
		@return Enable and level of automatic Archive of documents
	  */
	public String getAutoArchive()
	{
		return (String)get_Value(COLUMNNAME_AutoArchive);
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

	/** Set Document Directory.
		@param DocumentDir Directory for documents from the application server
	*/
	public void setDocumentDir (String DocumentDir)
	{
		set_Value (COLUMNNAME_DocumentDir, DocumentDir);
	}

	/** Get Document Directory.
		@return Directory for documents from the application server
	  */
	public String getDocumentDir()
	{
		return (String)get_Value(COLUMNNAME_DocumentDir);
	}

	/** Set EMail Test.
		@param EMailTest Test EMail
	*/
	public void setEMailTest (String EMailTest)
	{
		set_Value (COLUMNNAME_EMailTest, EMailTest);
	}

	/** Get EMail Test.
		@return Test EMail
	  */
	public String getEMailTest()
	{
		return (String)get_Value(COLUMNNAME_EMailTest);
	}

	/** Set Multi Lingual Documents.
		@param IsMultiLingualDocument Documents are Multi Lingual
	*/
	public void setIsMultiLingualDocument (boolean IsMultiLingualDocument)
	{
		set_Value (COLUMNNAME_IsMultiLingualDocument, Boolean.valueOf(IsMultiLingualDocument));
	}

	/** Get Multi Lingual Documents.
		@return Documents are Multi Lingual
	  */
	public boolean isMultiLingualDocument()
	{
		Object oo = get_Value(COLUMNNAME_IsMultiLingualDocument);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Post Immediately (Deprecated).
		@param IsPostImmediate Post the accounting immediately for testing (Deprecated)
	*/
	public void setIsPostImmediate (boolean IsPostImmediate)
	{
		set_Value (COLUMNNAME_IsPostImmediate, Boolean.valueOf(IsPostImmediate));
	}

	/** Get Post Immediately (Deprecated).
		@return Post the accounting immediately for testing (Deprecated)
	  */
	public boolean isPostImmediate()
	{
		Object oo = get_Value(COLUMNNAME_IsPostImmediate);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set SMTP SSL/TLS.
		@param IsSecureSMTP Use SSL/TLS for SMTP
	*/
	public void setIsSecureSMTP (boolean IsSecureSMTP)
	{
		set_Value (COLUMNNAME_IsSecureSMTP, Boolean.valueOf(IsSecureSMTP));
	}

	/** Get SMTP SSL/TLS.
		@return Use SSL/TLS for SMTP
	  */
	public boolean isSecureSMTP()
	{
		Object oo = get_Value(COLUMNNAME_IsSecureSMTP);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set SMTP Authentication.
		@param IsSmtpAuthorization Your mail server requires Authentication
	*/
	public void setIsSmtpAuthorization (boolean IsSmtpAuthorization)
	{
		set_Value (COLUMNNAME_IsSmtpAuthorization, Boolean.valueOf(IsSmtpAuthorization));
	}

	/** Get SMTP Authentication.
		@return Your mail server requires Authentication
	  */
	public boolean isSmtpAuthorization()
	{
		Object oo = get_Value(COLUMNNAME_IsSmtpAuthorization);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Is Use ASP.
		@param IsUseASP Is Use ASP
	*/
	public void setIsUseASP (boolean IsUseASP)
	{
		set_Value (COLUMNNAME_IsUseASP, Boolean.valueOf(IsUseASP));
	}

	/** Get Is Use ASP.
		@return Is Use ASP	  */
	public boolean isUseASP()
	{
		Object oo = get_Value(COLUMNNAME_IsUseASP);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Use Beta Functions.
		@param IsUseBetaFunctions Enable the use of Beta Functionality
	*/
	public void setIsUseBetaFunctions (boolean IsUseBetaFunctions)
	{
		set_Value (COLUMNNAME_IsUseBetaFunctions, Boolean.valueOf(IsUseBetaFunctions));
	}

	/** Get Use Beta Functions.
		@return Enable the use of Beta Functionality
	  */
	public boolean isUseBetaFunctions()
	{
		Object oo = get_Value(COLUMNNAME_IsUseBetaFunctions);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Login Prefix.
		@param LoginPrefix Login Prefix
	*/
	public void setLoginPrefix (String LoginPrefix)
	{
		set_Value (COLUMNNAME_LoginPrefix, LoginPrefix);
	}

	/** Get Login Prefix.
		@return Login Prefix	  */
	public String getLoginPrefix()
	{
		return (String)get_Value(COLUMNNAME_LoginPrefix);
	}

	/** MMPolicy AD_Reference_ID=335 */
	public static final int MMPOLICY_AD_Reference_ID=335;
	/** FiFo = F */
	public static final String MMPOLICY_FiFo = "F";
	/** LiFo = L */
	public static final String MMPOLICY_LiFo = "L";
	/** Set Material Policy.
		@param MMPolicy Material Movement Policy
	*/
	public void setMMPolicy (String MMPolicy)
	{

		set_Value (COLUMNNAME_MMPolicy, MMPolicy);
	}

	/** Get Material Policy.
		@return Material Movement Policy
	  */
	public String getMMPolicy()
	{
		return (String)get_Value(COLUMNNAME_MMPolicy);
	}

	/** Set Model Validation Classes.
		@param ModelValidationClasses List of data model validation classes separated by ;
	*/
	public void setModelValidationClasses (String ModelValidationClasses)
	{
		set_Value (COLUMNNAME_ModelValidationClasses, ModelValidationClasses);
	}

	/** Get Model Validation Classes.
		@return List of data model validation classes separated by ;
	  */
	public String getModelValidationClasses()
	{
		return (String)get_Value(COLUMNNAME_ModelValidationClasses);
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

	/** Set Request EMail.
		@param RequestEMail EMail address to send automated mails from or receive mails for automated processing (fully qualified)
	*/
	public void setRequestEMail (String RequestEMail)
	{
		set_Value (COLUMNNAME_RequestEMail, RequestEMail);
	}

	/** Get Request EMail.
		@return EMail address to send automated mails from or receive mails for automated processing (fully qualified)
	  */
	public String getRequestEMail()
	{
		return (String)get_Value(COLUMNNAME_RequestEMail);
	}

	/** Set Request Folder.
		@param RequestFolder EMail folder to process incoming emails; if empty INBOX is used
	*/
	public void setRequestFolder (String RequestFolder)
	{
		set_Value (COLUMNNAME_RequestFolder, RequestFolder);
	}

	/** Get Request Folder.
		@return EMail folder to process incoming emails; if empty INBOX is used
	  */
	public String getRequestFolder()
	{
		return (String)get_Value(COLUMNNAME_RequestFolder);
	}

	/** Set Request User.
		@param RequestUser User Name (ID) of the email owner
	*/
	public void setRequestUser (String RequestUser)
	{
		set_Value (COLUMNNAME_RequestUser, RequestUser);
	}

	/** Get Request User.
		@return User Name (ID) of the email owner
	  */
	public String getRequestUser()
	{
		return (String)get_Value(COLUMNNAME_RequestUser);
	}

	/** Set Request User Password.
		@param RequestUserPW Password of the user name (ID) for mail processing
	*/
	public void setRequestUserPW (String RequestUserPW)
	{
		set_Value (COLUMNNAME_RequestUserPW, RequestUserPW);
	}

	/** Get Request User Password.
		@return Password of the user name (ID) for mail processing
	  */
	public String getRequestUserPW()
	{
		return (String)get_Value(COLUMNNAME_RequestUserPW);
	}

	/** Set Mail Host.
		@param SMTPHost Hostname of Mail Server for SMTP and IMAP
	*/
	public void setSMTPHost (String SMTPHost)
	{
		set_Value (COLUMNNAME_SMTPHost, SMTPHost);
	}

	/** Get Mail Host.
		@return Hostname of Mail Server for SMTP and IMAP
	  */
	public String getSMTPHost()
	{
		return (String)get_Value(COLUMNNAME_SMTPHost);
	}

	/** Set SMTP Port.
		@param SMTPPort SMTP Port Number
	*/
	public void setSMTPPort (int SMTPPort)
	{
		set_Value (COLUMNNAME_SMTPPort, Integer.valueOf(SMTPPort));
	}

	/** Get SMTP Port.
		@return SMTP Port Number
	  */
	public int getSMTPPort()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SMTPPort);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Search Key.
		@param Value Search key for the record in the format required - must be unique
	*/
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue()
	{
		return (String)get_Value(COLUMNNAME_Value);
	}
}