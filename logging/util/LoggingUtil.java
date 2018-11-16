package org.cms.hios.common.logging.util;



import org.apache.log4j.Level;

import org.apache.log4j.Logger;

import org.slf4j.MDC;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * Util class for logging.Servers as a wrapper over jboss MDC and also provides 
 * some utility methods
 * @author svisvana
 *
 */
public class LoggingUtil {
	private static final Logger logger = Logger.getLogger(LoggingUtil.class);
	public static void put(String key, Object value){
		MDC.put(key, (String)value);
	}
	
	public static void remove(String key){
		MDC.remove(key);
	}
	
	public static Object get(String key){
		return MDC.get(key);
	}
	
	public static Marker createMarker(String name){
		return MarkerFactory.getMarker(name);
	}
	
	public static void addUserActivityAuditLog(Level level, String msg){
		addAuditLog(AuditType.USER_ACTIVITY_AUDIT, level, msg);
	}
	
	public static void addVirusScanAuditLog(Level level, String msg){
		addAuditLog(AuditType.VIRUS_SCAN_AUDIT, level, msg);
	}
	
	private static void addAuditLog(AuditType type, Level level, String msg){
		String message = type.getPrefixStr()+msg;
		
		switch(level.toInt()){
			case Level.INFO_INT:{
				logger.info(message);
				break;
			}
			case Level.TRACE_INT:{
				logger.trace(message);
				break;
			}
			case Level.DEBUG_INT:{
				logger.debug(message);
				break;
			}
			case Level.ERROR_INT:{
				logger.error(message);
				break;
			}
		
		}
		
	}
	
	

}
