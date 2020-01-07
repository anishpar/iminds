package com.stl.iminds.commons.exception;

import com.stl.core.commons.exception.EntityType;

public enum NotificationEntityType implements EntityType{

	CACHE (101),
	AUTHENTICATION (102),
	CHANNEL (103),
	COMMUNICATION (104),
	TEMPLATE(105),
	CHANNELATTRIBUTE (106),
	IMAGE (107),
	EVENTCONFIGURATION (108),
	QUERY (109),
	REQUESTSTRING(110),
	ATTACHMENT(111),
	COMMON(112);
	private long entityCode;
	
	NotificationEntityType(long code){
		this.entityCode = code;
	}
	
	public long getEntityCode() {
		return entityCode;
	}
	
}
