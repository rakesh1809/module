package org.cms.hios.common.logging.util;

import java.lang.reflect.Method;
import java.text.MessageFormat;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;




@Aspect
public class HiosTimeLogger {

	private Logger logger = Logger.getLogger(HiosTimeLogger.class);

  
    
    @Around("@annotation(LogTimeTaken))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
	Method m = MethodSignature.class.cast(point.getSignature()).getMethod();
	m.isAnnotationPresent(LogTimeTaken.class);
	
      long start = System.currentTimeMillis();
      Object result = point.proceed();
      String methodName =m.getName();

      long timeTaken =  System.currentTimeMillis() - start;
      String logMessage = MessageFormat.format("{0} took {1} msecs", methodName, timeTaken).toString();
      String argMessage =  MessageFormat.format("Arguments::{0}", point.getArgs()).toString();
     LogTimeTaken ann = m.getDeclaredAnnotation(LogTimeTaken.class);
     boolean isLogArgs=ann.logArguments();
      if(ann.logLevel().equalsIgnoreCase("DEBUG")){
          logger.debug(logMessage );
          if(isLogArgs){    	  
    	       logger.debug(argMessage );
          }
      }else if(ann.logLevel().equalsIgnoreCase("INFO")){
	  logger.info(logMessage );
          if(isLogArgs){    	  
    	       logger.info(argMessage );
          }
      }
      
      return result;
    }
}
