package org.cms.hios.common.action;


import org.apache.log4j.Logger;

import org.jboss.soa.esb.message.Message;
import org.jboss.spring.vfs.context.VFSClassPathXmlApplicationContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;

public class SpringAction {
  private Logger logger = Logger.getLogger(SpringAction.class);
  private String springContextXml;
  private ApplicationContext appContext;


  public void initialise(String springContextXml) throws Exception
  {
    this.springContextXml = springContextXml;
    if ((this.springContextXml == null) || (this.springContextXml.equals(""))) {
      throw new Exception("No Spring context specified on action config: springContextXml.");
    }
    initializeSpring(springContextXml);
  }

  public boolean isBeanFactoryNull()
  {
    return this.appContext == null;
  }

  public void exceptionHandler(Message message, Throwable exception)
  {
    Throwable rootCause = exception.getCause();
    StackTraceElement[] traceElms = rootCause.getStackTrace();

    StringBuffer stackTrace = new StringBuffer("Exception Root Cause is: \n");
    stackTrace.append(rootCause.getMessage());
    stackTrace.append("\n Full Stack Trace is: \n");
    for (StackTraceElement elm : traceElms)
    {
      stackTrace.append(elm);
      stackTrace.append("\n");
    }
    this.logger.error(stackTrace.toString());
  }

  public void destroy() throws Exception
  {
    if (this.appContext != null)
    {
      this.appContext = null;
    }
  }

  public void initializeSpring(String springContextXml) throws Exception
  {
    if (isBeanFactoryNull()) {
      loadSpringIoc(springContextXml);
    }
  }

  public BeanFactory getBeanFactory() throws Exception
  {
    return this.appContext;
  }

  private void loadSpringIoc(String springContextXml) throws Exception
  {
    try
    {
      String[] contextXmlTokens = this.springContextXml.split(",");
       // Class<?> snowdropVfsAppContextClass = ClassUtil.forName("org.jboss.spring.vfs.context.VFSClassPathXmlApplicationContext", AbstractSpringAction.class);
    	  this.appContext =  new VFSClassPathXmlApplicationContext(contextXmlTokens);
    	  //this.appContext = AbstractSpringAction.VFSClassPathXmlApplicationContextFactory.access$100(new AbstractSpringAction.VFSClassPathXmlApplicationContextFactory(null), contextXmlTokens);
    }
    catch (BeansException e)
    {
      throw new Exception("BeansException caught in loadSpringToc : ", e);
    }
  }
}
