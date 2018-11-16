package org.cms.hios.common.email.main.impl;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.apache.log4j.Logger;
import org.cms.hios.common.email.BaseEmailTemplate;
import org.cms.hios.common.email.EmailRecipient;
import org.cms.hios.common.email.dao.EmailAuditDao;
import org.cms.hios.common.email.util.CommonConstants;
import org.cms.hios.common.util.CommonUtilFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

public abstract class AbstractEmailTemplateImpl implements BaseEmailTemplate {
	
	private static Logger logger = Logger.getLogger(AbstractEmailTemplateImpl.class);
	public static final String DEFAULT_TEMPLATE_DIR = "C:\\test\\";
	private String[] TEST_TO_RECIPIENTS = {};
	private String[] TEST_CC_RECIPIENTS = {};
	private String[] TEST_BCC_RECIPIENTS = {};
	
	@Autowired
	protected EmailAuditDao emailAuditDao;
	
	private String fromEmailProp;
	private EmailRecipient bccRecipient;
	private Configuration configuration;
	private JavaMailSender mailSender;
	private EmailRecipient toRecipient;
	private EmailRecipient ccRecipient;
	
	private String emailType;
	
	/**
	 * @return the fromEmailProp
	 */
	public String getFromEmailProp() {
		if (fromEmailProp == null || fromEmailProp.trim().length() == 0)
			return CommonConstants.EmailConstants.HIOS_EMAIL_FROM_ID;
		
		return fromEmailProp;
	}
	
	/**
	 * @param fromEmailProp the fromEmailProp to set
	 */
	public void setFromEmailProp(String fromEmailProp) {
		this.fromEmailProp = fromEmailProp;
	}
	
	/**
	 * @return the bccRecipient
	 */
	public EmailRecipient getBccRecipient() {
		return bccRecipient;
	}
	
	/**
	 * @param bccRecipient the bccRecipient to set
	 */
	public void setBccRecipient(EmailRecipient bccRecipient) {
		this.bccRecipient = bccRecipient;
	}
	
	public void setFreemarkerMailConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
	
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	public EmailRecipient getToRecipient() {
		return toRecipient;
	}
	
	public void setToRecipient(EmailRecipient toRecipient) {
		this.toRecipient = toRecipient;
	}
	
	public EmailRecipient getCcRecipient() {
		return ccRecipient;
	}
	
	public void setCcRecipient(EmailRecipient ccRecipient) {
		this.ccRecipient = ccRecipient;
	}
	
	public abstract Map<String, Object> getEmailParams(Map<String, Object> params) throws Exception;
	
	public abstract List<HashMap<String, Object>> getPendingEmailList() throws Exception;
	
	public abstract Boolean markAsSend(HashMap<String, Object> emailParam) throws Exception;
	
	@Override
	public boolean sendEmail(String subject, String templateFile, HashMap<String, Object> params) throws Exception {
		Map<String, Object> emailParams = getEmailParams(params);
		
		String content = processTemplate(templateFile, emailParams);
		
		return sendHTMLMessage(getEmailSubject(subject, emailParams), content, emailParams) > 0;
	}
	
	public int sendBatchEmails(String subject, String templateFile) throws Exception {
		int success = 0;
		List<HashMap<String, Object>> pendingEmail = getPendingEmailList();
		for (HashMap<String, Object> params : pendingEmail) {
			String content = processTemplate(templateFile, params);
			logger.debug("value of content : " + content);
			sendHTMLMessage(subject, content, params);
			if (markAsSend(params)) {
				success++;
			}
		}
		
		logger.info(pendingEmail.size() + " emails were sent with subject of [" + subject + "].");
		
		return success;
	}
	
//	// returns audit log id
//	public int sendBackgroudnEmail(String subject, String templateFile, HashMap<String, Object> params) throws Exception {
//		Map<String, Object> emailParams = getEmailParams(params);
//		
//		String content = processTemplate(templateFile, emailParams);
//		
//		return sendHTMLMessage(getEmailSubject(subject, emailParams), content, emailParams);
//	}
	
