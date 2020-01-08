package com.stl.iminds.cache.dataprovider;

import java.util.HashMap;
import java.util.Map;

import com.stl.core.base.logger.ILogger;
import com.stl.core.base.logger.LogManager;
import com.stl.core.commons.cache.AbstractDataProvider;
import com.stl.iminds.cache.dto.InterviewTypeDto;

public class InterviewTypeDataProvider extends AbstractDataProvider {
	
	private static final String CLASSNAME="ChannelTypeDataProvider";
	private static final ILogger LOGGER = LogManager.getLogger();
	
	@Override
	public Map<String, InterviewTypeDto> getData(Long lTenantId) {
		String strMethodName="getData";
		if (LOGGER.isInfoEnabled()) LOGGER.infoLog(CLASSNAME, strMethodName, "Started");
		Map<String, InterviewTypeDto> mpInterviewTypeData = null;
		try {
			mpInterviewTypeData = new HashMap<>();
			InterviewTypeDto interviewTypeDto = new InterviewTypeDto();
			interviewTypeDto.setDescription("Screening Desc");
			interviewTypeDto.setName("Screening");
			mpInterviewTypeData.put("Screening", interviewTypeDto);
		} catch (Exception e) {
			LOGGER.errorLog(CLASSNAME, strMethodName,"Exception occurred while preparing cache " + strMethodName, e);
		}
		if (LOGGER.isInfoEnabled()) LOGGER.infoLog(CLASSNAME, strMethodName, "Ended");
		return mpInterviewTypeData;
	}

}
