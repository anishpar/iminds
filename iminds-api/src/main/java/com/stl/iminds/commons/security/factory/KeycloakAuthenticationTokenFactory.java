
package com.stl.iminds.commons.security.factory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.stl.core.commons.authentication.KeyCloackUtil;
import com.stl.core.commons.exception.STLExceptionHelper;
import com.stl.core.commons.logger.DefaultLogger;
import com.stl.iminds.commons.exception.BusinessExceptionType;
import com.stl.iminds.commons.exception.NotificationEntityType;
import com.stl.iminds.commons.exception.NotificationException;
import com.stl.iminds.commons.security.utils.SecurityConstant;
import com.stl.iminds.commons.security.utils.CommonConstant;



@Component
public class KeycloakAuthenticationTokenFactory {


	@Autowired
	private Environment env;

	private static final String CLASSNAME = "KeycloakAuthenticationTokenFactory";
	private static final DefaultLogger LOGGER = new DefaultLogger();


	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		String methodName = "doFilterInternal";
		String authToken = request.getHeader("authorization");
		String requestPath = request.getRequestURI().substring(request.getContextPath().length());
		String ssoRealm = env.getProperty(SecurityConstant.SSO_REALM);
		String baseURL = env.getProperty(SecurityConstant.SSO_URL);
		String resource = env.getProperty(SecurityConstant.SSO_CLIENT_ID);

		try
		{

			Map<String, Object> clientCredentials = new HashMap<>();
			clientCredentials.put("secret", env.getProperty(SecurityConstant.SSO_SECRET));

			if(StringUtils.isEmpty(authToken) && !isExcludedApi(requestPath)){
				LOGGER.errorLog( CLASSNAME, methodName," Token is null/empty or the api: " + requestPath		);
				request.setAttribute(CommonConstant.INVALID_REQUEST_ERROR_ATTRIBUTE, CommonConstant.INVALID_REQUEST_AUTH_TOKEN_MISSING);
				throw STLExceptionHelper.throwException(NotificationException.class,NotificationEntityType.AUTHENTICATION, BusinessExceptionType.MISSING_MANDATORY_PARAMETERS, CommonConstant.AUTH_TOKEN );

			}
			if(null != authToken && !authToken.isEmpty())
			{

				if(!KeyCloackUtil.isAuthTokenActive(authToken,baseURL, ssoRealm, resource,clientCredentials))
				{
					LOGGER.errorLog( CLASSNAME, methodName,"KeycloakUtil.isAuthTokenActive returned false");
					request.setAttribute(CommonConstant.INVALID_REQUEST_ERROR_ATTRIBUTE, CommonConstant.INVALID_REQUEST_AUTH_TOKEN_INVALID);
					throw STLExceptionHelper.throwException(NotificationException.class,NotificationEntityType.AUTHENTICATION, BusinessExceptionType.INVALID_INPUTS, "Autorization Token" );
				}
			}
			


		}
		catch(Exception e)
		{
			LOGGER.errorLog( CLASSNAME, methodName, e.getMessage(),e);
			RequestDispatcher rd=request.getRequestDispatcher("/invalidRequest");  
			rd.forward(request, response); 
			return;
		}
		String requestId = UUID.randomUUID().toString();
		MDC.put("RequestId", requestId);

		// setting request id in request attribute for auditHistory
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		requestAttributes.setAttribute("USER_REQUEST_ID", requestId, RequestAttributes.SCOPE_REQUEST);
		chain.doFilter(request, response);

	}		



	private boolean isExcludedApi(String requestPath){
		boolean isApiExcluded = false;
		List<String> apiList = new ArrayList<>();
		apiList.add(SecurityConstant.GENERATE_TOKEN_API);
		apiList.add(SecurityConstant.SSO_ENABLED_API);
		apiList.add(SecurityConstant.HEALTH_CHECK_API);
		

		if(apiList.contains(requestPath)  ){
			isApiExcluded =  true;
		}
		return isApiExcluded;
	}
}