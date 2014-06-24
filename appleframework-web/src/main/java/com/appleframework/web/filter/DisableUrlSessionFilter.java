package com.appleframework.web.filter;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 * @author Cruise.Xu 修正url自动添加JSESSIONID
 */
public class DisableUrlSessionFilter implements Filter {

	/**
	 * Filters requests to disable URL-based session identifiers.
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// skip non-http requests
		if (!(request instanceof HttpServletRequest)) {
			chain.doFilter(request, response);
			return;
		}

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		// clear session if session id in URL
		if (httpRequest.isRequestedSessionIdFromURL()) {
			HttpSession session = httpRequest.getSession();
			if (session != null)
				session.invalidate();
		}

		// wrap response to remove URL encoding
		HttpServletResponseWrapper wrappedResponse = new HttpServletResponseWrapper(httpResponse) {
			
			@Override
			public String encodeRedirectUrl(String url) {
				return url;
			}

			@Override
			public String encodeRedirectURL(String url) {
				return url;
			}

			@Override
			public String encodeUrl(String url) {
				return url;
			}

			@Override
			public String encodeURL(String url) {
				return url;
			}
		};

		// process next request in chain
		chain.doFilter(request, wrappedResponse);
	}

	/**
	 * Unused.
	 */
	public void init(FilterConfig config) throws ServletException {
	}

	/**
	 * Unused.
	 */
	public void destroy() {
	}
}
