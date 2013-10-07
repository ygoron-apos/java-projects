/**
 * 
 */
package com.apos.infoobject.properties;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * @author Yuri Goron
 * 
 */
@Component("EmailProcessor")
public class EmailProcessor {

	private static final Logger Log = Logger.getLogger(EmailProcessor.class);
	private ICommandLineArgs configurationSettings;
	private String emailText;
	private String headerText;

	
	public EmailProcessor() {

	}

	public boolean sendEmail() {
		try {
			Properties props = new Properties();
			props.put("mail.smtp.host",
					configurationSettings.getSmtpServerName());
			Log.debug("SMTP Server:"
					+ configurationSettings.getSmtpServerName());
			props.put("mail.smtp.port", configurationSettings.getSmptPort());
			Log.debug("SMTP Port:" + configurationSettings.getSmptPort());
			Log.debug("isSSL:" + configurationSettings.isEnableSmtpSSL());
			Log.debug("User Id:" + configurationSettings.getSmtpUserName());
			Log.debug("Password Length"
					+ configurationSettings.getSmtpUserPassword().length());
			Log.debug("From:" + configurationSettings.getSmptpEmailFrom());
			Log.debug("Tos:" + configurationSettings.getSmtpEmailTo());

			Session session = null;
			if (configurationSettings.isEnableSmtpSSL()) {
				Log.debug("Using SSL");
				props.put("mail.smtp.socketFactory.class",
						"javax.net.ssl.SSLSocketFactory");
			}

			if (StringUtils.isNotEmpty(configurationSettings.getSmtpUserName())) {
				Log.debug("Using User ID and Password");
				props.put("mail.smtp.auth", "true");
				session = Session.getInstance(props,
						new javax.mail.Authenticator() {
							protected PasswordAuthentication getPasswordAuthentication() {
								return new PasswordAuthentication(
										configurationSettings.getSmtpUserName(),
										configurationSettings
												.getSmtpUserPassword());
							}
						});
			} else {
				Log.debug("No User ID and Password were Provided");
				session = Session.getDefaultInstance(props);
			}

			Message message = new MimeMessage(session);

			message.setFrom(new InternetAddress(configurationSettings
					.getSmptpEmailFrom()));
			Log.debug("Original Email To:"
					+ configurationSettings.getSmtpEmailTo());
			String commaDelimited = configurationSettings.getSmtpEmailTo()
					.replace(";", ",");
			Log.debug("Comma Delimited:" + commaDelimited);
			InternetAddress[] internetAddresses = InternetAddress
					.parse(commaDelimited);
			message.setRecipients(Message.RecipientType.TO, internetAddresses);

			message.setSubject(configurationSettings.getSmptSubjectText());
			message.setText(headerText != null ? headerText+"\n\n" + emailText : emailText);
			
			Transport.send(message);

			Log.debug("Email Sent!");
			return true;
		} catch (Exception ee) {
			Log.error(ee.getLocalizedMessage(), ee);

		}
		return false;
	}

	/**
	 * @return the configurationSettings
	 */
	public ICommandLineArgs getConfigurationSettings() {
		return configurationSettings;
	}

	/**
	 * @param configurationSettings
	 *            the configurationSettings to set
	 */
	public void setConfigurationSettings(
			ICommandLineArgs configurationSettings) {
		this.configurationSettings = configurationSettings;
	}

	/**
	 * @return the emailText
	 */
	public String getEmailText() {
		return emailText;
	}

	/**
	 * @param emailText
	 *            the emailText to set
	 */
	public void setEmailText(String emailText) {
		this.emailText = emailText;
	}

	/**
	 * @param headerText the headerText to set
	 */
	public synchronized void setHeaderText(String headerText) {
		this.headerText = headerText;
	}

}
