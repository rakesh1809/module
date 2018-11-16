package org.cms.hios.common.email.transfer;



public class EmailContentTO {

	private String emailType;
	private String recipientBeanId;
	private String templateLocation;
	private String staticSubject;
	private String moduleCd;
	
	
	/**
	 * @return the moduleCd
	 */
	public String getModuleCd() {
		return moduleCd;
	}
	/**
	 * @param moduleCd the moduleCd to set
	 */
	public void setModuleCd(String moduleCd) {
		this.moduleCd = moduleCd;
	}
	/**
	 * @return the staticSubject
	 */
	public String getStaticSubject() {
		return staticSubject;
	}
	/**
	 * @param staticSubject the staticSubject to set
	 */
	public void setStaticSubject(String staticSubject) {
		this.staticSubject = staticSubject;
	}
	/**
	 * @return the emailType
	 */
	public String getEmailType() {
		return emailType;
	}
	/**
	 * @param emailType the emailType to set
	 */
	public void setEmailType(String emailType) {
		this.emailType = emailType;
	}
	/**
	 * @return the recipientBeanId
	 */
	public String getRecipientBeanId() {
		return recipientBeanId;
	}
	/**
	 * @param recipientBeanId the recipientBeanId to set
	 */
	public void setRecipientBeanId(String recipientBeanId) {
		this.recipientBeanId = recipientBeanId;
	}
	/**
	 * @return the templateLocation
	 */
	public String getTemplateLocation() {
		return templateLocation;
	}
	/**
	 * @param templateLocation the templateLocation to set
	 */
	public void setTemplateLocation(String templateLocation) {
		this.templateLocation = templateLocation;
	}
	
	
	
}
