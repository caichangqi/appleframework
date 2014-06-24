package com.appleframework.web.session;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Session提供者
 * 
 * @author xusm
 * 
 */
public interface SessionProvider {
	
	public Serializable getAttribute(HttpServletRequest request, String name);

	public void setAttribute(HttpServletRequest request, HttpServletResponse response, String name, Serializable value);
	
	public void removeAttribute(HttpServletRequest request, HttpServletResponse response, String name);

	public String getSessionId(HttpServletRequest request, HttpServletResponse response);

	public void logout(HttpServletRequest request, HttpServletResponse response);
	
}
