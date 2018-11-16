package org.cms.hios.common.validation;

public interface IErrorMessageType {
	
	public String getErrorMULTIPLE_SESSION_LOGIN();
	public String getErrorREQUIRED_TEXT_FIELD_MISSING();
	//Selection field (Dropdown, radio button, checkbox)
	public String getErrorREQUIRED_NONTEXT_FIELD_MISSING();
	public String getErrorINVALID_CHARACTERS();
	public String getError_ORGANAME_CHARACTERS();
	public String getErrorMIN_TEXT_LENGTH_NOT_MET();
	public String getErrorMAX_TEXT_LENGTH_EXCEEDED();
	public String getErrorNUMBER_FIELD_INVALID_EXACT_LENGTH();
	public String getErrorNUMBER_FIELD_INVALID_MAX_LENGTH();
	public String getErrorINVALID_DATE_FORMAT();
	public String getErrorINVALID_PHONE_FORMAT();
	public String getErrorINVALID_NONUS_PHONE_FORMAT();
	public String getErrorINVALID_EMAIL_ADDRESS_FORMAT();
	public String getErrorINVALID_EMAIL_DOMAIN();
	public String getErrorINVALID_URL_FORMAT();
	public String getErrorINVALID_SERFF_FORMAT();
	public String getErrorREQUIRED_FILE_UPLOAD_MISSING();
	public String getErrorMAX_FILE_SIZE_EXCEEDED();
	public String getErrorINVALID_FILE_EXTENSION();
	public String getErrorINVALID_PLAN_NAME();
	public String getErrorINVALID_NUMERIC_FIELD();
	public String getErrorTOO_MANY_RESULTS();
	public String getErrorINVALID_FILE_NAME();
	public String getErrorINVALID_ISSUER_ID();
}
