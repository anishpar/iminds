package com.stl.iminds.commons.utils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stl.core.base.logger.ILogger;
import com.stl.core.base.logger.LogManager;
import com.stl.core.commons.exception.ExceptionType;
import com.stl.core.commons.exception.STLExceptionHelper;
import com.stl.iminds.commons.exception.BusinessExceptionType;
import com.stl.iminds.commons.exception.NotificationEntityType;
import com.stl.iminds.commons.exception.NotificationException;
import com.stl.iminds.commons.security.utils.CommonConstant;

public class CommonUtility {
	
	private static ObjectMapper objectMapperForFromJson = null;
	private static final ILogger LOGGER = LogManager.getLogger();
	private static final String CLASSNAME = "CommonUtility";
	
	public static String toJson(Object obj) {
		try {
			ObjectMapper objectMapperForToJson = new ObjectMapper();
			objectMapperForToJson.setSerializationInclusion(Include.NON_NULL);
			objectMapperForToJson.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
			objectMapperForToJson.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
			return objectMapperForToJson.writeValueAsString(obj);
		}catch(JsonProcessingException e) {
			LOGGER.errorLog(CLASSNAME,"toJson"," Exception Occured ", e);
			throw STLExceptionHelper.throwException(NotificationException.class,null, BusinessExceptionType.TECHNICAL_FAILURE);
		}
	}
	
	public static <T> T fromJson(String json, Class<T> class1)
	{
		if (objectMapperForFromJson == null) {
			objectMapperForFromJson = new ObjectMapper();
			objectMapperForFromJson.setSerializationInclusion(Include.NON_NULL);
			objectMapperForFromJson.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
			objectMapperForFromJson.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		}
		
		try{
			return json != null ? objectMapperForFromJson.readValue(json, class1) : null;
		}
		catch(IOException ex){
			LOGGER.errorLog(CLASSNAME," fromJson "," Exception Occured ", ex);
			throw STLExceptionHelper.throwException(NotificationException.class,null, BusinessExceptionType.TECHNICAL_FAILURE);
		}
	}
	
	public static Date getSystemDate()
	{
		Calendar calCurrent = Calendar.getInstance();

		return calCurrent.getTime();
	}
	
	
	public static boolean validatePattern(String address, String pattern) {
		Pattern valPattern = Pattern.compile(pattern);
		if(!isNull(address)) {
			return valPattern.matcher(address).matches();
		}
		return false;
	}
	
	public static boolean validateField(String field) {
		if(field == null || field.isEmpty()) {
			return false;
		}else {
			return true;
		}
	}

	public static boolean validateFieldLength(String field, String fieldValue, int dbLength) {
		if(fieldValue != null && fieldValue.length() > dbLength) {
			throw STLExceptionHelper.throwException(NotificationException.class,NotificationEntityType.COMMON, BusinessExceptionType.DB_LENGTH_INVALID, field, String.valueOf(dbLength)); 
		}else {
			return true;
		}
	}

	public static ExceptionType validateName(String strName, int fieldSize, boolean requiredRegExCheck){
		if(strName == null || strName.isEmpty()){ 
			return BusinessExceptionType.MISSING_MANDATORY_PARAMETERS;
		}
		if(requiredRegExCheck && !strName.trim().matches(CommonConstant.RCHECKVALIDNAME_REGEX)){
			return BusinessExceptionType.SPECIAL_CHAR_MSG;
		}
		if(strName.trim().length() > fieldSize){
			return BusinessExceptionType.MAX_CHAR_ALLOWED;
		}
		return null;
	}
	
	public static boolean isNull(String str){
		return (str == null || str.trim().isEmpty() || CommonConstant.NULL.equals(str));
	}
	
	public static boolean specialCharCheck(String str,String regEx) {
		return str.trim().matches(CommonConstant.RCHECKVALIDNAME_REGEX);
	}
	
	public static boolean maxLengthCheck(String str,int length) {
		return str.trim().length() > length;
	}
	
	public static Date setStartTime(Date startDate){
		if(startDate == null)
			return null;
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		return calendar.getTime();
	}
	
	public static Date setEndTime(Date endDate){
		
		if(endDate == null)
			return null;

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(endDate);
		
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		
		return calendar.getTime();
	}

	public static java.sql.Date getSQLDate(Date date) {
		java.sql.Date returnObj = null;
		if(date != null) {
			returnObj = new java.sql.Date(date.getTime());   
		}
		return returnObj;
	}
	
	public static Date dateFromString(String date, String pattern) {
		Date dateReturn = null;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			dateReturn = sdf.parse(date);
		} catch (ParseException ex) {
			dateReturn = null;
		}
		return dateReturn;
	}
	
	public static String stringFromDate(Date date, String pattern) {
		String strReturn = null;
		try{
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			strReturn = sdf.format(date);
		} catch (IllegalArgumentException iae) { //NOSONAR
			strReturn = null;
		}
		return strReturn;
	}
}
