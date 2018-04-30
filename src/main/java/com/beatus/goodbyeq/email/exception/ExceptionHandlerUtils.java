package com.beatus.goodbyeq.email.exception;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class ExceptionHandlerUtils{
    
    //OAUTH2 ERRORS
    public static final String ERROR_INVALID_REQUEST = "invalid_request";
    
    //JSEND CONSTANTS
    private static final String DATA = "data";
    private static final String MESSAGE = "message";
    private static final String STATUS = "status";
    
    //OAUTH2 ERROR HEADER CONSTANTS 
    private static final String GOODBYEQ_API_ERROR_NS = "goodbyeq_api";
    private static final String WWW_AUTHENTICATE = "WWW-Authenticate";
    private static final String WWW_AUTHENTICATE_TEMPLATE = "WWW-Authenticate: Bearer realm=\"" + GOODBYEQ_API_ERROR_NS + "\", error=\"%s\", error_description=\"%s.\"";    
    
    //JSEND ERROR STATUS
    public static final String ERROR = "error";
    public static final String FAIL = "fail";
    private static MessageSource MESSAGE_SOURCE;
    
    private static Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerUtils.class);
    
    public static HttpHeaders oauthHeaders(String error, String desc){
        HttpHeaders headers = defaultHeaders();
        headers.add(WWW_AUTHENTICATE, String.format(WWW_AUTHENTICATE_TEMPLATE, error, desc));
        return headers;
    }
    
    public static HttpHeaders defaultHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
    
    public static Map<String, Object> fail(Map<String, Object> data){
        return jsend(FAIL, data);
    }
    
	public static Map<String, Object> fail(String message) {
        return jsend(FAIL, message);
	}
	
    public static Map<String, Object> error(String message){
        return jsend(ERROR, message);
    }    

    public static Map<String, Object> error(String message, Map<String, String> data){
        return jsend(ERROR, message, data);
    }

    public static Map<String, Object> jsend(String status, Map<String, Object> data){
        return jsend(status, null, data);
    }    
    public static Map<String, Object> jsend(String status, String message){
        return jsend(status, message, null);
    }
    
    public static Map<String, Object> jsend(String status, String message, Map<String, ?> data){
        Map<String, Object> jsend = new LinkedHashMap<String, Object>();
        jsend.put(STATUS, status);
        if(message != null){
        	if(MESSAGE_SOURCE != null){
        		message = MESSAGE_SOURCE.getMessage(message, null, message, Locale.US);
        	}
            jsend.put(MESSAGE, message);            
        }
        if(data != null){
            jsend.put(DATA, getMessages(data));        		
        }
        
        return jsend;
    }
    
    private static Map<String, ?> getMessages(Map<String, ?> data){
    	if(data != null && MESSAGE_SOURCE != null){
    		Map<String, Object> result = new LinkedHashMap<String, Object>();
            for(Map.Entry entry :data.entrySet()){
                Object key = entry.getKey();
                Object value = entry.getValue();
                if(value instanceof String){
                    String s = (String) value;
                    value = MESSAGE_SOURCE.getMessage(s, null, s, Locale.US);
                }
                result.put((String)key, value);
            }
    		return result;
    	}else{
    		return data;
    	}
    }

	public synchronized static void setMessageSource(MessageSource messageSource) {
		LOGGER.info("Message Source injected: " + messageSource.toString());
        MESSAGE_SOURCE = messageSource;
	}

    

}
