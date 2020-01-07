package com.stl.iminds.commons.security.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.stl.iminds.commons.exception.NotificationException;
import com.stl.iminds.commons.security.factory.JwtAuthenticationTokenFactory;
import com.stl.iminds.commons.security.factory.KeycloakAuthenticationTokenFactory;
import com.stl.iminds.commons.security.utils.KeyCloackConfigurationUtil;


public class BasicAuthenticationTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtAuthenticationTokenFactory jwtAuthenticationTokenFactory;

	@Autowired
	private KeycloakAuthenticationTokenFactory keycloakAuthenticationTokenFactory;


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException,NotificationException {
		if(KeyCloackConfigurationUtil.isSSOEnabled()) {
			keycloakAuthenticationTokenFactory.doFilterInternal(request, response, chain);
		} else {
			jwtAuthenticationTokenFactory.doFilterInternal(request, response, chain);
		}
	}


	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		//Set<String> skipUrls = new HashSet<>(Arrays.asList("/iminds/STL/**", "/iminds/stlconfig/**","/iminds/swagger/**", "/iminds/v2/api-docs/**", "/iminds/loadKeyCloakConfiguration"));
		Set<String> skipUrls = new HashSet<>(Arrays.asList("/iminds/STL/stlconfig/**","/iminds/swagger/**", "/iminds/v2/api-docs/**", "/iminds/loadKeyCloakConfiguration"));
		AntPathMatcher pathMatcher = new AntPathMatcher();

		return skipUrls.stream().anyMatch(p -> pathMatcher.match(p,request.getRequestURI()));
	}


}