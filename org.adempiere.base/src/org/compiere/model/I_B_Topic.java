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
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.util.KeyNamePair;

/** Generated Interface for B_Topic
 *  @author iDempiere (generated) 
 *  @version Release 12
 */
public interface I_B_Topic 
{

    /** TableName=B_Topic */
    public static final String Table_Name = "B_Topic";

    /** AD_Table_ID=679 */
    public static final int Table_ID = 679;

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Tenant.
	  * Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within tenant
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within tenant
	  */
	public int getAD_Org_ID();

    /** Column name B_TopicCategory_ID */
    public static final String COLUMNNAME_B_TopicCategory_ID = "B_TopicCategory_ID";

	/** Set Topic Category.
	  * Auction Topic Category
	  */
	public void setB_TopicCategory_ID (int B_TopicCategory_ID);

	/** Get Topic Category.
	  * Auction Topic Category
	  */
	public int getB_TopicCategory_ID();

	public org.compiere.model.I_B_TopicCategory getB_TopicCategory() throws RuntimeException;

    /** Column name B_TopicType_ID */
    public static final String COLUMNNAME_B_TopicType_ID = "B_TopicType_ID";

	/** Set Topic Type.
	  * Auction Topic Type
	  */
	public void setB_TopicType_ID (int B_TopicType_ID);

	/** Get Topic Type.
	  * Auction Topic Type
	  */
	public int getB_TopicType_ID();

	public org.compiere.model.I_B_TopicType getB_TopicType() throws RuntimeException;

    /** Column name B_Topic_ID */
    public static final String COLUMNNAME_B_Topic_ID = "B_Topic_ID";

	/** Set Topic.
	  * Auction Topic
	  */
	public void setB_Topic_ID (int B_Topic_ID);

	/** Get Topic.
	  * Auction Topic
	  */
	public int getB_Topic_ID();

    /** Column name B_Topic_UU */
    public static final String COLUMNNAME_B_Topic_UU = "B_Topic_UU";

	/** Set B_Topic_UU	  */
	public void setB_Topic_UU (String B_Topic_UU);

	/** Get B_Topic_UU	  */
	public String getB_Topic_UU();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name DecisionDate */
    public static final String COLUMNNAME_DecisionDate = "DecisionDate";

	/** Set Decision date	  */
	public void setDecisionDate (Timestamp DecisionDate);

	/** Get Decision date	  */
	public Timestamp getDecisionDate();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/** Set Document No.
	  * Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo);

	/** Get Document No.
	  * Document sequence number of the document
	  */
	public String getDocumentNo();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name IsPublished */
    public static final String COLUMNNAME_IsPublished = "IsPublished";

	/** Set Published.
	  * The Topic is published and can be viewed
	  */
	public void setIsPublished (boolean IsPublished);

	/** Get Published.
	  * The Topic is published and can be viewed
	  */
	public boolean isPublished();

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Processed.
	  * The document has been processed
	  */
	public void setProcessed (boolean Processed);

	/** Get Processed.
	  * The document has been processed
	  */
	public boolean isProcessed();

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing (boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

    /** Column name TextDetails */
    public static final String COLUMNNAME_TextDetails = "TextDetails";

	/** Set Details	  */
	public void setTextDetails (String TextDetails);

	/** Get Details	  */
	public String getTextDetails();

    /** Column name TextMsg */
    public static final String COLUMNNAME_TextMsg = "TextMsg";

	/** Set Text Message.
	  * Text Message
	  */
	public void setTextMsg (String TextMsg);

	/** Get Text Message.
	  * Text Message
	  */
	public String getTextMsg();

    /** Column name TopicAction */
    public static final String COLUMNNAME_TopicAction = "TopicAction";

	/** Set Topic Action	  */
	public void setTopicAction (String TopicAction);

	/** Get Topic Action	  */
	public String getTopicAction();

    /** Column name TopicStatus */
    public static final String COLUMNNAME_TopicStatus = "TopicStatus";

	/** Set Topic Status	  */
	public void setTopicStatus (String TopicStatus);

	/** Get Topic Status	  */
	public String getTopicStatus();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();
}
