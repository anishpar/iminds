package com.stl.iminds.commons.security.utils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.stl.core.base.logger.ILogger;
import com.stl.core.base.logger.LogManager;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class JWTTokenAdapter implements Serializable {

	private static final ILogger logger = LogManager.getLogger();
	private static final String CLASSNAME="JWTTokenAdapter";

	static final String CLAIM_KEY_USERNAME = "sub";
	static final String CLAIM_KEY_AUDIENCE = "audience";
	static final String CLAIM_KEY_CREATED = "created";

	private static final String AUDIENCE_MOBILE = "mobile";
	private static final String AUDIENCE_TABLET = "tablet";

	private static final String REQUEST_IP_ADDRESS = "ip_address";
	private static final String GRANTED_AUTHORITIES = "granted_authorities";
	private static final String CLAIM_KEY_USERID = "userid";
	
	public static final String EXCEPTION_OCCURED = "Exception occured ";

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long expiration;

	public String getUsernameFromToken(String token) {
		String username;
		try {
			final Claims claims = getClaimsFromToken(token);
			username = claims!=null?(claims.getSubject()):null;
		} catch (Exception e) {
			logger.errorLog(CLASSNAME,"getUsernameFromToken()",EXCEPTION_OCCURED +e , e);
			username = null;
		}
		return username;
	}

	public String getIpAddressFromToken(String token) {
		String ipAddress;
		try {
			final Claims claims = getClaimsFromToken(token);
			ipAddress = claims!=null?((String) claims.get(REQUEST_IP_ADDRESS)):null;
		} catch (Exception e) {
			logger.errorLog(CLASSNAME,"getIpAddressFromToken()",EXCEPTION_OCCURED +e,e);
			ipAddress = null;
		}
		return ipAddress;
	}

	@SuppressWarnings("unchecked")
	public List<Map<GrantedAuthority, String>> getAuthoritiesFromToken(String token) {
		List<Map<GrantedAuthority, String>> authorities = null;
		try {
			final Claims claims = getClaimsFromToken(token);
			if(claims!=null){
				authorities = (List<Map<GrantedAuthority, String>>) claims.get(GRANTED_AUTHORITIES);
			}
		} catch (Exception e) {
			logger.errorLog(CLASSNAME,"getAuthoritiesFromToken()",EXCEPTION_OCCURED+e,e);
			authorities = null;
		}
		return authorities;
	}

	public Date getCreatedDateFromToken(String token) {
		Date created;
		try {
			final Claims claims = getClaimsFromToken(token);
			created = claims!=null?(new Date((Long) claims.get(CLAIM_KEY_CREATED))):null;
		} catch (Exception e) {
			logger.errorLog(CLASSNAME,"getCreatedDateFromToken()",EXCEPTION_OCCURED+e,e);
			created = null;
		}
		return created;
	}


	public Date getExpirationDateFromToken(String token) {
		Date expirationDate;
		try {
			final Claims claims = getClaimsFromToken(token);
			expirationDate = claims!=null?(claims.getExpiration()):null;
		} catch (Exception e) {
			logger.errorLog(CLASSNAME,"getExpirationDateFromToken()",EXCEPTION_OCCURED+e,e);
			expirationDate = null;
		}
		return expirationDate;
	}

	public String getAudienceFromToken(String token) {
		String audience;
		try {
			final Claims claims = getClaimsFromToken(token);
			audience = claims!=null?((String) claims.get(CLAIM_KEY_AUDIENCE)):null;
		} catch (Exception e) {
			logger.errorLog(CLASSNAME,"getAudienceFromToken()",EXCEPTION_OCCURED+e,e);
			audience = null;
		}
		return audience;
	}

	private Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			logger.errorLog(CLASSNAME,"getClaimsFromToken()",EXCEPTION_OCCURED +e,e);
			claims = null;
		}
		return claims;
	}

	private Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + expiration * 1000);
	}

	public Boolean isTokenExpired(String token) {
		Boolean isExpired = false;

		Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		return isExpired;
	}
	private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
		return (lastPasswordReset != null && created.before(lastPasswordReset));
	}


	private Boolean ignoreTokenExpiration(String token) {
		String audience = getAudienceFromToken(token);
		return (AUDIENCE_TABLET.equals(audience) || AUDIENCE_MOBILE.equals(audience));
	}

	public String generateToken(JwtUser userDetails, String ipAddress) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
		claims.put(CLAIM_KEY_USERID, userDetails.getId());
		claims.put(CLAIM_KEY_CREATED, new Date());
		claims.put(REQUEST_IP_ADDRESS, ipAddress);
		return generateToken(claims);
	}

	String generateToken(Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims).setExpiration(generateExpirationDate())
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
		final Date created = getCreatedDateFromToken(token);
		return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
				&& (!isTokenExpired(token) || ignoreTokenExpiration(token));
	}

	public String refreshToken(String token) {
		String refreshedToken;
		try {
			final Claims claims = getClaimsFromToken(token);
			if(claims!=null){
				claims.put(CLAIM_KEY_CREATED, new Date());
				refreshedToken = generateToken(claims);
			}
			else 
				refreshedToken = null;
		} catch (Exception e) {
			logger.errorLog(CLASSNAME,"refreshToken()",EXCEPTION_OCCURED +e);
			refreshedToken = null;
		}
		return refreshedToken;
	}

	public Boolean validateToken(String token, JwtUser userDetails, String ipAddress, String tokenIpAddress) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && tokenIpAddress.equals(ipAddress));
	}
}