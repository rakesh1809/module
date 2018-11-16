package org.cms.hios.common.logging.util;


public enum AuditType {
	USER_ACTIVITY_AUDIT("USER_AUDIT::"),
	VIRUS_SCAN_AUDIT("VIRUS_SCAN::");
	
	private String prefixStr;

	private AuditType(String prefixStr){
		this.prefixStr= prefixStr;
		
	}
	
	
	
	public String getPrefixStr() {
		return prefixStr;
	}
	
	
}
