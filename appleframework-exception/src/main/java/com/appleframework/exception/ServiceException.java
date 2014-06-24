/*
 * CloudframeworkException.java
 *
 * 深圳广联赛讯有限公司
 *
 * Copyright (C) 2012 WONDERSHARE.COM
 *
 * All Right reserved
 * http://www.glsx.com.cn
 */
package com.appleframework.exception;
/**
 * Base class for all custom exception thrown in CloudFramework
 * 
 * @author Cruise.Xu
 * @date: 2012-10-15
 *
 */
public class ServiceException extends Exception {
	
	/**
     * uid used for serialization
     */
	private static final long serialVersionUID = 7696865849245536841L;
	
	/**
     * Exception code 
     */
	private String code;
	
    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
     * Default constructor
     */
    public ServiceException() {
        super();
    }

    /**
     * Create a new CloudframeworkException from a message which explain the nature of
     * the Exception
     */
    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String code, String message) {
        super(message);
        this.code = code;
    }
    /**
     * Create a new CloudframeworkException from a message and a base throwable
     * exception
     */
    public ServiceException(String message, Throwable throwable) {
        super(message, throwable);
    }
    
    public ServiceException(String code, String message, Throwable throwable) {
        super(message, throwable);
        this.code = code;
    }
    
    public ServiceException(ServiceErrorType serviceErrorType) {
		super(ServiceErrorType.msgValue(serviceErrorType));
		this.code = ServiceErrorType.codeValue(serviceErrorType);
	}
	
	public ServiceException(ServiceErrorType serviceErrorType, Throwable throwable) {
		super(ServiceErrorType.msgValue(serviceErrorType), throwable);
		this.code = ServiceErrorType.codeValue(serviceErrorType);
	}
	
	public ServiceException(ServiceErrorType serviceErrorType, String message, Throwable throwable) {
		super(ServiceErrorType.msgValue(serviceErrorType) + ":" + message, throwable);
		this.code = ServiceErrorType.codeValue(serviceErrorType);
	}
}

