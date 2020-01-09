package com.stl.iminds.commons.exception;

import com.stl.core.commons.exception.ExceptionType;

/**
 * The Enum ExceptionType.
 */
public enum TechnicalExceptionType  implements ExceptionType{

	TECHNICAL(9999L, "technical.error"),
	SQL(9999L, "sql.error");
	
	private long errorCode;
	private String errorMsgId;

	TechnicalExceptionType(long code, String message) {
        this.errorCode = code;
        this.errorMsgId = message;
    }

    /**
     *
     * @return
     */
    public String getMessageMsgId() {
        return errorMsgId;
    }

	public long getErrorCode() {
		return errorCode;
	}

}
