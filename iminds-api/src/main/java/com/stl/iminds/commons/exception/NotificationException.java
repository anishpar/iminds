package com.stl.iminds.commons.exception;

import com.stl.core.commons.exception.STLException;

public class NotificationException extends STLException{

	private static final long serialVersionUID = 1L;
	
	public NotificationException(Long errCode, String errMessage) {
		super(errCode, errMessage);
	}
	
}
