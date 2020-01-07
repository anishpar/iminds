package com.stl.iminds.commons.security.serviceimpl;

import java.util.HashMap;

import org.jboss.logging.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.stl.core.base.authentication.AuthenticationData;
import com.stl.core.base.authentication.IAuthentication;
import com.stl.core.base.logger.ILogger;
import com.stl.core.base.logger.LogManager;
import com.stl.core.base.utils.Response;
import com.stl.core.base.utils.constant.BaseResponseCode;
import com.stl.core.commons.authentication.KeyCloackAuthenticationData;
import com.stl.core.commons.authentication.KeyCloakAdapter;
import com.stl.core.commons.exception.STLExceptionHelper;
import com.stl.iminds.commons.exception.BusinessExceptionType;
import com.stl.iminds.commons.exception.NotificationEntityType;
import com.stl.iminds.commons.exception.NotificationException;
import com.stl.iminds.commons.security.resource.AuthorizationDTO;
import com.stl.iminds.commons.security.utils.JWTTokenAdapter;
import com.stl.iminds.commons.security.utils.JwtUser;
import com.stl.iminds.commons.security.utils.KeyCloackConfigurationUtil;

@Service
public class AuthenticationServiceImpl implements IAuthentication {

	private static final ILogger LOGGER = LogManager.getLogger();
	private static final String  CLASSNAME = "AuthenticationServiceImpl";

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JWTTokenAdapter jwtTokenAdapter;



	public Response authenticateUser(AuthenticationData authenticationData) throws Exception
	{
		return null;
	}

	public Response<HashMap<String,AuthorizationDTO>> authorizeUser(AuthenticationData authenticationData) {
		String strMethodName = "authorizeUser()";

		if(LOGGER.isInfoEnabled()) LOGGER.infoLog(CLASSNAME,strMethodName,"Entered");
		Response<HashMap<String,AuthorizationDTO>> response = new Response(BaseResponseCode.SUCCESS_RESPONSE_CODE,BaseResponseCode.SUCCESS_RESPONSE_MESSAGE);
		
		if(authenticationData != null)
		{
			HashMap<String,AuthorizationDTO> mapAuthorization = prepateAuthorization();
			
			response.setResponseObject(mapAuthorization);
		}
		else
		{
			LOGGER.errorLog(CLASSNAME, strMethodName, "Authoriztion Parameters Are null");
			throw STLExceptionHelper.throwException(NotificationException.class,NotificationEntityType.AUTHENTICATION, BusinessExceptionType.MISSING_MANDATORY_PARAMETERS, "Username , Password" );
		}
		
		return response;

	}

	public Response logout(AuthenticationData authenticationData)
	{
		return null;
	}

	public Response generateToken(AuthenticationData authenticationRequest , String ipAddress ) throws CloneNotSupportedException
	{
		String strMethodName = "generateToken()";

		Response response = new Response(BaseResponseCode.SUCCESS_RESPONSE_CODE,BaseResponseCode.SUCCESS_RESPONSE_MESSAGE,null);

		if(KeyCloackConfigurationUtil.isSSOEnabled()){
			if(LOGGER.isInfoEnabled()) LOGGER.infoLog(CLASSNAME,strMethodName,"Generating token using KeyCloak");

			KeyCloakAdapter adapter = new KeyCloakAdapter();

			KeyCloackAuthenticationData authenticationData = KeyCloackConfigurationUtil.getKeycloakConfigurations();
			
			
			
			if(authenticationData != null)
			{
				KeyCloackAuthenticationData authenticationDataClone = (KeyCloackAuthenticationData)authenticationData.clone();
				
				authenticationDataClone.setUserName(authenticationRequest.getUserName());
				authenticationDataClone.setPassword(authenticationRequest.getPassword());
				authenticationDataClone.setAdditionalDetails(authenticationRequest.getAdditionalDetails());
				authenticationDataClone.setClientName(authenticationData.getClientId());
				Response keylcoackresponse = adapter.generateToken(authenticationDataClone);
				
				authenticationRequest.setToken(((KeyCloackAuthenticationData)keylcoackresponse.getResponseObject()).getToken());

				response.setResponseObject(authenticationRequest);

			}
			else
			{
				LOGGER.errorLog(CLASSNAME, strMethodName, "Keycloak related configuration can not be loaded");
				throw STLExceptionHelper.throwException(NotificationException.class,NotificationEntityType.AUTHENTICATION, BusinessExceptionType.MISSING_CONFIGURATION, "Keycloak" );
			}

		}
		else
		{
			if(LOGGER.isInfoEnabled()) LOGGER.infoLog(CLASSNAME,strMethodName,"Going to get default JWT token.");
			JwtUser jwtUser;

			final Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(),
							authenticationRequest.getPassword()));
			SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			jwtUser = (JwtUser) authentication.getPrincipal();


			MDC.put("userName", authenticationRequest.getUserName());
			final String token = jwtTokenAdapter.generateToken(jwtUser, ipAddress);
			authenticationRequest.setToken(token);
			response.setResponseObject(authenticationRequest);
		}

		return response;
	}

	private HashMap<String,AuthorizationDTO>  prepateAuthorization()
	{
		HashMap<String,AuthorizationDTO> mapAuthorization = new HashMap<>();
		
		AuthorizationDTO authorizationDTO = new AuthorizationDTO();
		
		authorizationDTO.setAlias("GENERATE_TOKEN_API");
		authorizationDTO.setName("GENERATE_TOKEN_API");
		authorizationDTO.setStatus("SHOW");
		authorizationDTO.setUrl("/login");
		
		
		mapAuthorization.put(authorizationDTO.getAlias(), authorizationDTO)	;	
		return mapAuthorization;
	}
}
