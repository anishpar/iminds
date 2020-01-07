package com.stl.iminds.commons.security.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.stl.core.base.logger.ILogger;
import com.stl.core.base.logger.LogManager;
import com.stl.core.commons.authentication.KeyCloackAuthenticationData;

@Component
public class KeyCloackConfigurationUtil {
	
	
	private static Environment env;
		
	private static final ILogger LOGGER = LogManager.getLogger();
	private static final String CLASS_NAME = "KeyCloackConfigurationUtil";
	private static KeyCloackAuthenticationData keycloakConfigurationVO;

	
	@Autowired
	public void setEnvironment(Environment env){
		KeyCloackConfigurationUtil.env = env;	//NOSONAR
	}
	

	public static KeyCloackAuthenticationData getKeycloakConfigurations() {
		String methodName = "getKeycloakConfigurations";
		
		if(keycloakConfigurationVO == null) {
			keycloakConfigurationVO = new KeyCloackAuthenticationData();
			keycloakConfigurationVO.setAuthServerURL(env.getProperty(SecurityConstant.SSO_URL));
			keycloakConfigurationVO.setClientId(env.getProperty(SecurityConstant.SSO_CLIENT_ID));
			keycloakConfigurationVO.setSecret(env.getProperty(SecurityConstant.SSO_SECRET));
			keycloakConfigurationVO.setIsSSOEnabled(env.getProperty(SecurityConstant.SSO_AVAILABILITY_IN_SYSTEM));
			keycloakConfigurationVO.setRealm(env.getProperty(SecurityConstant.SSO_REALM));
			keycloakConfigurationVO.setRedirectURL(env.getProperty(SecurityConstant.SSO_REDIRECT_URL));
			keycloakConfigurationVO.setAdminClient(env.getProperty(SecurityConstant.SSO_ADMIN_CLIENT));
		
			if(LOGGER.isInfoEnabled()) 
				LOGGER.infoLog(CLASS_NAME, methodName, "Fethed keycloak configurations from system parameters, setting to KeycloakConfigurationVO.");
			if(LOGGER.isDebugEnabled()) 
				LOGGER.debugLog(CLASS_NAME, methodName, "Fethed keycloak configurations : " + keycloakConfigurationVO);
		}
		return keycloakConfigurationVO;
	}
	
	public static boolean isSSOEnabled() {
		String methodName = "isSSOEnabled";
		if(LOGGER.isInfoEnabled()) 
			LOGGER.infoLog(CLASS_NAME, methodName, "Fething if SSO is enabled in system.");
		boolean isSSOEnabled = !StringUtils.isEmpty(env.getProperty(SecurityConstant.SSO_AVAILABILITY_IN_SYSTEM))
				&& ("true").equals(env.getProperty(SecurityConstant.SSO_AVAILABILITY_IN_SYSTEM)) ? true : false;
		if(LOGGER.isInfoEnabled()) 
			LOGGER.infoLog(CLASS_NAME, methodName, "SSO is enabled in system? " + isSSOEnabled);

		return isSSOEnabled;
	}
	
}