	/**
	 * 
	 * @param staticSubject
	 * @param recipientBeanId
	 * @param content
	 * @param dynamicParams
	 * @throws Exception
	 */
	public int sendMessage(String subject, String content, Map<String, Object> emailParams) throws Exception {
		SimpleMailMessage message = new SimpleMailMessage();
		int isSuccess;
		boolean isHelpDeskEmail = true;
		
		try {
			boolean isDebugOn = checkDebugStatus();
			
			// if debug true then modify message
			if (isDebugOn) {
				String[] realToRecipients = new String[] {};
				if (toRecipient != null) {
					realToRecipients = toRecipient.getRecipient(emailParams);
					message.setTo(TEST_TO_RECIPIENTS);
				}
				
				String[] realCCRecipients = new String[] {};
				if (ccRecipient != null) {
					realCCRecipients = ccRecipient.getRecipient(emailParams);
					message.setCc(TEST_CC_RECIPIENTS);
				}
				
				String[] realBCCRecipients = new String[] {};
				if (bccRecipient != null) {
					realBCCRecipients = bccRecipient.getRecipient(emailParams);
					message.setBcc(TEST_BCC_RECIPIENTS);
					
				}
				String debugContent = createDebugMessage(content, subject, realToRecipients, realCCRecipients, realBCCRecipients);
				message.setText(debugContent);
			}
			else {
				// TO recipients
				logger.debug("emails not in debug mode.");
				if (toRecipient != null) {
					
					String[] toList = toRecipient.getRecipient(emailParams);
					if (toList != null && toList.length > 0) {
						message.setTo(toRecipient.getRecipient(emailParams));
						isHelpDeskEmail = false;
					}
				}
				
				// CC recipients
				if (ccRecipient != null) {
					message.setCc(ccRecipient.getRecipient(emailParams));
					isHelpDeskEmail = false;
				}
				
				// BCC recipients
				if (bccRecipient != null) {
					String[] bccRecipients = bccRecipient.getRecipient(emailParams);
					if (bccRecipients != null && bccRecipients.length > 0) {
						message.setBcc(bccRecipients);
						isHelpDeskEmail = false;
					}
				}
				
				// if to, cc, bcc email addresses are not present then set default
				if (isHelpDeskEmail) {
					String defHelpdeskId = System.getProperty(CommonConstants.EmailConstants.HIOS_HELPDESK_EMAIL_ID);
					String[] helpDeskIds = defHelpdeskId.split(",");
					message.setTo(helpDeskIds);
				}
				message.setText(content);
			}
			
			// set message text
			message.setSubject(subject);
			
			message.setFrom(System.getProperty(getFromEmailProp()));
			mailSender.send(message);
			
		}
		catch (Exception e) {
			logger.info("Exception in sending email", e);
			isSuccess = 0;
			throw e;
		}
		
		// if successful then write to db
		try {
			isSuccess = auditLog(message, emailParams);
		}
		catch (Exception e) {
			isSuccess = 0;
			logger.info("Exception while writing message into audit log table", e);
			throw e;
		}
		return isSuccess;
	}
	
	private int auditLog(SimpleMailMessage message, Map<String, Object> emailParams) throws Exception {
		return emailAuditDao.saveEmailMessage(message, emailParams);
		
	}
	
	public int auditLog(MimeMessage message, Map<String, Object> emailParams) throws Exception {
		SimpleMailMessage mm = new SimpleMailMessage();
		mm.setBcc(Arrays.toString(message.getRecipients(RecipientType.BCC)));
		mm.setCc(Arrays.toString(message.getRecipients(RecipientType.CC)));
		mm.setTo(Arrays.toString(message.getRecipients(RecipientType.TO)));
		mm.setFrom(Arrays.toString(message.getFrom()));
		mm.setSubject(message.getSubject());
		mm.setText(message.getContent().toString());
		emailParams.put("emailType", emailType);
		
		return emailAuditDao.saveEmailMessage(mm, emailParams);
	}
	
	/**
	 * 
	 * @param params
	 * @return
	 */
	private String getEmailSubject(String subject, Map<String, Object> params) {
		String strSubject = (String) params.get("subject");
		
		return strSubject;
	}
	
