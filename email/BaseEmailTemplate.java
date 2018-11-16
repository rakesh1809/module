package org.cms.hios.common.email;

import java.util.HashMap;


public interface BaseEmailTemplate {
	public boolean sendEmail(String subject, String templateFile, HashMap<String,Object> emailParams) throws Exception ;
	public int sendBatchEmails(String subject, String templateFile) throws Exception ;
	public int sendBackgroundEmail(String subject, String templateFile, HashMap<String,Object> emailParams) throws Exception ;
	public void setEmailType(String emailType);
}
