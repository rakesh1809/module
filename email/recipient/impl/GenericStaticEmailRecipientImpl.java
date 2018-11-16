package org.cms.hios.common.email.recipient.impl;

import java.util.Map;

import org.apache.log4j.Logger;
import org.cms.hios.common.email.EmailRecipient;

public class GenericStaticEmailRecipientImpl implements EmailRecipient {
	
	private static final Logger logger = Logger.getLogger(GenericStaticEmailRecipientImpl.class);
	
	private String propertyName;
	private String[] recipients = {};
	
	@Override
	public String[] getRecipient(Map<String, Object> params) throws Exception {
		
		logger.info("ENTER getRecipient");
		
		String emailAddresses = System.getProperty(propertyName);
		
		if (emailAddresses != null && emailAddresses.length() > 0) {
			recipients = emailAddresses.split(",");
		}
		
		return recipients;
	}
	
	/**
	 * @return the recipients
	 */
	public String[] getRecipients() {
		return recipients;
	}
	
	/**
	 * @param recipients
	 *            the recipients to set
	 */
	public void setRecipients(String[] recipients) {
		this.recipients = recipients;
	}
	
	/**
	 * @return the propertyName
	 */
	public String getPropertyName() {
		return propertyName;
	}
	
	/**
	 * @param propertyName the propertyName to set
	 */
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	
}
