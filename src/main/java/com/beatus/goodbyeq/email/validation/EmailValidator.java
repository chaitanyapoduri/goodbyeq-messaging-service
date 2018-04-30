package com.beatus.goodbyeq.email.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beatus.goodbyeq.email.model.MailVO;
import com.beatus.goodbyeq.email.validation.exception.GoodByeQClientValidationException;
public class EmailValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(EmailValidator.class);

	/*public boolean validateCompanyDTO(CompanyDTO company) throws GoodByeQClientValidationException{
		LOGGER.info(" Validating CompanyData " + company);

		if(company == null || StringUtils.isBlank(String.valueOf(company.getCompanyID()))){
			throw new GoodByeQClientValidationException("companyID","CompanyData, the companyId field is not available");

		}
		if(StringUtils.isBlank(String.valueOf(company.getCompanyName()))){
			throw new GoodByeQClientValidationException("companyName","CompanyData, the companyName field is not available for company with companyId = " + company.getCompanyID());

		}
		if(StringUtils.isBlank(String.valueOf(company.getAddressLine1()))){
			throw new GoodByeQClientValidationException("addressLine1","CompanyData, the addressLine1 is not availablefor company with companyId = " + company.getCompanyID());

		}
		if(StringUtils.isBlank(String.valueOf(company.getCity()))){
			throw new GoodByeQClientValidationException("city","CompanyData, the city field is not available for company with companyId = " + company.getCompanyID());

		}
		if(StringUtils.isBlank(String.valueOf(company.getZipCode()))){
			throw new GoodByeQClientValidationException("zipcode","CompanyData, the zipcode field is not available for company with companyId = " + company.getCompanyID());

		}
		if(StringUtils.isBlank(String.valueOf(company.getEmail()))){
			throw new GoodByeQClientValidationException("Email","CompanyData, the Email field is not available for company with companyId = " + company.getCompanyID());

		}
		if(StringUtils.isBlank(String.valueOf(company.getPhoneNumber()))){
			throw new GoodByeQClientValidationException("phoneNumber","CompanyData, the phoneNumber field is not available for company with companyId = " + company.getCompanyID());

		}
		return false;
	}*/
	public boolean validateMailVO(MailVO mailVO) throws GoodByeQClientValidationException{
		LOGGER.info(" Validating MailVO " + mailVO);
		return true;
	}

}
