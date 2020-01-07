package com.stl.iminds.commons.security.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stl.core.base.authentication.AuthenticationData;
import com.stl.core.base.cache.CacheManager;
import com.stl.core.base.logger.ILogger;
import com.stl.core.base.logger.LogManager;
import com.stl.core.base.utils.Response;
import com.stl.core.base.utils.constant.BaseResponseCode;
import com.stl.core.commons.authentication.KeyCloackAuthenticationData;
import com.stl.core.commons.exception.STLException;
import com.stl.iminds.commons.security.resource.AuthorizationDTO;
import com.stl.iminds.commons.security.serviceimpl.AuthenticationServiceImpl;
import com.stl.iminds.commons.security.utils.KeyCloackConfigurationUtil;
import com.stl.iminds.commons.security.utils.CommonConstant;


@SuppressWarnings({ "rawtypes"})
@RestController
public class AuthenticationController {

	private static final ILogger LOGGER = LogManager.getLogger();

	private static final String CLASSNAME="AuthenticationController";

	@Autowired
	private AuthenticationServiceImpl authenticationServiceImpl;


	@Autowired
	Environment env;


	@Autowired
	KeyCloackConfigurationUtil keyCloackConfigurationUtil;


	@SuppressWarnings("unchecked")
	@CrossOrigin
	@RequestMapping(value = "/generateToken", method = {org.springframework.web.bind.annotation.RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Response generateToken(@RequestBody AuthenticationData authenticationRequest,HttpServletRequest httpServletRequest) throws IOException {
		String strMethodName="generateToken";
		if(LOGGER.isInfoEnabled()) LOGGER.infoLog(CLASSNAME,strMethodName," Login request for username :" + authenticationRequest.getUserName());
		CacheManager.reloadCache("QUERY_CONFIG_CACHE");
		Response response = new Response(BaseResponseCode.SUCCESS_RESPONSE_CODE,BaseResponseCode.SUCCESS_RESPONSE_MESSAGE,null);
		try
		{
			/**GENERATE TOKEN*/
			String ipAddress = httpServletRequest.getHeader("X-FORWARDED-FOR");
			if (ipAddress == null) {
				ipAddress = httpServletRequest.getRemoteAddr();
			}

			response = authenticationServiceImpl.generateToken(authenticationRequest, ipAddress);

		}
		catch( STLException e)
		{
			LOGGER.errorLog(CLASSNAME,strMethodName," Exception Occured ",e);
			response.setResponseMessage(e.getErrorMessage());
			response.setResponseCode(e.getErrorCode());
			response.setResponseObject(null);
		}
		catch( Exception e)
		{
			LOGGER.errorLog(CLASSNAME,strMethodName," Exception Occured ", e);
			response.setResponseMessage(e.getMessage());
			response.setResponseObject(null);
		}

		if(LOGGER.isInfoEnabled()) LOGGER.infoLog(CLASSNAME,strMethodName," Response Returned" + response.getResponseCode());
		return response;

	}


	@CrossOrigin
	@RequestMapping(value = "/isSSOEnabled", method = {
			org.springframework.web.bind.annotation.RequestMethod.GET })
	public Response isSSOEnabled(HttpServletRequest httpServletRequest) throws IOException {

		String strMethodName = "isSSOEnabled";
		Response response = new Response(BaseResponseCode.SUCCESS_RESPONSE_CODE,BaseResponseCode.SUCCESS_RESPONSE_MESSAGE,null);

		if(LOGGER.isInfoEnabled()) LOGGER.infoLog(CLASSNAME,strMethodName," Entered");

		try
		{
			boolean bFlag = KeyCloackConfigurationUtil.isSSOEnabled();
			response.setResponseObject(bFlag);
		}
		catch( STLException e)
		{
			LOGGER.errorLog(CLASSNAME,strMethodName," Exception Occured ",e);
			response.setResponseMessage(e.getErrorMessage());
			response.setResponseCode(e.getErrorCode());
		}
		catch( Exception e)
		{
			LOGGER.errorLog(CLASSNAME,strMethodName," Exception Occured " ,e);
			response.setResponseMessage(e.getMessage());
		}

		if(LOGGER.isInfoEnabled()) LOGGER.infoLog(CLASSNAME,strMethodName," Exited " +  response);
		return response;
	}

	@CrossOrigin
	@RequestMapping(value = "/loadKeyCloakConfiguration", method = {
			org.springframework.web.bind.annotation.RequestMethod.GET })
	public Response<KeyCloackAuthenticationData> loadKeyCloakConfiguration(HttpServletRequest httpServletRequest) throws IOException {

		String strMethodName = "loadKeyCloakConfiguration";
		Response response = new Response(BaseResponseCode.SUCCESS_RESPONSE_CODE,BaseResponseCode.SUCCESS_RESPONSE_MESSAGE,null);

		if(LOGGER.isInfoEnabled()) LOGGER.infoLog(CLASSNAME,strMethodName," Entered");

		try
		{
			KeyCloackAuthenticationData keyCloackAuthenticationData = KeyCloackConfigurationUtil.getKeycloakConfigurations();
			response.setResponseObject(keyCloackAuthenticationData);
		}
		catch( STLException e)
		{
			LOGGER.errorLog(CLASSNAME,strMethodName," Exception Occured ",e);
			response.setResponseMessage(e.getErrorMessage());
			response.setResponseCode(e.getErrorCode());
		}
		catch( Exception e)
		{
			LOGGER.errorLog(CLASSNAME,strMethodName," Exception Occured ",e);
			response.setResponseMessage(e.getMessage());
		}

		if(LOGGER.isInfoEnabled()) LOGGER.infoLog(CLASSNAME,strMethodName," Exited " +  response);
		return response;
	}


	@CrossOrigin
	@RequestMapping(value = "/authorizeUser", method = {org.springframework.web.bind.annotation.RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Response<HashMap<String,AuthorizationDTO>> authorizeUser(@RequestBody AuthenticationData authenticationRequest,HttpServletRequest httpServletRequest) throws IOException {
		String strMethodName="generateToken";
		if(LOGGER.isInfoEnabled()) LOGGER.infoLog(CLASSNAME,strMethodName," Authorization request for username :" + authenticationRequest.getUserName());

		Response response = authenticationServiceImpl.authorizeUser(authenticationRequest);

		if(LOGGER.isInfoEnabled()) LOGGER.infoLog(CLASSNAME,strMethodName," Response Returned" + response.getResponseCode());
		return response;

	}


	@CrossOrigin
	@RequestMapping(value = "/invalidRequest")
	public Response inValidRequest(HttpServletRequest httpServletRequest,HttpServletResponse response)
	{
		Response response1 = new Response(BaseResponseCode.UNKNOWN_ERROR_CODE,BaseResponseCode.UNKNOWN_ERROR_MESSAGE);

		if(httpServletRequest.getAttribute(CommonConstant.INVALID_REQUEST_ERROR_ATTRIBUTE) != null )
		{
			response1.setResponseCode(CommonConstant.INVALID_REQUEST_ERROR_CODE);
			response1.setResponseMessage((String)httpServletRequest.getAttribute(CommonConstant.INVALID_REQUEST_ERROR_ATTRIBUTE));
		}
			
		return response1;		

	}


}
