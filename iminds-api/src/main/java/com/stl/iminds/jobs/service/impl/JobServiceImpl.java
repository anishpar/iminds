package com.stl.iminds.jobs.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stl.core.base.logger.ILogger;
import com.stl.core.base.logger.LogManager;
import com.stl.core.commons.db.DBManager;
import com.stl.core.commons.exception.STLExceptionHelper;
import com.stl.iminds.cache.constant.CacheConstant;
import com.stl.iminds.candidate.resource.CandidatesDTO;
import com.stl.iminds.commons.exception.BusinessExceptionType;
import com.stl.iminds.commons.exception.NotificationEntityType;
import com.stl.iminds.commons.exception.NotificationException;
import com.stl.iminds.commons.exception.TechnicalExceptionType;
import com.stl.iminds.commons.security.utils.CommonConstant;
import com.stl.iminds.commons.utils.CommonUtility;
import com.stl.iminds.jobs.mapper.JobMapper;
import com.stl.iminds.jobs.model.JobOpening;
import com.stl.iminds.jobs.repository.JobRepository;
import com.stl.iminds.jobs.resource.JobOpeningsDTO;
import com.stl.iminds.jobs.service.JobService;

@Service
public class JobServiceImpl implements JobService{
	
	private static final String CLASSNAME = "JobServiceImpl";
	private static final ILogger LOGGER = LogManager.getLogger();
	
	@Autowired
	JobRepository jobRepository;
	
	@Autowired
	DBManager dbManager;
	
	@Override
	public JobOpeningsDTO createJobOpenings(JobOpeningsDTO jobOpeningsDTO) {
		String strMethodName = "createJobOpenings";
	
		if(LOGGER.isDebugEnabled()) LOGGER.debugLog(CLASSNAME, strMethodName, CommonConstant.METHOD_START_LOG+ jobOpeningsDTO);

		validateJobOpeningRequest(jobOpeningsDTO);
		
		JobOpening jobOpening = JobMapper.convertJobOpeningDTOToJobOpening(jobOpeningsDTO);
		
		if(LOGGER.isDebugEnabled()) LOGGER.debugLog(CLASSNAME, strMethodName,"Going to save Job Opening Data : "+ jobOpening);
		
		jobRepository.save(jobOpening);
		
		jobOpeningsDTO = JobMapper.convertJobOpeningToJobOpeningDTO(jobOpening);

		if(LOGGER.isDebugEnabled()) LOGGER.debugLog(CLASSNAME, strMethodName,"job opening created successfully with data : "+ jobOpeningsDTO);
		if(LOGGER.isInfoEnabled())  LOGGER.infoLog(CLASSNAME, strMethodName, CommonConstant.METHOD_END_LOG);
		
		return jobOpeningsDTO;
	}
	
	@Override
	public JobOpeningsDTO updateJobOpenings(Long jobOpeningId, JobOpeningsDTO jobOpeningsDTO) {
		String strMethodName = "updateJobOpenings";
		if(LOGGER.isDebugEnabled()) LOGGER.debugLog(CLASSNAME, strMethodName, CommonConstant.METHOD_START_LOG+ jobOpeningsDTO);
		
		jobOpeningsDTO.setJobOpeningid(jobOpeningId);
		validateJobOpeningRequest(jobOpeningsDTO);
		
		JobOpening UpdatejobOpening = JobMapper.convertJobOpeningDTOToJobOpening(jobOpeningsDTO);
		
		JobOpening jobOpening =null;
		Optional<JobOpening> optional = jobRepository.findById(jobOpeningId);
		if(optional.isPresent()) {
			
			jobOpening = optional.get();
			jobOpening.getJobSkills().clear();
			jobOpening.getJobSkills().addAll(UpdatejobOpening.getJobSkills());
			
			jobOpening.getJobQuestionsRels().clear();
			jobOpening.getJobQuestionsRels().addAll(UpdatejobOpening.getJobQuestionsRels());
			
			jobOpening.getJobInterviewRels().clear();
			jobOpening.getJobInterviewRels().addAll(UpdatejobOpening.getJobInterviewRels());
		}	
		if(LOGGER.isDebugEnabled()) LOGGER.debugLog(CLASSNAME, strMethodName,"Going to save Template Data : "+ jobOpening);
		
		//persist updated template data  
		jobRepository.save(jobOpening);	
		
		jobOpeningsDTO = JobMapper.convertJobOpeningToJobOpeningDTO(jobOpening);

		if(LOGGER.isDebugEnabled()) LOGGER.debugLog(CLASSNAME, strMethodName,"job opening updated successfully with data : "+ jobOpeningsDTO);
		if(LOGGER.isInfoEnabled())  LOGGER.infoLog(CLASSNAME, strMethodName, CommonConstant.METHOD_END_LOG);
		
		return jobOpeningsDTO;
	}
	
