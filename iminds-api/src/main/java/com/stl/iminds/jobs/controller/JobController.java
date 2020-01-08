package com.stl.iminds.jobs.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stl.core.base.logger.ILogger;
import com.stl.core.base.logger.LogManager;
import com.stl.core.base.utils.Response;
import com.stl.core.commons.exception.STLException;
import com.stl.core.commons.utils.CommonConstantCode;
import com.stl.iminds.commons.security.utils.CommonConstant;
import com.stl.iminds.jobs.resource.JobOpeningsDTO;
import com.stl.iminds.jobs.service.JobService;

@RestController
@CrossOrigin
@RequestMapping("job")
public class JobController {
	
	private static final String CLASSNAME = "JobController";
	private static final ILogger LOGGER = LogManager.getLogger();
	
	@Autowired
	JobService jobService;	
	
	/**
	 * End-point for creating job opening
	 * @return Response with JobOpeningsDTO	 
	 */    
    @PostMapping("/")
	public Response<JobOpeningsDTO> createJobOpenings(@Valid @RequestBody JobOpeningsDTO jobOpeningsDTO) {
		String strMethodName = "createJobOpenings";
		JobOpeningsDTO jobOpeningsResp = jobOpeningsDTO;
		try {
			
			if(LOGGER.isInfoEnabled()) LOGGER.infoLog(CLASSNAME, strMethodName, CommonConstant.METHOD_START_LOG);
			jobOpeningsResp= jobService.createJobOpenings(jobOpeningsDTO);
			return new Response<>(CommonConstantCode.SUCCESS_RESPONSE_CODE, CommonConstantCode.SUCCESS_RESPONSE_MESSAGE, jobOpeningsResp);
		}catch(STLException e) {
			LOGGER.errorLog(CLASSNAME, strMethodName, CommonConstant.METHOD_EXCEPTION_LOG, e);
			return new Response<>(e.getErrorCode(), e.getErrorMessage(), null);
		}
	}	
}
