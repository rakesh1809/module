package org.cms.hios.common.email.util;

public class CommonConstants {
	
	public static final String PENDING = "Pending";
	public static final String SENDING = "Sending";
	public static final String FAILED = "Failed";
	public static final String COMPLETED = "Completed";
	public static String RESULT_SET = "#result-set-1";
	public static final String MODULE_CD_UI = "HSPORT";
	public static final String USER_EMAIL_ADDRESS = "USER_EMAIL_ADDRESS";
	
	public static final String GET_REQUESTOR_EMAIL_ADDRESS_SP = "PENDING.SP_GET_REQUESTOR_EMAIL_ADDRESS";
	public static final String SP_GET_LIST_COMPANY_REQUESTS = "PENDING.SP_GET_LIST_COMPANY_REQUESTS";
	
	public static final String SUBJECT_VALUE = "{requestType}";
	
	public static final String CSV_EXT = ".csv";
	public static final String DOC_EXT = ".doc";
	public static final String PDF_EXT = ".pdf";
	public static final String ZIP_EXT = ".zip";
	public static final String TXT_EXT = ".txt";
	public static final String XLS_EXT = ".xlsx";
	
	public static final String DATA_DUMP_FOLDER_KEY = "DATA_DUMP_FOLDER";
	public static final String CSV_NAME_KEY = "CSV_NAME";
	public static final String ZIP_FILE_NAME_KEY = "ZIP_FILE_NAME";
	public static final String QUERY_NAME_KEY = "QUERY_NAME";
	public static final String HEADER_ROW_KEY = "HEADER_ROW";
	public static final String ZIP_FILE_NAME_WEB_KEY = "ZIP_FILE_NAME_WEB";
	
	public static final String ARCHIVE_FOLDER = "archiveFolder";
	public static final String FTP_REMOTE_DIR = "ftpRemoteDir";
	public static final String ROOT_DIR_PATH = "rootDirPath";
	
	public static final String FILE_SEPARATOR_PROP = "file.separator";
	
	public static final String MESSAGE_PAYLOAD = "PAYLOAD";
	public static final int LIMIT = 2000;
	
	//fien validation esb service constants
	public static final String FEIN_FILE_PROCESS_WORKFLOW = "HIOSFIENFileProcessWorkFlow";
	public static final String FEIN_FILE_NAME = "HIOSTINLBNValidation";
	public static final String FEIN_FILE_PARAM = "FEIN_FILE";
	public static final String FEIN_FILE_IN_PROCESS_PARAM = "FEIN_FILE_IN_PROCESS";
	
	public static final String SEMI_COLON = ";";
	public static final String DATE_FORMAT = "MM/dd/yyyy";
	public static final String DATE_TIME_FORMAT = "MM/dd/yyyy HH:mm:ss";
	public static final String DATE_TIME_MILI_SEC_FORMAT = "yyyy-MM-dd HH:mm:ss.SS";
	
	//validation status
	public static final String NOT_VALIDATED = "Not Validated";
	public static final String VALIDATION_IN_PROCESS = "Validation In Process";
	public static final String FAILED_VALIDATION = "Failed Validation";
	public static final String VALIDATED = "Validated";
	
	//CCS Roles
	public static final String CCS_SUBMITTER = "CERTCOMSubmitter";
	public static final String CCS_AO = "CERTCOMAuthOfficial";
	
	public static final String META_DATA_KEY_ID = "META_DATA_KEY_ID";
	public static final String TRCKD_EVENT_NAME = "TRCKD_EVENT_NAME";
	
	public static final String USER_STATUS = "USER_STATUS";
	public static final String PENDING_APPROVAL_CODE = "176";
	public static final String AUTO_APPROVAL_CODE = "320";
	public static final String USER_ROLE_ASSIGNMENT = "USER_ROLE_ASSIGNMENT";
	
	//ASSISTER CERT SCHEDULER CONSTANTS
	public static final String LAST_SUCCESS_RUN_DT = "LAST_SUCCESS_RUN_DT";
	public static final String MLMS_SVC_RES = "MLMS_SVC_RES";
	public static final String NEXT_RUN_DT = "NEXT_RUN_DT";
	public static final String DT_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	//MEC Email Constants
	public static final String MEC_SUBMISSION_COMPLETE = "Submission Complete";
	public static final String MEC_ATTESTATION_COMPLETE = "Attestation Complete";
	public static final String MEC_EXAMINER_ASSINGED_TO_SUBMSN = "Examiner Assigned";
	public static final String MEC_ACTION_TYPE_REFERENCE_ID = "MEC_Action_Type";
	public static final String MEC_LABEL_REFERENCE_ID = "Label_Id";
	public static final String MEC_SUBMSN_REFERENCE_ID = "Submsn_Id";
	public static final String MEC_REVIEWER = "MECReviewer";
	public static final String MEC_CERTIFYING_OFFICIAL = "MECCertifyingOfficial";
	public static final String MEC_SUBMITTER = "MECSubmitter";
	public static final String MEC_EXAMINER = "MECExaminer";
	public static final String MEC_MODULE = "DCMMEC";
	
	//DCM-FF Data Extract Contants
	public static final String FF_QUERY_POSTFIX_PROPERTY = "_DCMFF_DataExtract";
	public static final String FF_PREFIX_VAL_PROPERTY = "DCMFF.DATAEXTRACT.PREFIX.";
	
	//HIOS User Data Extract Constants
	public static final String HIOSUser_QUERY_POSTFIX_PROPERTY = "_HIOSUser_DataExtract";
	public static final String TEST_EMAILS_FILTER_KEY = "Filter_TestUserEmails";
	public static final String HIOS_USER_DATA_EXTRACT_PREFIX = "USER.DATA.EXTRACT";
	public static final String HIOS_USER_DATA_EXTRACT_FILE = "USER.DATA.EXTRACT_FILE";
	public static final String TestUser_FILTER_QUERY_POSTFIX_PROPERTY = "_testUserFilter";
	public static final String Order_QUERY_POSTFIX_PROPERTY = "_ordering";
	