	/**
	 * 
	 * @param templateFileName
	 * @param params
	 * @return
	 * @throws Exception
	 */
	protected String processTemplate(String templateFileName, java.util.Map<String, Object> params) throws Exception {
		// get root dir
		String templateRootDirVal = System.getProperty(CommonConstants.EmailConstants.HIOS_EMAIL_TEMPLATE_ROOT_DIR_PROP);
		
		FileTemplateLoader ftl = new FileTemplateLoader(new File(templateRootDirVal != null ? templateRootDirVal : DEFAULT_TEMPLATE_DIR));
		configuration.setTemplateLoader(ftl);
		
		Template template = configuration.getTemplate(templateFileName);
		String result = FreeMarkerTemplateUtils.processTemplateIntoString(template, params);
		logger.info("Processing email template: " + templateFileName);
		return result;
	}
	
	/**
	 * 
	 * @return
	 */
	private boolean checkDebugStatus() {
		String debugMode = System.getProperty(CommonConstants.EmailConstants.HIOS_EMAIL_DEBUG_MODE_PROP);
		boolean DEBUG_MODE = false;
		
		if (!"false".equals(debugMode)) {
			DEBUG_MODE = true;
			String toEmails = System.getProperty(CommonConstants.EmailConstants.HIOS_EMAIL_DEBUG_TO_ID_PROP);
			
			if (toEmails != null && toEmails.length() > 0) {
				TEST_TO_RECIPIENTS = toEmails.split(",");
			}
			
			String ccEmails = System.getProperty(CommonConstants.EmailConstants.HIOS_EMAIL_DEBUG_CC_ID_PROP);
			if (ccEmails != null && ccEmails.length() > 0) {
				TEST_CC_RECIPIENTS = ccEmails.split(",");
			}
			String bccEmails = System.getProperty(CommonConstants.EmailConstants.HIOS_EMAIL_DEBUG_BCC_ID_PROP);
			if (bccEmails != null && bccEmails.length() > 0) {
				TEST_BCC_RECIPIENTS = bccEmails.split(",");
			}
			
		}
		else {
			DEBUG_MODE = false;
		}
		return DEBUG_MODE;
	}
	
	/**
	 * Instead of sending the email to the real recipients, do the
	 * following - Copy the list of recipients, subject and content
	 * to a single string. setup an email message with the resulting
	 * string as email content, the TEST_RECIPIENTS as the real
	 * recipients, and put scenario specific subject line for
	 * testers.
	 */
	private String createDebugMessage(String content, String subject, String[] realToRecipients, String[] realCCRecipients, String[] bccRecipients) {
		
		StringWriter testContentSW = new StringWriter();
		PrintWriter pw = new PrintWriter(testContentSW);
		pw.print("********** This is a sample email to be used for testing purposes only");
		pw.print(" ENVIRONMENT= ");
		pw.print(System.getProperty("HIOS.Environment"));
		pw.println("  ***************   ");
		pw.println("**********It contains all the data for testing the real email scenario**************");
		pw.println("------------------------------------------------------------------------------------");
		pw.println("TO:");
		for (String currentRealRecipient : realToRecipients) {
			pw.println("     " + currentRealRecipient + ";");
		}
		pw.println("CC:");
		for (String currentRealRecipient : realCCRecipients) {
			pw.println("     " + currentRealRecipient + ";");
		}
		pw.println("BCC:");
		for (String curentRealRecipient : bccRecipients) {
			pw.println("     " + curentRealRecipient + ";");
		}
		
		pw.println("------------------------------------------------------------------------------------");
		pw.println("SUBJECT: " + subject);
		pw.println("------------------------------------------------------------------------------------");
		//pw.println("Real email content: " + subject);
		pw.println(content);
		pw.println("------------------------------------------------------------------------------------");
		
		content = testContentSW.toString();
		return content;
	}
	
