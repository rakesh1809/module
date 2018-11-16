package org.cms.hios.common.action;

import java.util.HashMap;

import org.apache.log4j.Logger;

public abstract class  HIOSAbstractWSBaseAction {

	final protected static Logger logger = Logger.getLogger(HIOSAbstractWSBaseAction.class);

	public String process(HashMap<String, Object> message) throws Exception {
		try{
			logger.debug("The request message:"+ message.toString());
			return this.executeAction(message);
		}catch(Exception e){
			logger.error("Exception in Action" , e);
			throw e;
		}catch(Throwable e){
			logger.error("Error in Action" , e);
			throw new Exception(e);
		}
	}

	protected abstract String executeAction(HashMap<String, Object> message) throws Exception ;

}