	private void validateJobOpeningRequest(JobOpeningsDTO jobOpeningsDTO) {
		String strMethodName = "validateJobOpeningRequest";
		if(LOGGER.isInfoEnabled())  LOGGER.infoLog(CLASSNAME, strMethodName, CommonConstant.START);
		
		//Check posting title is null or not
		if(CommonUtility.isNull(jobOpeningsDTO.getTitle())){
			LOGGER.errorLog(CLASSNAME, strMethodName, "posting title can not be null or empty.");
			throw STLExceptionHelper.throwException(NotificationException.class, NotificationEntityType.TEMPLATE, BusinessExceptionType.MISSING_MANDATORY_PARAMETERS, CommonConstant.POSTING_TITLE);
		}
		
		//Check job status is null or not
		if(CommonUtility.isNull(jobOpeningsDTO.getJobStatus())){
			LOGGER.errorLog(CLASSNAME, strMethodName, "job status can not be null or empty.");
			throw STLExceptionHelper.throwException(NotificationException.class, NotificationEntityType.TEMPLATE, BusinessExceptionType.MISSING_MANDATORY_PARAMETERS, CommonConstant.JOB_STATUS);
		}
		
		//Check Hiring Lead is null or not
		if(CommonUtility.isNull(jobOpeningsDTO.getHiringLead())){
			LOGGER.errorLog(CLASSNAME, strMethodName, "Hiring Lead can not be null or empty.");
			throw STLExceptionHelper.throwException(NotificationException.class, NotificationEntityType.TEMPLATE, BusinessExceptionType.MISSING_MANDATORY_PARAMETERS, CommonConstant.HIRING_LEAD);
		}
		
		//Check Employee type is null or not
		if(CommonUtility.isNull(jobOpeningsDTO.getEmployeeType())){
			LOGGER.errorLog(CLASSNAME, strMethodName, "Employee type can not be null or empty.");
			throw STLExceptionHelper.throwException(NotificationException.class, NotificationEntityType.TEMPLATE, BusinessExceptionType.MISSING_MANDATORY_PARAMETERS, CommonConstant.EMPLOYMENT_TYPE);
		}
		//Check job description is null or not
		if(CommonUtility.isNull(jobOpeningsDTO.getJobDescription())){
			LOGGER.errorLog(CLASSNAME, strMethodName, "job description can not be null or empty.");
			throw STLExceptionHelper.throwException(NotificationException.class, NotificationEntityType.TEMPLATE, BusinessExceptionType.MISSING_MANDATORY_PARAMETERS, CommonConstant.JOB_DESCRIPTION);
		}
		
		//Check location is null or not
		if(CommonUtility.isNull(jobOpeningsDTO.getLocation())){
			LOGGER.errorLog(CLASSNAME, strMethodName, "location can not be null or empty.");
			throw STLExceptionHelper.throwException(NotificationException.class, NotificationEntityType.TEMPLATE, BusinessExceptionType.MISSING_MANDATORY_PARAMETERS, CommonConstant.LOCATION);
		}
		
		//Check city is null or not
		if(CommonUtility.isNull(jobOpeningsDTO.getCity())){
			LOGGER.errorLog(CLASSNAME, strMethodName, "city can not be null or empty.");
			throw STLExceptionHelper.throwException(NotificationException.class, NotificationEntityType.TEMPLATE, BusinessExceptionType.MISSING_MANDATORY_PARAMETERS, CommonConstant.CITY);
		}
		
		//Check state is null or not
		if(CommonUtility.isNull(jobOpeningsDTO.getState())){
			LOGGER.errorLog(CLASSNAME, strMethodName, "state can not be null or empty.");
			throw STLExceptionHelper.throwException(NotificationException.class, NotificationEntityType.TEMPLATE, BusinessExceptionType.MISSING_MANDATORY_PARAMETERS, CommonConstant.STATE);
		}
				
		if(LOGGER.isInfoEnabled())  LOGGER.infoLog(CLASSNAME, strMethodName, "Ended.");
	}
	
