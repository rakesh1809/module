package org.cms.hios.common.email;

import java.util.Map;

import javax.mail.internet.AddressException;

public interface EmailRecipient {
	/**
	 * Concrete implementations of this method
	 * will retrieve email recipients 
	 * @param params
	 * @return
	 * @throws AddressException
	 */
	public String[] getRecipient(Map<String, Object> params) throws Exception;
}
