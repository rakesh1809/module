package org.cms.hios.common.email.transfer;

import java.util.Date;

public class EmailRequest{

	private String emailRequestId;
	
	private String emailTypeCd;
	private String emailStatus;
	private Date emailRequestCreateTime;
	private Date emailRequestUpdateTime;
	private String moduleCd;
	private String templateLocation;
	private String staticSubject;
	private String recipientBeanId;
	
	
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
	 * @return the emailRequestId
	 */
	public String getEmailRequestId() {
		return emailRequestId;
	}
	/**
	 * @param emailRequestId the emailRequestId to set
	 */
	public void setEmailRequestId(String emailRequestId) {
		this.emailRequestId = emailRequestId;
	}
	/**
	 * @return the emailTypeCd
	 */
	public String getEmailTypeCd() {
		return emailTypeCd;
	}
	/**
	 * @param emailTypeCd the emailTypeCd to set
	 */
	public void setEmailTypeCd(String emailTypeCd) {
		this.emailTypeCd = emailTypeCd;
	}
	/**
	 * @return the emailStatus
	 */
	public String getEmailStatus() {
		return emailStatus;
	}
	/**
	 * @param emailStatus the emailStatus to set
	 */
	public void setEmailStatus(String emailStatus) {
		this.emailStatus = emailStatus;
	}
	/**
	 * @return the emailRequestCreateTime
	 */
	public Date getEmailRequestCreateTime() {
		return emailRequestCreateTime;
	}
	/**
	 * @param emailRequestCreateTime the emailRequestCreateTime to set
	 */
	public void setEmailRequestCreateTime(Date emailRequestCreateTime) {
		this.emailRequestCreateTime = emailRequestCreateTime;
	}
	/**
	 * @return the emailRequestUpdateTime
	 */
	public Date getEmailRequestUpdateTime() {
		return emailRequestUpdateTime;
	}
	/**
	 * @param emailRequestUpdateTime the emailRequestUpdateTime to set
	 */
	public void setEmailRequestUpdateTime(Date emailRequestUpdateTime) {
		this.emailRequestUpdateTime = emailRequestUpdateTime;
	}
	
}
