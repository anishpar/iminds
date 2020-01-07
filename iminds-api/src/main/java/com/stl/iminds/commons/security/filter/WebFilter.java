package com.stl.iminds.commons.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * XSS filter
 *
 * This class filters every request parameters for the suspicious character patterns and
 * alerts the user if found. Thus relaxing on the usage of special characters.
 *
 */
public class WebFilter extends OncePerRequestFilter {

	protected final Log logger = LogFactory.getLog(WebFilter.class);

	/**
	 *
	 * @param request
	 * @return
	 * @throws IOException 
	 * 
	 */

	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		HttpServletRequest requestWrapper = new HttpServletRequestWrapper(request);
		filterChain.doFilter(requestWrapper, response);
	}	
}