package com.beatus.goodbyeq.email.service.exception;

import com.beatus.goodbyeq.email.exception.ResponseEntityException;

public class GoodByeQEmailServiceException extends ResponseEntityException {
	
	/**
	 * @author vakey15
	 * This is a exception that is thrown when there is a bad data in the request parameters
	 */
	private static final long serialVersionUID = 1L;

	public GoodByeQEmailServiceException(String message) {
        super(message);
    }

    public GoodByeQEmailServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
