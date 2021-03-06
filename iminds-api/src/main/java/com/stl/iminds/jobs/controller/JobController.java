package com.stl.iminds.jobs.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stl.core.base.logger.ILogger;
import com.stl.core.base.logger.LogManager;
import com.stl.core.base.utils.Response;
import com.stl.core.commons.exception.STLException;
import com.stl.core.commons.utils.CommonConstantCode;
import com.stl.iminds.candidate.resource.CandidatesDTO;
import com.stl.iminds.commons.security.utils.CommonConstant;
import com.stl.iminds.jobs.resource.JobOpeningChangeStatusDTO;
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
    
    /**
	 * End-point for updating job opening
	 * @return Response with JobOpeningsDTO	 
	 */    
    @PutMapping("/{id}")
	public Response<JobOpeningsDTO> updateJobOpenings(@PathVariable Long id, @RequestBody JobOpeningsDTO jobOpeningsDTO) {
		String strMethodName = "updateJobOpenings";
		JobOpeningsDTO jobOpeningsResp = jobOpeningsDTO;
		try {
			
			if(LOGGER.isInfoEnabled()) LOGGER.infoLog(CLASSNAME, strMethodName, CommonConstant.METHOD_START_LOG);
			jobOpeningsResp= jobService.updateJobOpenings(id,jobOpeningsDTO);
			return new Response<>(CommonConstantCode.SUCCESS_RESPONSE_CODE, CommonConstantCode.SUCCESS_RESPONSE_MESSAGE, jobOpeningsResp);
		}catch(STLException e) {
			LOGGER.errorLog(CLASSNAME, strMethodName, CommonConstant.METHOD_EXCEPTION_LOG, e);
			return new Response<>(e.getErrorCode(), e.getErrorMessage(), null);
		}
	}
    
    /**
	 * End-point for updating job opening
	 * @return Response with JobOpeningsDTO	 
	 */    
    @GetMapping("/{id}")
	public Response<JobOpeningsDTO> viewJobOpenings(@PathVariable Long id) {
		String strMethodName = "updateJobOpenings";
		try {
			
			if(LOGGER.isInfoEnabled()) LOGGER.infoLog(CLASSNAME, strMethodName, CommonConstant.METHOD_START_LOG);
			JobOpeningsDTO jobOpeningsResp= jobService.viewJobOpenings(id);
			return new Response<>(CommonConstantCode.SUCCESS_RESPONSE_CODE, CommonConstantCode.SUCCESS_RESPONSE_MESSAGE, jobOpeningsResp);
		}catch(STLException e) {
			LOGGER.errorLog(CLASSNAME, strMethodName, CommonConstant.METHOD_EXCEPTION_LOG, e);
			return new Response<>(e.getErrorCode(), e.getErrorMessage(), null);
		}
	}
    
    /**
	 * End-point for Searching new job opening
	 * @return Response with JobOpeningsDTO	 
	 */    
    @GetMapping("/search")
	public Response<List<JobOpeningsDTO>> searchJobOpenings(@RequestParam(value = "location", required = false) String location,
			@RequestParam(value = "title", required = false) String title) {
		String strMethodName = "searchJobOpenings";
		try {
			
			if(LOGGER.isInfoEnabled()) LOGGER.infoLog(CLASSNAME, strMethodName, CommonConstant.METHOD_START_LOG);
			List<JobOpeningsDTO> jobOpeningsResp= jobService.searchJobOpenings(location,title);
			return new Response<>(CommonConstantCode.SUCCESS_RESPONSE_CODE, CommonConstantCode.SUCCESS_RESPONSE_MESSAGE, jobOpeningsResp);
		}catch(STLException e) {
			LOGGER.errorLog(CLASSNAME, strMethodName, CommonConstant.METHOD_EXCEPTION_LOG, e);
			return new Response<>(e.getErrorCode(), e.getErrorMessage(), null);
		}
	}
    
    @GetMapping("/searchRegistered")
   	public Response<List<JobOpeningsDTO>> searchJobOpeningsForRegisteredStatus(	@RequestParam(value = "location", required = false) String location,
   			@RequestParam(value = "title", required = false) String title) {
   		String strMethodName = "searchJobOpeningsForRegisteredStatus";
   		try {
   			
   			if(LOGGER.isInfoEnabled()) LOGGER.infoLog(CLASSNAME, strMethodName, CommonConstant.METHOD_START_LOG);
   			List<JobOpeningsDTO> jobOpeningsResp= jobService.searchJobOpeningsForRegisteredStatus(location,title);
   			return new Response<>(CommonConstantCode.SUCCESS_RESPONSE_CODE, CommonConstantCode.SUCCESS_RESPONSE_MESSAGE, jobOpeningsResp);
   		}catch(STLException e) {
   			LOGGER.errorLog(CLASSNAME, strMethodName, CommonConstant.METHOD_EXCEPTION_LOG, e);
   			return new Response<>(e.getErrorCode(), e.getErrorMessage(), null);
   		}
   	}
    
    
    @PostMapping("/apply")
	public Response<CandidatesDTO> applyJob(@Valid @RequestBody CandidatesDTO candidatesDTO) {
		String strMethodName = "applyJob";
		if(LOGGER.isInfoEnabled()) LOGGER.infoLog(CLASSNAME, strMethodName, CommonConstant.METHOD_START_LOG + candidatesDTO);
		try {
			if(LOGGER.isInfoEnabled()) LOGGER.infoLog(CLASSNAME, strMethodName, CommonConstant.METHOD_START_LOG);
			candidatesDTO= jobService.applyJob(candidatesDTO);
			return new Response<>(CommonConstantCode.SUCCESS_RESPONSE_CODE, CommonConstantCode.SUCCESS_RESPONSE_MESSAGE, candidatesDTO);
		}catch(STLException e) {
			LOGGER.errorLog(CLASSNAME, strMethodName, CommonConstant.METHOD_EXCEPTION_LOG, e);
			return new Response<>(e.getErrorCode(), e.getErrorMessage(), null);
		}
	}
    
    @PostMapping("/changeApprovalStatus")
    public Response<JobOpeningChangeStatusDTO> changeApprovalStatus(@Valid @RequestBody JobOpeningChangeStatusDTO jobOpeningChangeStatusDTO){
    	String strMethodName = "changeApprovalStatus";
		try {
			
			if(LOGGER.isInfoEnabled()) LOGGER.infoLog(CLASSNAME, strMethodName, CommonConstant.METHOD_START_LOG);
			jobService.changeApprovalStatus(jobOpeningChangeStatusDTO);
			return new Response<>(CommonConstantCode.SUCCESS_RESPONSE_CODE, CommonConstantCode.SUCCESS_RESPONSE_MESSAGE, null);
		}catch(STLException e) {
			LOGGER.errorLog(CLASSNAME, strMethodName, CommonConstant.METHOD_EXCEPTION_LOG, e);
			return new Response<>(e.getErrorCode(), e.getErrorMessage(), null);
		}
    }
    
}