	//Generic Data Extract Constants
	public static final String NUMOfFILES_PROPERTY = ".NumOfFiles"; //number of final files to be generated
	public static final String PARENTDIR_PROPERTY = ".Parent_Dir"; //parent directory where all the files will reside
	public static final String STATICFILEDIR_PROPERTY = ".StaticFiles_DirName"; //static file folder name
	public static final String UIDIR_PROPERTY = ".UIOutput_DirName"; //final output folder name
	public static final String ARCHIVEDIR_PROPERTY = ".Archive_DirName"; //archive folder name
	public static final String FILENAME_PROPERTY = ".FILENAME"; //data extract file name
	public static final String ZIPFILENAME_PROPERTY = ".ZIPFILENAME"; //zip file to be generated
	public static final String QUERYNAME_PROPERTY = ".QUERYNAME"; //specify the query name(s)
	public static final String CREATEZIPFLAG_PROPERTY = ".CREATEZIPFLAG"; //boolean value
	
	public static final String OUTPUTDIR_PROPERTY = ".Output_DirName"; //final output folder name (used for excel extract)
	public static final String TEMPLATEDIR_PROPERTY = ".Template_Dir"; //template directory
	public static final String TEMPLATEFILENAME_PROPERTY = ".TEMPLATE_FILE_NAME"; //template name for every file
	public static final String NUMOFSHEETS_PROPERTY = ".NumOfEXCEL_SHEETS"; //number of excel sheets
	public static final String EXCEL_FILEPREFIX_PROPERTY = "EXCEL.";
	public static final String EXCEL_SHEETPREFIX_PROPERTY = "SHEET.";
	public static final String POPULATEHEADERFLAG_PROPERTY = ".POPULATE_SHEET_HEADER"; //boolean value
	public static final String ZIP_PASSWORD_PROPERTY = ".ZIP_PASSWORD";
	public static final String FILTER_CRITERIA_KEY_PROPERTY = ".FILTER_CRITERIA_KEY";
	public static final String FILTER_CRITERIA_VALUE_PROPERTY = ".FILTER_CRITERIA_VALUE";
	public static final String QUERY_POSTFIX_PROPERTY = ".QUERY_POSTFIX";
	public static final String SEND_EMAIL_PROPERTY = ".SEND_EMAIL";
	public static final String APPEND_TIMESTAMP = ".APPEND_TIMESTAMP";
	public static final String APPEND_REQID = ".APPEND_REQUESTID";
	public static final String FILENAME_LIST_PARAM = "filenameList";

	// HIOS PORTAL Email Constants
	public static final String COMPANY_GROUOP_CREATION = "Company and Group Creation";
	public static final String ISSUER_CREATION = "Issuer Creation";
	public static final String NON_FED_GOVERNMENTAL_PLANS_CREATION = "Non-Federal Governmental Plans Creation";
	public static final String OTHER_ORG_TYPE_CREATION = "Other Organization Type Creation";
	public static final int COMPANY_GROUOP_CREATION_REQUEST_ID = 233;
	public static final int ISSUER_CREATION_REQUEST_ID = 230;
	public static final int OTHER_ORG_TYPE_CREATION_REQUEST_ID = 304;
	public static final int NONFED_CREATION_REQUEST_ID = 284;
	public static final int USER_ROLE_ASSIGNMENT_REQUEST_ID = 236;
	
	// ECP Role Names
	public static final String ROLE_NAME_ECP_STATE_SUBMITTER = "ECPStateSubmitter";
	public static final String ROLE_NAME_ECP_STATE_VALIDATOR = "ECPStateValidator";
	public static final String ROLE_NAME_ECP_CCIIO_REVIEWER = "ECPCCIIOEnforcement";
	
	// SFG Role Names
	public static final String ROLE_NAME_SFG_STATE_SUBMITTER = "SFGStateSubmitter";
	public static final String ROLE_NAME_SFG_STATE_VALIDATOR = "SFGStateValidator";
	public static final String ROLE_NAME_SFG_CCIIO_REVIEWER = "SFGCCIIOEnforcement";
	
	//ERE Role Name
	public static final String ROLE_NAME_ERE_CCIIO_REVIEWER = "ERECCIIOReviewer";
	
	//NONFed Email Constants
	public static final String NON_FED_ECLTN_RVW_STUS_TYPE_APPRVD = "3";
	
	public class EmailConstants {
		public static final String HIOS_EMAIL_ATTACHMENTS_PARAM = "HIOS.EMAIL.ATTACHMENT";
		public static final String HIOS_HELPDESK_EMAIL_ID = "HIOS.HELPDESK.SUPPORT.EMAIL.ID";
		public static final String HIOS_EMAIL_FROM_ID = "HIOS.EMAIL.FROM.ID";
		public static final String HIOS_EMAIL_TEMPLATE_ROOT_DIR_PROP = "HIOS_EMAIL_TEMPLATE_ROOT_DIR";
		public static final String HIOS_EMAIL_DEBUG_MODE_PROP = "HIOS.Email.Debug.Mode";
		public static final String HIOS_EMAIL_DEBUG_TO_ID_PROP = "HIOS.Email.Debug.TO.ids";
		public static final String HIOS_EMAIL_DEBUG_CC_ID_PROP = "HIOS.Email.Debug.CC.ids";
		public static final String HIOS_EMAIL_DEBUG_BCC_ID_PROP = "HIOS.Email.Debug.BCC.ids";
	}
}