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
import org.springframework.transaction.annotation.Transactional;

import com.stl.core.base.logger.ILogger;
import com.stl.core.base.logger.LogManager;
import com.stl.core.commons.db.DBManager;
import com.stl.core.commons.exception.STLExceptionHelper;
import com.stl.iminds.cache.constant.CacheConstant;
import com.stl.iminds.candidate.resource.CandidateSkillsDTO;
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
		/*if(CommonUtility.isNull(jobOpeningsDTO.getCity())){
			LOGGER.errorLog(CLASSNAME, strMethodName, "city can not be null or empty.");
			throw STLExceptionHelper.throwException(NotificationException.class, NotificationEntityType.TEMPLATE, BusinessExceptionType.MISSING_MANDATORY_PARAMETERS, CommonConstant.CITY);
		}
		
		//Check state is null or not
		if(CommonUtility.isNull(jobOpeningsDTO.getState())){
			LOGGER.errorLog(CLASSNAME, strMethodName, "state can not be null or empty.");
			throw STLExceptionHelper.throwException(NotificationException.class, NotificationEntityType.TEMPLATE, BusinessExceptionType.MISSING_MANDATORY_PARAMETERS, CommonConstant.STATE);
		}*/
				
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
		boolean isLocation = false;
		boolean isTitle = false;
	
		if(LOGGER.isDebugEnabled()) LOGGER.debugLog(CLASSNAME, strMethodName,"Going to search Job Opening for location : "+ location + "and for title "+title);
		
		StringBuilder strQuery = new StringBuilder("SELECT JOBOPENINGID,HIRINGLEAD,CREATIONDATE,TITLE,JOBSTATUS FROM TBLMJOBOPENING WHERE APPROVALSTATUS = 'APPROVED'");
		
		if(location != null && !"".equals(location)) {
			isLocation = true;
			strQuery.append(" AND LOCATION = ?");
		}
		
		if(title != null && !"".equals(title)) {
			isTitle = true;
			strQuery.append(" AND TITLE = ?");
		}
		
		try(Connection con = dbManager.getConnection(CacheConstant.DATASOURCE_NAME);
				PreparedStatement pStmt = dbManager.getPreparedStatement(con, strQuery.toString());) {
			int colIndex = 1;
			if(isLocation) {
				pStmt.setString(colIndex++, location);
			}
			if(isTitle) {
				pStmt.setString(colIndex++, title);
			}
			try(ResultSet rs = pStmt.executeQuery()) {
				while(rs.next()) {
					JobOpeningsDTO jobOpeningsDTO =new JobOpeningsDTO();
					jobOpeningsDTO.setHiringLead(rs.getString("HIRINGLEAD"));
					jobOpeningsDTO.setTitle(rs.getString("TITLE"));
					jobOpeningsDTO.setCreationDate(rs.getDate("CREATIONDATE"));
					jobOpeningsDTO.setJobStatus(rs.getString("JOBSTATUS"));
					jobOpeningsDTO.setJobOpeningid(rs.getLong("JOBOPENINGID"));
					jobOpeningsDTO.setApprovalStatus("APPROVALSTATUS");
					jobOpeningsDTO.setCandidateCount(getCandidateCountForOneJob(rs.getLong("JOBOPENINGID")));
					listSearchJobOpenings.add(jobOpeningsDTO);
				}
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

	private long getCandidateCountForOneJob(long jobOpeningId) {
		
		String strMethodName = "getCandidateCountForOneJob";
		if(LOGGER.isDebugEnabled()) LOGGER.debugLog(CLASSNAME, strMethodName,"Going to get count of per job using Job ID: "+ jobOpeningId);
		
		StringBuilder strQuery = new StringBuilder("SELECT COUNT(1) FROM TBLMCANDIDATES WHERE CANDIDATEID IN(SELECT CANDIDATEID FROM TBLMCANDIDATEJOBREL WHERE JOBOPENINGID = ?)");
		try(Connection con = dbManager.getConnection(CacheConstant.DATASOURCE_NAME);
				PreparedStatement pStmt = dbManager.getPreparedStatement(con, strQuery.toString());) {
				pStmt.setLong(1, jobOpeningId);
			
			try(ResultSet rs = pStmt.executeQuery()) {
				while(rs.next()) {
					return rs.getLong("COUNT(1)");
				}
			}
					
				
		}catch(SQLException sql) {
			LOGGER.errorLog(CLASSNAME,strMethodName,sql.getMessage(),sql);
			throw STLExceptionHelper.throwException(NotificationException.class, null, TechnicalExceptionType.SQL);
		}catch(Exception e) {
			LOGGER.errorLog(CLASSNAME,strMethodName,e.getMessage(),e);
			throw STLExceptionHelper.throwException(NotificationException.class, null, TechnicalExceptionType.TECHNICAL);
		}
		
		return 0;
	}
	
	@Override
	public List<JobOpeningsDTO> searchJobOpeningsForRegisteredStatus(String location, String title) {
		String strMethodName = "searchJobOpenings";
		List<JobOpeningsDTO> listSearchJobOpenings = new ArrayList();
		boolean isLocation = false;
		boolean isTitle = false;
	
		if(LOGGER.isDebugEnabled()) LOGGER.debugLog(CLASSNAME, strMethodName,"Going to search Job Opening for location : "+ location + "and for title "+title);
		
		StringBuilder strQuery = new StringBuilder("SELECT JOBOPENINGID,HIRINGLEAD,CREATIONDATE,TITLE,JOBSTATUS FROM TBLMJOBOPENING WHERE APPROVALSTATUS = 'REGISTERED'");
		
		if(location != null && !"".equals(location)) {
			isLocation = true;
			strQuery.append(" AND LOCATION = ?");
		}
		
		if(title != null && !"".equals(title)) {
			isTitle = true;
			strQuery.append(" AND TITLE = ?");
		}
		
		try(Connection con = dbManager.getConnection(CacheConstant.DATASOURCE_NAME);
				PreparedStatement pStmt = dbManager.getPreparedStatement(con, strQuery.toString());) {
			int colIndex = 1;
			if(isLocation) {
				pStmt.setString(colIndex++, location);
			}
			if(isTitle) {
				pStmt.setString(colIndex++, title);
			}
			try(ResultSet rs = pStmt.executeQuery()) {
				while(rs.next()) {
					JobOpeningsDTO jobOpeningsDTO =new JobOpeningsDTO();
					jobOpeningsDTO.setHiringLead(rs.getString("HIRINGLEAD"));
					jobOpeningsDTO.setTitle(rs.getString("TITLE"));
					jobOpeningsDTO.setCreationDate(rs.getDate("CREATIONDATE"));
					jobOpeningsDTO.setJobStatus(rs.getString("JOBSTATUS"));
					jobOpeningsDTO.setJobOpeningid(rs.getLong("JOBOPENINGID"));
					jobOpeningsDTO.setApprovalStatus("APPROVALSTATUS");
					listSearchJobOpenings.add(jobOpeningsDTO);
				}
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
	
	
	@Transactional
	@Override
	public CandidatesDTO applyJob(CandidatesDTO candidatesDTO) {
		String strMethodName = "applyJob";
	
		if(LOGGER.isDebugEnabled()) LOGGER.debugLog(CLASSNAME, strMethodName, CommonConstant.METHOD_START_LOG+ candidatesDTO);
		
		String strQuery = "SELECT SEQ_CANDIDATES.NEXTVAL CANDIDATEID FROM DUAL";
		try(Connection con = dbManager.getConnection(CacheConstant.DATASOURCE_NAME);
				PreparedStatement pStmt = dbManager.getPreparedStatement(con, strQuery)){
			
			try(ResultSet rs = pStmt.executeQuery()){
				if (rs.next()) {
					Long newId = rs.getLong("CANDIDATEID");
					candidatesDTO.setCandidateid(newId);
				}
			}

		}catch(SQLException sql) {
			LOGGER.errorLog(CLASSNAME,strMethodName,sql.getMessage(),sql);
			throw STLExceptionHelper.throwException(NotificationException.class, null, TechnicalExceptionType.SQL);
		}catch(Exception e) {
			LOGGER.errorLog(CLASSNAME,strMethodName,e.getMessage(),e);
			throw STLExceptionHelper.throwException(NotificationException.class, null, TechnicalExceptionType.TECHNICAL);
		}
		

		strQuery = "INSERT INTO TBLMCANDIDATES(CANDIDATEID, EMAIL, MOBILE, STATUS, RATING, CREATIONDATE, LASTMODIFIEDDATE, CANDIDATENAME, CANDIDATERESUME)\n" + 
				"VALUES (?,?,?,'Draft', NULL,SYSDATE,SYSDATE,?,NULL)";
		
		try(Connection con = dbManager.getConnection(CacheConstant.DATASOURCE_NAME);
				PreparedStatement pStmt = dbManager.getPreparedStatement(con, strQuery)){
			
			int colIndex = 1;
			pStmt.setLong(colIndex++, candidatesDTO.getCandidateid());
			pStmt.setString(colIndex++, candidatesDTO.getEmail());
			pStmt.setString(colIndex++, candidatesDTO.getMobile());
			pStmt.setString(colIndex++, candidatesDTO.getName());
			
			int iCount = pStmt.executeUpdate();

			if(iCount > 0) {
				if(LOGGER.isDebugEnabled()) LOGGER.debugLog(CLASSNAME, strMethodName, "Candidate Inserted successfully :"+candidatesDTO);
			} 
				
		}catch(SQLException sql) {
			LOGGER.errorLog(CLASSNAME,strMethodName,sql.getMessage(),sql);
			throw STLExceptionHelper.throwException(NotificationException.class, null, TechnicalExceptionType.SQL);
		}catch(Exception e) {
			LOGGER.errorLog(CLASSNAME,strMethodName,e.getMessage(),e);
			throw STLExceptionHelper.throwException(NotificationException.class, null, TechnicalExceptionType.TECHNICAL);
		}
		
		Long candidateid = candidatesDTO.getCandidateid();
		// Add Candidate Skills
		strQuery = "INSERT INTO TBLMCANDIDATESKILLS(CANDIDATESKILLID, CANDIDATEID, JOBSKILLID, DESCRIPTION, EXPERIENCE)\n" + 
				"VALUES (SEQ_CANDIDATESKILL.NEXTVAL,?,?,NULL,?)";
		try(Connection con = dbManager.getConnection(CacheConstant.DATASOURCE_NAME);
				PreparedStatement pStmt = dbManager.getPreparedStatement(con, strQuery)){
			
			List<CandidateSkillsDTO> candidateSkills = candidatesDTO.getCandidateSkills();
			if(candidateSkills != null && !candidateSkills.isEmpty()) {
				for(int index = 0; index < candidateSkills.size(); index++) {
					CandidateSkillsDTO candidateSkillsDTO = candidateSkills.get(index);
					int colIndex = 1;
					pStmt.setLong(colIndex++, candidateid);
					pStmt.setLong(colIndex++, candidateSkillsDTO.getJobSkillId());
					pStmt.setDouble(colIndex++, candidateSkillsDTO.getExperience());
					pStmt.addBatch();
				}
			}
			
			int[] status = pStmt.executeBatch();
			for (int i = 0; i < status.length; i++) {
				if (status[i] != -2 && status[i] < 0) {
					LOGGER.errorLog(CLASSNAME,strMethodName,"Error Occoured.");
					throw STLExceptionHelper.throwException(NotificationException.class, null, TechnicalExceptionType.SQL);
				}
			}
				
		}catch(SQLException sql) {
			LOGGER.errorLog(CLASSNAME,strMethodName,sql.getMessage(),sql);
			throw STLExceptionHelper.throwException(NotificationException.class, null, TechnicalExceptionType.SQL);
		}catch(Exception e) {
			LOGGER.errorLog(CLASSNAME,strMethodName,e.getMessage(),e);
			throw STLExceptionHelper.throwException(NotificationException.class, null, TechnicalExceptionType.TECHNICAL);
		}
		
		
		// Insert Relation Table
		strQuery = "INSERT INTO TBLMCANDIDATEJOBREL(CANDIDATEJOBRELID, CANDIDATEID, JOBOPENINGID)\n" + 
				"VALUES (SEQ_CANDIDATEJOBREL.NEXTVAL,?,?)";
		
		try(Connection con = dbManager.getConnection(CacheConstant.DATASOURCE_NAME);
				PreparedStatement pStmt = dbManager.getPreparedStatement(con, strQuery)){
			
			int colIndex = 1;
			pStmt.setLong(colIndex++, candidateid);
			pStmt.setLong(colIndex++, candidatesDTO.getCandidateJobRel().get(0).getJobOpeningId());
			
			int iCount = pStmt.executeUpdate();
			if(iCount > 0) {
				if(LOGGER.isDebugEnabled()) LOGGER.debugLog(CLASSNAME, strMethodName, "Relation Inserted successfully :"+candidatesDTO);
			} 
				
		}catch(SQLException sql) {
			LOGGER.errorLog(CLASSNAME,strMethodName,sql.getMessage(),sql);
			throw STLExceptionHelper.throwException(NotificationException.class, null, TechnicalExceptionType.SQL);
		}catch(Exception e) {
			LOGGER.errorLog(CLASSNAME,strMethodName,e.getMessage(),e);
			throw STLExceptionHelper.throwException(NotificationException.class, null, TechnicalExceptionType.TECHNICAL);
		}

		if(LOGGER.isDebugEnabled()) LOGGER.debugLog(CLASSNAME, strMethodName,"job opening created successfully with data : "+ candidatesDTO);
		if(LOGGER.isInfoEnabled())  LOGGER.infoLog(CLASSNAME, strMethodName, CommonConstant.METHOD_END_LOG);
		
		return candidatesDTO;
	}
}
