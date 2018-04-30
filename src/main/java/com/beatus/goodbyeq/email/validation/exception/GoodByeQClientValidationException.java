package com.beatus.goodbyeq.email.validation.exception;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import com.beatus.goodbyeq.email.exception.ExceptionHandlerUtils;
import com.beatus.goodbyeq.email.exception.ResponseEntityException;

/**
 * @author vakey15
 * This is a exception that is thrown when there is a bad data in the request parameters
 */

public class GoodByeQClientValidationException extends ResponseEntityException{
	private static final long serialVersionUID = 1L;
	
	private String field;
	private String description;
    public GoodByeQClientValidationException(String field, String description, Throwable t) {
        super(description, t);
        this.field = field;
        this.description = description;
        this.status = HttpStatus.BAD_REQUEST;
    }

    public GoodByeQClientValidationException(String field, String description) {
        super(description);
        this.field = field;
        this.description = description;
        this.status = HttpStatus.BAD_REQUEST;
    }

	public String getField() {
        return field;
    }

    public String getDescription() {
        return description;
    }

    @Override
	protected Object body() {
		Map<String, Object> body = new LinkedHashMap<String, Object>();
		body.put(field, description);
		return ExceptionHandlerUtils.fail(body);
	}

	@Override
	protected HttpHeaders headers() {
		return ExceptionHandlerUtils.oauthHeaders(ExceptionHandlerUtils.ERROR_INVALID_REQUEST, description);
	}
}
