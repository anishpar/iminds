package com.stl.iminds.commons.security.utils;

import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class CommonConstant {

	public static final String SUCCESS = "SUCCESS";
	public static final String FAIL = "FAIL";
	public static final String FAILED = "FAILED";
	public static final String STALE = "STALE";
	public static final String ADMIN_STAFF_NAME = "Admin";
	public static final String ACCEPTED = "ACCEPTED";
	public static final String REJECTED = "REJECTED";
	public static final String SHOW = "SHOW";
	public static final String HIDE = "HIDE";
	public static final String ALL = "ALL";
	public static final String SMS_CHANNEL_ALIAS = "SMS";
	public static final String EMAIL_CHANNEL_ALIAS = "EMAIL";
	public static final String CHANNEL_ATTRIBUTE_VALIDATION_EXPR = "validationExp";
	public static final Pattern EMAIL_PATTERN = Pattern.compile("^([a-zA-Z0-9_\\.\\-])+@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$");
	public static final Pattern MOBILE_PATTERN = Pattern.compile("^[0-9]*");
	public static final String RCHECKVALIDNAME_REGEX = "^([a-zA-Z0-9][a-zA-Z0-9_ .]*)$";
	public static final String DATE_TIME_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";
	public static final String NULL = "null";
	public static final String DEFAULT_NOTIFICATION_LANGUAGE = "en";
	public static final String DEFAULT_IPADDRESS = "127.0.0.1"; //NOSONAR
	public static final String HTML_CONTENT_TYPE="text/html";
	public static final String COMMUNICATION_DATA_NULL = "Communication details data is null";
	public static final String SEPARATOR_SLASH = "/";
	public static final String QUESTION_MARK = "?";
	public static final String EQUAL_TO = "=";
	public static final String PERCENTAGE_SIGN = "%";
	public static final int MAXIMUM_LIMIT_FOR_GET_HISTORY = 1000;
	public static final String COMMUNICATION_MSG = "communicationMessage";
	
	public static final int MAXIMUM_TEMPLATE_NAME_LENGTH = 50;
	public static final int MAXIMUM_TEMPLATE_DESCRIPTION_LENGTH = 255;
	
	public static final String NAME = "name";
	public static final String ID="id";
	public static final String CLASS_NAME="className";
	public static final String DESCRIPTION = "description";
	public static final String MIME_TYPE="mineType";
	public static final String CHANNEL_ALIAS = "channelAlias";
	public static final String EVENT_ALIAS = "eventAlias";
	public static final String TEMPLATE_DETAILS = "templateDetails";
	public static final String ATTRIBUTE_NAME = "attributeName";
	public static final String ATTRIBUTE_VALUE = "attributeValue";
	
	public static final String SUBJECT = "subject";
	public static final String CONTENT = "content";
	public static final int DBLENGTH_50=50;
	public static final int DBLENGTH_255=255;
	public static final int DBLENGTH_10=10;
	
	public static final String STATUS = "status";

	public static final String COMMA=",";
	public static final String DOLLAR="$";
	public static final String RECIPIENTADDRESS ="RECIPIENTADDRESS";
	public static final String RECIPIENTTYPE ="RECIPIENTTYPE";
	public static final String ADMIN_STAFF_ID="STF0001";
	public static final String NOTIFICATION_SUCCESS_MESSAGE = "Notification send successfully.";
    public static final int CREATE_FLOW = 1;
	public static final int UPDATE_FLOW = 2;
	public static final String START="Started ";
	public static final String START_WITH="Started :: ";
	public static final String END="Ended ";
	public static final String END_WITH="Ended :: ";
	public static final DateTimeFormatter TRANSATIONID_DATE_FORMATE = DateTimeFormatter.ofPattern("yyMMddhhmmssSSS");
	public static final String CHANNELS = "channels";
	public static final String LANGUAGES = "languages";
	public static final String IMAGES = "images";
	public static final String EVENTS = "events";
	public static final String DISPATCH_STATUS = "DISPATCH_STATUS";
	public static final String REMAINING_EVENT_FOR_CONFIGURATION = "REMAINING_EVENTS_FOR_CONFIGURATION";
	public static final String EVENT_TAGS = "tags";
	public static final String RECEIPIENT_TYPE = "RECEIPIENT_TYPE";
	public static final String METHOD_START_LOG = "Started";
	public static final String METHOD_END_LOG = "Ended";
	public static final String METHOD_EXCEPTION_LOG = "exception occurred :";
	public static final String DEFAULT_EMAIL = "g5.bss@ec.com";
	
	/** Authentication Constants **/
	
	public static final String AUTH_TOKEN = "Authorizarion Token";
	public static final String INVALID_REQUEST_ERROR_ATTRIBUTE = "ERROR_REASON";
	public static final String INVALID_REQUEST_AUTH_TOKEN_MISSING = "Mandatory parameter Authorizarion is missing ";
	public static final String INVALID_REQUEST_AUTH_TOKEN_INVALID = "Authorization token is invalid ";
	
	public static final String UNABLE_TO_CONNECT_TO_MAIL_SERVER = "Unable to connect to mail server ";
	public static final String VALID_ADDRESS_UNABLE_TO_SEND_MESSAGE = "Valid address but unable to send message ";
	public static final String UNABLE_TO_SEND_MESSAGE = "Unable to send message";
	
	
	public static final long INVALID_REQUEST_ERROR_CODE = -500l;
	private CommonConstant() {	}
}
