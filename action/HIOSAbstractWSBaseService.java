package org.cms.hios.common.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public abstract class  HIOSAbstractWSBaseService {

	final protected static Logger logger = Logger.getLogger(HIOSAbstractWSBaseService.class);

	public Map<String,Object> process(HashMap<String, Object> message) throws Exception {
		try{
			logger.debug("HIOSAbstractWSBaseService.process: " + "The request message: "+ message.toString());
			return this.executeAction(message);
		}catch(Exception e){
			logger.error("Exception in Action" , e);
			throw e;
		}catch(Throwable e){
			logger.error("Error in Action" , e);
			throw new Exception(e);
		}
	}

	protected abstract Map<String,Object> executeAction(HashMap<String, Object> message) throws Exception ;

}
