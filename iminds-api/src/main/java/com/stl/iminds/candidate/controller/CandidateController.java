package com.stl.iminds.candidate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stl.core.base.logger.ILogger;
import com.stl.core.base.logger.LogManager;
import com.stl.core.base.utils.Response;
import com.stl.core.commons.exception.STLException;
import com.stl.core.commons.utils.CommonConstantCode;
import com.stl.iminds.candidate.resource.CandidatesDTO;
import com.stl.iminds.candidate.service.CandidateService;
import com.stl.iminds.commons.security.utils.CommonConstant;

@RestController
@CrossOrigin
@RequestMapping("candidate")
public class CandidateController {

	private static final String CLASSNAME = "CandidateController";
	private static final ILogger LOGGER = LogManager.getLogger();
	
	@Autowired
	CandidateService candidateService;
	
	/**
	 * End-point for creating job opening
	 * @return Response with JobOpeningsDTO	 
	 */    
	@GetMapping("/")
	public Response<List<CandidatesDTO>> searchCandidate() {
		String strMethodName = "searchCandidate";
		try {
			if(LOGGER.isInfoEnabled()) LOGGER.infoLog(CLASSNAME, strMethodName, CommonConstant.METHOD_START_LOG);
			List<CandidatesDTO> lstCandidatesDTO= candidateService.searchCandidate();
			return new Response<>(CommonConstantCode.SUCCESS_RESPONSE_CODE, CommonConstantCode.SUCCESS_RESPONSE_MESSAGE, lstCandidatesDTO);
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
	public Response<CandidatesDTO> viewJobOpenings(@PathVariable Long id) {
		String strMethodName = "updateJobOpenings";
		try {
			
			if(LOGGER.isInfoEnabled()) LOGGER.infoLog(CLASSNAME, strMethodName, CommonConstant.METHOD_START_LOG);
			CandidatesDTO candidatesDTO= candidateService.viewCandidates(id);
			return new Response<>(CommonConstantCode.SUCCESS_RESPONSE_CODE, CommonConstantCode.SUCCESS_RESPONSE_MESSAGE, candidatesDTO);
		}catch(STLException e) {
			LOGGER.errorLog(CLASSNAME, strMethodName, CommonConstant.METHOD_EXCEPTION_LOG, e);
			return new Response<>(e.getErrorCode(), e.getErrorMessage(), null);
		}
	}
}
