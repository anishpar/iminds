package com.stl.iminds.commons.security.serviceimpl;

import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stl.core.commons.logger.DefaultLogger;
import com.stl.iminds.commons.security.utils.JwtUser;

@Service
public class UserDetailService implements org.springframework.security.core.userdetails.UserDetailsService{
	private static final DefaultLogger logger = new DefaultLogger();
	private static final String CLASS_NAME = "UserDetailService";
	
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userName) {
		String method = "loadUserByUsername";
		JwtUser jwtUser = null;
		try{
			if(logger.isInfoEnabled()) logger.infoLog( CLASS_NAME, method,"Going to get user detail for username : "+userName);
			
			jwtUser = new JwtUser(String.valueOf("userName"), userName, "$2a$10$F8c7.4O83xBAYWDVh2aSt.nIMlbdqaH.xZzqUKrD.wrrZkjg1r9FW", null, true,new Date(), "admin@ec.com");
		}catch (Exception e) {
			logger.errorLog( CLASS_NAME, method, e.getMessage(),e);
		}
		
		if(jwtUser!=null && logger.isInfoEnabled()) {
			logger.infoLog( CLASS_NAME, method,"Completed " + jwtUser.getUsername());
		}
		
		return jwtUser;
	}
}