	public JobOpeningsDTO viewJobOpenings(Long jobOpeningId) {
		String strMethodName = "viewJobOpenings";
		if(LOGGER.isDebugEnabled()) LOGGER.debugLog(CLASSNAME, strMethodName, CommonConstant.METHOD_START_LOG+ jobOpeningId);
		
		JobOpeningsDTO jobOpeningsDTO =new JobOpeningsDTO();
		Optional<JobOpening> optional = jobRepository.findById(jobOpeningId);
		if(optional.isPresent()) {
			JobOpening jobOpening = optional.get();
			
			if(LOGGER.isDebugEnabled()) LOGGER.debugLog(CLASSNAME, strMethodName,"Going to save Template Data : "+ jobOpening);
			
			jobOpeningsDTO = JobMapper.convertJobOpeningToJobOpeningDTO(jobOpening);
		}	

		if(LOGGER.isDebugEnabled()) LOGGER.debugLog(CLASSNAME, strMethodName,"job opening get successfully with data : "+ jobOpeningsDTO);
		if(LOGGER.isInfoEnabled())  LOGGER.infoLog(CLASSNAME, strMethodName, CommonConstant.METHOD_END_LOG);
		
		return jobOpeningsDTO;
	}
	
	@Override
	public List<JobOpeningsDTO> searchJobOpenings(String location, String title) {
		String strMethodName = "searchJobOpenings";
		List<JobOpeningsDTO> listSearchJobOpenings = new ArrayList();
		
	
		if(LOGGER.isDebugEnabled()) LOGGER.debugLog(CLASSNAME, strMethodName,"Going to search Job Opening for location : "+ location + "and for title "+title);
		
		String strQuery = "SELECT JO.HIRINGLEAD,JO.CREATIONDATE,JO.TITLE FROM TBLMJOBOPENING JO ";
		
		try(Connection con = dbManager.getConnection(CacheConstant.DATASOURCE_NAME);
				PreparedStatement pStmt = dbManager.getPreparedStatement(con, strQuery);
				ResultSet rs = pStmt.executeQuery())
		{
				while(rs.next()) {
					JobOpeningsDTO jobOpeningsDTO =new JobOpeningsDTO();
					jobOpeningsDTO.setHiringLead(rs.getString("HIRINGLEAD"));
					jobOpeningsDTO.setTitle(rs.getString("TITLE"));
					jobOpeningsDTO.setCreationDate(rs.getDate("CREATIONDATE"));
					listSearchJobOpenings.add(jobOpeningsDTO);
				}
		}catch(SQLException sql) {
			LOGGER.errorLog(CLASSNAME,strMethodName,sql.getMessage(),sql);
			throw STLExceptionHelper.throwException(NotificationException.class, null, TechnicalExceptionType.SQL);
		}catch(Exception e) {
			LOGGER.errorLog(CLASSNAME,strMethodName,e.getMessage(),e);
			throw STLExceptionHelper.throwException(NotificationException.class, null, TechnicalExceptionType.TECHNICAL);
		}
		
		

		if(LOGGER.isDebugEnabled()) LOGGER.debugLog(CLASSNAME, strMethodName,"job searched successfully with data : "+ listSearchJobOpenings);
		if(LOGGER.isInfoEnabled())  LOGGER.infoLog(CLASSNAME, strMethodName, CommonConstant.METHOD_END_LOG);
		
		return listSearchJobOpenings;
	}
}
