/*
 * CrudType.java
 *
 * 深圳广联赛讯有限公司
 *
 * Copyright (C) 2012 WONDERSHARE.COM
 *
 * All Right reserved
 * http://www.glsx.com.cn
 */
package com.appleframework.persistent.hibernate.enums;

import org.hibernate.envers.RevisionType;

import com.appleframework.persistent.hibernate.exceptions.UnknownEnumValueException;

/**
 * Type of operation(CREATE, UPDATE, DELETE).
 * 
 * @author Cruise.Xu
 * @date: 2011-9-8
 * 
 */
public enum CrudType {
	CREATE, UPDATE, DELETE;

	public static CrudType from(RevisionType type) {
		if (type == null) {
			return null;
		} else if (type == RevisionType.ADD) {
			return CREATE;
		} else if (type == RevisionType.DEL) {
			return DELETE;
		} else if (type == RevisionType.MOD) {
			return UPDATE;
		}
		throw new UnknownEnumValueException(CrudType.class, type.name());
	}
}
