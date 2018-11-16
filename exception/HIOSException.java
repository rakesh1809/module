package org.cms.hios.common.exception;

import java.util.List;

import org.cms.hios.common.exception.ExceptionType;
import org.springframework.validation.Errors;

public class HIOSException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ExceptionType exceptionType;
	
	Errors errorMessages;
	/**
	 * 
	 */
	public HIOSException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public HIOSException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public HIOSException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public HIOSException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	public HIOSException(ExceptionType exType) {
		super(exType.name());
		this.exceptionType = exType;
		// TODO Auto-generated constructor stub
	}
	
	public HIOSException(Errors arg0) {
		super();
		errorMessages= arg0;
		
	}
	
	public Errors getErrorMessages() {
		return errorMessages;
	}

	/**
	 * @return the exceptionType
	 */
	public ExceptionType getExceptionType() {
		return exceptionType;
	}

	/**
	 * @param exceptionType the exceptionType to set
	 */
	public void setExceptionType(ExceptionType exceptionType) {
		this.exceptionType = exceptionType;
	}

}

