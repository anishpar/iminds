package com.stl.iminds.commons.security.factory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.stl.core.base.logger.ILogger;
import com.stl.core.base.logger.LogManager;
import com.stl.core.commons.exception.STLExceptionHelper;
import com.stl.iminds.commons.exception.BusinessExceptionType;
import com.stl.iminds.commons.exception.NotificationEntityType;
import com.stl.iminds.commons.exception.NotificationException;
import com.stl.iminds.commons.security.utils.JWTTokenAdapter;
import com.stl.iminds.commons.security.utils.SecurityConstant;
import com.stl.iminds.commons.security.utils.CommonConstant;

@Component
public class JwtAuthenticationTokenFactory {

	@Autowired
	private JWTTokenAdapter jwtTokenAdapter;

	@Value("${jwt.header}")
	private String tokenHeader;

	@Value("${jwt.expiration}")
	private long expiration;

	@Value("${jwt.refresh}")
	private long refresh;


	private static final String CLASSNAME = "JwtAuthenticationTokenFactory";

	private static final ILogger LOGGER = LogManager.getLogger();

	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		String methodName = "doFilterInternal";
		String authToken = request.getHeader(this.tokenHeader);
		String requestPath = request.getRequestURI().substring(request.getContextPath().length());

		if(StringUtils.isEmpty(authToken) && !isExcludedApi(requestPath)){
			LOGGER.errorLog(CLASSNAME, methodName,"Token is empty and api is " + requestPath);
			request.setAttribute(CommonConstant.INVALID_REQUEST_ERROR_ATTRIBUTE, CommonConstant.INVALID_REQUEST_AUTH_TOKEN_MISSING);
			throw STLExceptionHelper.throwException(NotificationException.class,NotificationEntityType.AUTHENTICATION, BusinessExceptionType.MISSING_MANDATORY_PARAMETERS, CommonConstant.AUTH_TOKEN );

		}
		if(null != authToken && !authToken.isEmpty())
		{
			try {
				if(jwtTokenAdapter.isTokenExpired(authToken))
				{
					LOGGER.errorLog(CLASSNAME, methodName,"Token Expired");
					request.setAttribute(CommonConstant.INVALID_REQUEST_ERROR_ATTRIBUTE, CommonConstant.INVALID_REQUEST_AUTH_TOKEN_INVALID);
					throw STLExceptionHelper.throwException(NotificationException.class,NotificationEntityType.AUTHENTICATION, BusinessExceptionType.INVALID_INPUTS, CommonConstant.AUTH_TOKEN );

				}
				else 
				{

					Date tokenCreationdate = jwtTokenAdapter.getCreatedDateFromToken(authToken);
					if((tokenCreationdate.getTime() + refresh * 1000) - System.currentTimeMillis() < 0)
					{
						String refreshedToken = jwtTokenAdapter.refreshToken(authToken);
						response.addHeader("authorization",refreshedToken);
						response.setHeader("Access-Control-Expose-Headers", "token");
					}

					String requestId = UUID.randomUUID().toString();
					MDC.put("RequestId", requestId);

					// setting request id in request attribute for auditHistory
					RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
					requestAttributes.setAttribute("USER_REQUEST_ID", requestId, RequestAttributes.SCOPE_REQUEST);
					chain.doFilter(request, response);
				}

			}
			catch(java.lang.IllegalArgumentException e)
			{

				LOGGER.errorLog(CLASSNAME, methodName,"Token Expired",e);
				request.setAttribute(CommonConstant.INVALID_REQUEST_ERROR_ATTRIBUTE, CommonConstant.INVALID_REQUEST_AUTH_TOKEN_INVALID);
				throw STLExceptionHelper.throwException(NotificationException.class,NotificationEntityType.AUTHENTICATION, BusinessExceptionType.INVALID_INPUTS, CommonConstant.AUTH_TOKEN );

			}
		}

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