package org.cms.hios.common.exception;

public enum ExceptionType {
	
	HIOS_USER_NAME_NOT_FOUND("HIOS_USER_NAME_NOT_FOUND"),
	HIOS_USER_DETAILS_NOT_FOUND("HIOS_USER_DETAILS_NOT_FOUND"),
	HIOS_USER_ROLES_NOT_FOUND("HIOS_USER_DETAILS_NOT_FOUND"),
	HIOS_USER_ACCOUNT_LOCKED("HIOS_USER_ACCOUNT_LOCKED"),
	HIOS_USER_NOT_APPROVED("HIOS_USER_NOT_APPROVED"),
	HIOS_USER_ACCOUNT_LOCKED_FOR_MAX_RETRY("HIOS_USER_ACCOUNT_LOCKED_FOR_MAX_RETRY"),
	HIOS_NO_DATA_FOUND("NO_DATA_FOUND");
	
	private String errorMsg;
	private ExceptionType(String errorMessage){
		this.errorMsg = errorMessage;
	}
	/**
	 * @return the errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}
	
	public static ExceptionType getExceptionTypeForMsg(String errorMsg){
		if(errorMsg==null||errorMsg.isEmpty()) return null;
		for(ExceptionType type: ExceptionType.values()){
			if(errorMsg.indexOf(type.getErrorMsg())>0){
				return type;
			}
		}
		return null;
	}
	
	
}