	public int sendHTMLMessage(String subject, String content, Map<String, Object> emailParams) throws Exception {
		// SimpleMailMessage message = new SimpleMailMessage();
		int isSuccess;
		boolean isHelpDeskEmail = true;
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		
		// If email has attachments, make this multiple part.
		@SuppressWarnings("unchecked")
		List<String> fileNames = (List<String>) emailParams.get(CommonConstants.EmailConstants.HIOS_EMAIL_ATTACHMENTS_PARAM);
		boolean multipart = false;
		if (fileNames != null && !fileNames.isEmpty()) {
			multipart = true;
		}
		
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, multipart, "utf-8");
		
		try {
			boolean isDebugOn = checkDebugStatus();
			
			// if debug true then modify message
			if (isDebugOn) {
				String[] realToRecipients = new String[] {};
				if (toRecipient != null) {
					realToRecipients = toRecipient.getRecipient(emailParams);
					// mimeMessage.setTo(TEST_TO_RECIPIENTS);
					helper.setTo(TEST_TO_RECIPIENTS);
				}
				
				String[] realCCRecipients = new String[] {};
				if (ccRecipient != null) {
					realCCRecipients = ccRecipient.getRecipient(emailParams);
					helper.setCc(TEST_CC_RECIPIENTS);
				}
				
				String[] realBCCRecipients = new String[] {};
				if (bccRecipient != null) {
					realBCCRecipients = bccRecipient.getRecipient(emailParams);
					helper.setBcc(TEST_BCC_RECIPIENTS);
					
				}
				String debugContent = createDebugMessage(content, subject, realToRecipients, realCCRecipients, realBCCRecipients);
				helper.setText(debugContent, true);
			}
			else {
				logger.debug("emails not in debug mode.");
				
				// TO recipients
				if (toRecipient != null) {
					String[] toList = toRecipient.getRecipient(emailParams);
					if (toList != null && toList.length > 0) {
						helper.setTo(toList);
						isHelpDeskEmail = false;
					}
				}
				
				// CC recipients
				if (ccRecipient != null) {
					String[] ccRecipients = ccRecipient.getRecipient(emailParams);
					if (ccRecipients != null && ccRecipients.length > 0) {
						helper.setCc(ccRecipients);
						isHelpDeskEmail = false;
					}
				}
				
				// BCC recipients
				if (bccRecipient != null) {
					String[] bccRecipients = bccRecipient.getRecipient(emailParams);
					if (bccRecipients != null && bccRecipients.length > 0) {
						helper.setBcc(bccRecipients);
						isHelpDeskEmail = false;
					}
				}
				
				// if to, cc, bcc email addresses are not present then set default
				if (isHelpDeskEmail) {
					String defHelpdeskId = System.getProperty(CommonConstants.EmailConstants.HIOS_HELPDESK_EMAIL_ID);
					String[] helpDeskIds = defHelpdeskId.split(",");
					helper.setTo(helpDeskIds);
				}
				helper.setText(content, true);
			}
			
			// set the attachments
			if(CommonUtilFunctions.isListNotNullAndNotEmpty(fileNames)){				
				for(String names : fileNames){
					Object fileByte = emailParams.get(names);
					if(fileByte != null && fileByte instanceof byte []){
						byte [] fileBytes = (byte [])fileByte;
						helper.addAttachment(names, new ByteArrayResource(fileBytes));	
						if(logger.isDebugEnabled())
							logger.debug("Attachment File Name :"+names+" File size : " + fileBytes.length);	
					}else{
						File f = new File(names);
						helper.addAttachment(f.getName(), f);	
					}
				}
			}
			
			// set message text
			helper.setSubject(subject);
			
			helper.setFrom(System.getProperty(getFromEmailProp()));
			
			mailSender.send(helper.getMimeMessage());
			
		}
		catch (Exception e) {
			logger.info("Exception in sending email", e);
			isSuccess = 0;
			throw e;
		}
		
		// if successful then write to db
		try {
			isSuccess = auditLog(helper.getMimeMessage(), emailParams);
		}
		catch (Exception e) {
			isSuccess = 0;
			logger.info("Exception while writing message into audit log table", e);
			throw e;
		}
		return isSuccess;
	}
	
	@Override
	public void setEmailType(String emailType) {
		this.emailType = emailType;
	}
	
	public String getDate() {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Date today = Calendar.getInstance().getTime();
		String todayDate = df.format(today);
		return todayDate;
	}
}
