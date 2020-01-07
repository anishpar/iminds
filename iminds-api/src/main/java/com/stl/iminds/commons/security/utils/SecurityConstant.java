package com.stl.iminds.commons.security.utils;

public class SecurityConstant
{
	
	public static final String SSO_URL = "sso.url";
	public static final String SSO_CLIENT_ID= "sso.clientId";
	public static final String SSO_SECRET = "sso.secret";
	public static final String SSO_AVAILABILITY_IN_SYSTEM = "sso.available";
	public static final String SSO_REALM = "sso.realm";
	public static final String SSO_REDIRECT_URL = "sso.redirectUrl";
	public static final String SSO_ADMIN_CLIENT = "sso.admin.client";
	
	public static final String ENCRYPTION_TYPE_VALUE = "BCRYPT";
	public static final String ENCRYPTION_TYPE_PROPERTY = "login.encryption.type";
	
	public static final String GENERATE_TOKEN_API = "/generateToken";
	public static final String SSO_ENABLED_API = "/isSSOEnabled";
	public static final String HEALTH_CHECK_API = "/healthCheck";	
	
	private SecurityConstant()
	{
		
	}
	
}