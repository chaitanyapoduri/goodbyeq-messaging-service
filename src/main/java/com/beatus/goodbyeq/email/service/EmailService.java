package com.beatus.goodbyeq.email.service;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beatus.goodbyeq.email.model.MailVO;
import com.beatus.goodbyeq.email.repository.EmailRepository;
import com.beatus.goodbyeq.email.service.exception.GoodByeQEmailServiceException;
import com.beatus.goodbyeq.email.validation.EmailValidator;
import com.beatus.goodbyeq.email.validation.exception.GoodByeQValidationException;
import com.beatus.goodbyeq.utils.Constants;
import com.beatus.goodbyeq.utils.Utils;

public class EmailService {
	
	private EmailValidator emailValidator;
	private EmailRepository emailRepository;
	AWSSimpleMailService awsSimpleMailService;
	private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

	public EmailService() throws ClassNotFoundException, SQLException, IOException{
		awsSimpleMailService = new AWSSimpleMailService();
		emailRepository = new EmailRepository();
		emailValidator = new EmailValidator(); 
	}
	
	public String addEmail(MailVO mailVO)
			throws GoodByeQValidationException {
		if (mailVO == null) {
			throw new GoodByeQValidationException("Mail data cant be null");
		}
		try {
			// Revisit Validator
			String mailId = null;
			boolean isValidated = emailValidator.validateMailVO(mailVO);
			if (isValidated) {
				mailId = mailVO.getMailId();
				MailVO existingMailVO = null;
				if (StringUtils.isNotBlank(mailId)) {
					existingMailVO = getMailVOByID(mailId);
					return updateMailVO(mailVO);
				}
			} else {
				mailId = Utils.generateRandomKey(20);
				mailVO.setMailId(mailId);
				return emailRepository.addEmail(mailVO);
			}
		} catch (GoodByeQEmailServiceException goodByeQEmailServiceException) {
			LOGGER.error("GoodByeQEmail Service Exception in the addMail() {} ",
					goodByeQEmailServiceException.getMessage());
			throw goodByeQEmailServiceException;
		} catch (ClassNotFoundException | SQLException e) {
			throw new GoodByeQEmailServiceException("Exception in addEmail " + e.getMessage());
		}
		return Constants.FAILURE;
	}

	public MailVO getMailVOByID(String mailId) {
		LOGGER.info("In getMailVOByID method of Email Service");
		try {
			if (StringUtils.isNotBlank(mailId)) {
				MailVO mailVO = emailRepository.getMailVOByID(mailId);
				if (mailVO != null) {
					return mailVO;
				} else
					return null;
			} else {
				LOGGER.error(
						"GoodByeQEmail Service Exception in the getMailByID() {},  MailId passed cant be null or empty string");
				throw new GoodByeQEmailServiceException("Mail Id passed cant be null or empty string");
			}
		} catch (Exception e) {
			throw new GoodByeQEmailServiceException("Exception in getMailByID " + e);
		}

	}

	public String updateMailVO(MailVO mailVO)
			throws GoodByeQValidationException {

		if (mailVO == null) {
			throw new GoodByeQValidationException("MailVO data cant be null");
		}
		try {
			// Validate company DTO
			boolean isValidated = emailValidator.validateMailVO(mailVO);
			if (isValidated) {
				String companyID = mailVO.getMailId();
				MailVO existingMailVO = null;
				if (StringUtils.isNotBlank(mailVO.getMailId()))
					existingMailVO = getMailVOByID(companyID);
				return emailRepository.updateMailVO(mailVO);
			}
		} catch (GoodByeQEmailServiceException companyServiceException) {
			LOGGER.error("GoodByeQEmail Service Exception in the updateMailVOService() {} ",
					companyServiceException.getMessage());
			throw companyServiceException;
		} catch (SQLException e) {
			LOGGER.error("GoodByeQEmail Service Exception in the updateMailVOService() {} ", e.getMessage());
			throw new GoodByeQEmailServiceException(
					"GoodByeQEmail Service Exception in the updateMailVOService() {}" + e.getMessage());
		}

		return Constants.FAILURE;
	}

	public String sendEmail(MailVO mailVO)
			throws GoodByeQValidationException {

		if(emailValidator.validateMailVO(mailVO)) {
			try {
				awsSimpleMailService.sendEmailThroughAWSSES(mailVO.getToAddress(), mailVO.getSubject(), mailVO.getBody(), mailVO.getHtmlBody());
				addEmail(mailVO);
			} catch (IOException e) {
				throw new GoodByeQEmailServiceException("Error seding email through AWS " + e.getMessage());
			}
		}			
		return Constants.SUCCESS;
	}
}
