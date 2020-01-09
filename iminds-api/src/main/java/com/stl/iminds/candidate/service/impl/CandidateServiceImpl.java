package com.stl.iminds.candidate.service.impl;

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
import com.stl.iminds.candidate.mapper.CandidateMapper;
import com.stl.iminds.candidate.model.Candidates;
import com.stl.iminds.candidate.repository.CandidateRepository;
import com.stl.iminds.candidate.resource.CandidatesDTO;
import com.stl.iminds.candidate.service.CandidateService;
import com.stl.iminds.commons.exception.NotificationException;
import com.stl.iminds.commons.exception.TechnicalExceptionType;
import com.stl.iminds.commons.security.utils.CommonConstant;

@Service
public class CandidateServiceImpl implements CandidateService{

	private static final String CLASSNAME = "CandidateServiceImpl";
	private static final ILogger LOGGER = LogManager.getLogger();
	
	@Autowired
	CandidateRepository candidateRepository;
	
	@Autowired 
	DBManager dbManager;
	
	public List<CandidatesDTO> searchCandidate() {
		String strMethodName = "searchCandidate";
		if(LOGGER.isDebugEnabled()) LOGGER.debugLog(CLASSNAME, strMethodName, CommonConstant.METHOD_START_LOG);
		
		String strQuery = "SELECT C.CANDIDATEID,C.CANDIDATENAME,C.EMAIL,C.MOBILE,C.STATUS,C.RATING,C.CREATIONDATE,C.LASTMODIFIEDDATE,CJ.JOBOPENINGID,JO.TITLE FROM TBLMCANDIDATES C,TBLMCANDIDATEJOBREL CJ, TBLMJOBOPENING JO WHERE C.CANDIDATEID=CJ.CANDIDATEID AND CJ.JOBOPENINGID = JO.JOBOPENINGID";
		
		List<CandidatesDTO> lstCandidatesDTO = new ArrayList<>();
		try(Connection con = dbManager.getConnection(CacheConstant.DATASOURCE_NAME);
				PreparedStatement pStmt = dbManager.getPreparedStatement(con, strQuery);
				ResultSet rs = pStmt.executeQuery())
		{
				while(rs.next()) {
					CandidatesDTO candidatesDTO =new CandidatesDTO();
					candidatesDTO.setCandidateid(rs.getLong("CANDIDATEID"));
					candidatesDTO.setName(rs.getString("CANDIDATENAME"));
					candidatesDTO.setEmail(rs.getString("EMAIL"));
					candidatesDTO.setMobile(rs.getString("MOBILE"));
					candidatesDTO.setStatus(rs.getString("STATUS"));
					candidatesDTO.setRating(rs.getString("RATING"));
					candidatesDTO.setCreationDate(rs.getDate("CREATIONDATE"));
					candidatesDTO.setLastModifiedDate(rs.getDate("LASTMODIFIEDDATE"));
					candidatesDTO.setJobOpening(rs.getString("TITLE"));
					lstCandidatesDTO.add(candidatesDTO);
				}
		}catch(SQLException sql) {
			LOGGER.errorLog(CLASSNAME,strMethodName,sql.getMessage(),sql);
			throw STLExceptionHelper.throwException(NotificationException.class, null, TechnicalExceptionType.SQL);
		}catch(Exception e) {
			LOGGER.errorLog(CLASSNAME,strMethodName,e.getMessage(),e);
			throw STLExceptionHelper.throwException(NotificationException.class, null, TechnicalExceptionType.TECHNICAL);
		}
		if(LOGGER.isDebugEnabled()) LOGGER.debugLog(CLASSNAME, strMethodName,"job opening get successfully with data : "+ lstCandidatesDTO);
		if(LOGGER.isInfoEnabled())  LOGGER.infoLog(CLASSNAME, strMethodName, CommonConstant.METHOD_END_LOG);
		
		return lstCandidatesDTO;
	}
	
	public CandidatesDTO viewCandidates(Long candidateId) {
		String strMethodName = "viewCandidates";
		if(LOGGER.isDebugEnabled()) LOGGER.debugLog(CLASSNAME, strMethodName, CommonConstant.METHOD_START_LOG+ candidateId);
		
		CandidatesDTO candidatesDTO =new CandidatesDTO();
		Optional<Candidates> optional = candidateRepository.findById(candidateId);
		if(optional.isPresent()) {
			Candidates candidates = optional.get();
			
			if(LOGGER.isDebugEnabled()) LOGGER.debugLog(CLASSNAME, strMethodName,"Candidate Data : "+ candidates);
			
			candidatesDTO = CandidateMapper.convertCandidatesToCandidatesDTO(candidates);
		}	

		if(LOGGER.isDebugEnabled()) LOGGER.debugLog(CLASSNAME, strMethodName,"candidate get successfully with data : "+ candidatesDTO);
		if(LOGGER.isInfoEnabled())  LOGGER.infoLog(CLASSNAME, strMethodName, CommonConstant.METHOD_END_LOG);
		
		return candidatesDTO;
	}
}
