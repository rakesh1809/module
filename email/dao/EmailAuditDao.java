package org.cms.hios.common.email.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.cms.hios.common.email.util.CommonUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.mail.SimpleMailMessage;

public class EmailAuditDao {
	private static final Logger logger = Logger.getLogger(EmailAuditDao.class);
	
	private NamedParameterJdbcTemplate jdbcTemplate;
	private JdbcTemplate basicJdbcTemplate;
	private Properties sqlQueries;
	protected LobHandler lobHandler;
	
	public LobHandler getLobHandler() {
		return lobHandler;
	}

	public void setLobHandler(LobHandler lobHandler) {
		this.lobHandler = lobHandler;
	}

	public NamedParameterJdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public JdbcTemplate getBasicJdbcTemplate() {
		return basicJdbcTemplate;
	}
	
	public void setBasicJdbcTemplate(JdbcTemplate basicJdbcTemplate) {
		this.basicJdbcTemplate = basicJdbcTemplate;
	}
	
	public Properties getSqlQueries() {
		return sqlQueries;
	}
	
	public void setSqlQueries(Properties sqlQueries) {
		this.sqlQueries = sqlQueries;
	}
	
	/**
	 * Save audit record for email
	 * 
	 * @param message
	 * @param emailParams
	 */
	public int saveEmailMessage(final SimpleMailMessage message, final Map<String, Object> emailParams) {
		String sqlQuery = sqlQueries.getProperty("saveEmailMessage");
		this.basicJdbcTemplate.execute(sqlQuery, new AbstractLobCreatingPreparedStatementCallback(this.lobHandler) {
			
			@Override
			protected void setValues(PreparedStatement ps, LobCreator lc) throws SQLException, DataAccessException {
				byte[] emailMessage = message.getText().getBytes();
				if (emailParams.get("requestId") != null) {
					ps.setInt(1, Integer.valueOf((String) emailParams.get("requestId")));
				}
				else {
					ps.setString(1, null);
				}
				
				lc.setBlobAsBytes(ps, 2, emailMessage);
				ps.setString(3, CommonUtils.convertToCommaDelimited(message.getTo()));
				ps.setString(4, CommonUtils.convertToCommaDelimited(message.getCc()));
				ps.setString(5, CommonUtils.convertToCommaDelimited(message.getBcc()));
				ps.setTimestamp(6, new Timestamp(new Date().getTime()));
				
				if (emailParams.get("emailType") != null) {
					ps.setString(7, (String) emailParams.get("emailType"));
				}
				else {
					ps.setString(7, null);
				}
			}
		});
		
		sqlQuery = sqlQueries.getProperty("getLatestLogId");
		
		logger.debug("Email Message saved into database...");
		
		return this.basicJdbcTemplate.queryForInt(sqlQuery);
	}
}
