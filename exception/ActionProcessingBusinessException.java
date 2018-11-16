package org.cms.hios.common.exception;

import org.apache.log4j.Logger;
import org.cms.hios.common.util.XMLUtils;

public class ActionProcessingBusinessException extends Exception {

	private static final long serialVersionUID = 3941239041133066943L;

	final protected Logger logger = Logger.getLogger(this.getClass().getName());

	public ActionProcessingBusinessException(String faultMessage) {
		super(faultMessage);
	}

	public ActionProcessingBusinessException(String message, String cause, Object o) {
		try {
			XMLUtils.marshall(o);
		}
		catch (Throwable th) {
			logger.error(cause, th);
		}
	}


}
