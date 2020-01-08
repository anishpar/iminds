package com.stl.iminds.jobs.service.impl;

import org.springframework.stereotype.Service;

import com.stl.core.base.logger.ILogger;
import com.stl.core.base.logger.LogManager;
import com.stl.iminds.commons.security.utils.CommonConstant;
import com.stl.iminds.jobs.resource.JobOpeningsDTO;
import com.stl.iminds.jobs.service.JobService;

@Service
public class JobServiceImpl implements JobService{
	
	private static final String CLASSNAME = "CommunicationMessageServiceImpl";
	private static final ILogger LOGGER = LogManager.getLogger();
	
	
	@Override
	public JobOpeningsDTO createJobOpenings(JobOpeningsDTO jobOpeningsDTO) {
		String strMethodName = "createJobOpenings";
	
		if(LOGGER.isDebugEnabled()) LOGGER.debugLog(CLASSNAME, strMethodName, CommonConstant.METHOD_START_LOG+ jobOpeningsDTO);

		
		
		if(LOGGER.isDebugEnabled()) LOGGER.debugLog(CLASSNAME, strMethodName, CommonConstant.METHOD_END_LOG+jobOpeningsDTO);
		return jobOpeningsDTO;
	}
}
