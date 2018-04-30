package com.beatus.goodbyeq.email.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityException extends RuntimeException{
    
    private static final long serialVersionUID = 1L;
    protected HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    private ResponseEntity<Object> response;

    public ResponseEntityException(String message, Throwable t){
        super(message, t);
    }
    
    public ResponseEntityException(String message){
        super(message);
    }
    
    public ResponseEntityException(ResponseEntity<Object> response, String message){
    	super(message);
        this.response = response;
    }
    
    protected Object body(){
        return ExceptionHandlerUtils.error("Backend.Server.Error");        
    }
    protected HttpHeaders headers(){
        return ExceptionHandlerUtils.defaultHeaders();
    }    
    public ResponseEntity<Object> responseEntity(){
        if(response != null){
            return response;
        }else{
            return new ResponseEntity<Object>(body(), headers(), status);
        }
    }

}
