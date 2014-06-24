/**
 * 
 */
package com.appleframework.web.springmvc.interceptor;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


/**
 * @author Cruise.Xu CSRF攻击识别
 */
public class CsrfInterceptor extends HandlerInterceptorAdapter {
	
	private static Logger logger = Logger.getLogger(CsrfInterceptor.class);
	private final static String CSRF_TOKEN = "csrfToken";

	@Override
	public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
			ModelAndView modelAndView) throws Exception {

		logger.debug("进入防止csrf攻击拦截");
		String method = httpServletRequest.getMethod();
		if ("GET".equals(method)) {
			if (modelAndView != null && !modelAndView.getViewName().contains("layout")) {
				String csrf = UUID.randomUUID().toString();
				httpServletRequest.getSession().setAttribute(CSRF_TOKEN, csrf);
			}
		} else {
			String csrf = httpServletRequest.getParameter(CSRF_TOKEN);
			String rcsrf = (String) httpServletRequest.getSession().getAttribute(CSRF_TOKEN);
			if (csrf == null || csrf.length() == 0 || !csrf.equals(rcsrf)) {
				httpServletResponse.sendError(403);
			} else {
				httpServletRequest.getSession().setAttribute(CSRF_TOKEN, UUID.randomUUID().toString());
			}
		}

	}

}