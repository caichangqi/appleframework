/*
 * AlreadyExistingEntityException.java
 *
 * 深圳广联赛讯有限公司
 *
 * Copyright (C) 2012 WONDERSHARE.COM
 *
 * All Right reserved
 * http://www.glsx.com.cn
 */
package com.appleframework.core.exception;
/**
 * Base class for all custom exception thrown in CloudFramework
 * @author Cruise.Xu
 * @date: 2012-10-15
 *
 */
public class AlreadyExistingEntityException extends RuntimeException {

	/**
     * uid used for serialization
     */
	private static final long serialVersionUID = -8449870076163210864L;

	/**
     * Default constructor
     */
    public AlreadyExistingEntityException() {
        super();
    }

    /**
     * Create a new AlreadyExistingEntityException from a message which explain
     * the nature of the Exception
     */
    public AlreadyExistingEntityException(String message) {
        super(message);
    }

    /**
     * Create a new AlreadyExistingEntityException from a message and a base
     * throwable exception
     */
    public AlreadyExistingEntityException(String message, Throwable throwable) {
        super(message, throwable);
    }
}

