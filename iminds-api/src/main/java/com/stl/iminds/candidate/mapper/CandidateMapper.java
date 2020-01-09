package com.stl.iminds.candidate.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.stl.iminds.candidate.model.CandidateHistory;
import com.stl.iminds.candidate.model.CandidateJobRel;
import com.stl.iminds.candidate.model.CandidateQuestions;
import com.stl.iminds.candidate.model.CandidateResult;
import com.stl.iminds.candidate.model.CandidateSkills;
import com.stl.iminds.candidate.model.Candidates;
import com.stl.iminds.candidate.resource.CandidateHistoryDTO;
import com.stl.iminds.candidate.resource.CandidateJobRelDTO;
import com.stl.iminds.candidate.resource.CandidateQuestionsDTO;
import com.stl.iminds.candidate.resource.CandidateResultDTO;
import com.stl.iminds.candidate.resource.CandidateSkillsDTO;
import com.stl.iminds.candidate.resource.CandidatesDTO;

public class CandidateMapper {
	
	public static Candidates convertCandidatesDTOToCandidates(CandidatesDTO candidatesDTO) {
		
		Candidates candidates = new Candidates();
		candidates.setCandidateid(candidatesDTO.getCandidateid());
		candidates.setCandidateName(candidatesDTO.getName());
		candidates.setEmail(candidatesDTO.getEmail());		
		candidates.setMobile(candidatesDTO.getMobile());		
		candidates.setCandidateResume(candidatesDTO.getResume());		
		candidates.setRating(candidatesDTO.getRating());		
		candidates.setStatus(candidatesDTO.getStatus());		
		
		Date sysDate = new Date();
		candidates.setCreationDate(sysDate);
		candidates.setLastModifiedDate(sysDate);		
		
		List<CandidateSkillsDTO> lstCandidateSkillsDto = candidatesDTO.getCandidateSkills();
    	List<CandidateSkills> lstCandidateSkills = new ArrayList<>();
    	if(lstCandidateSkillsDto != null && !lstCandidateSkillsDto.isEmpty()) {
    		for(int i=0,size=lstCandidateSkillsDto.size();i<size;i++) {
    			CandidateSkillsDTO candidateSkillsDTO = lstCandidateSkillsDto.get(i);
    			CandidateSkills candidateSkills = new CandidateSkills();
    			candidateSkills.setCandidateskillid(candidateSkillsDTO.getCandidateskillid());
    			candidateSkills.setJobSkillId(candidateSkillsDTO.getJobSkillId());
    			candidateSkills.setDescription(candidateSkillsDTO.getDescription());    			
    			candidateSkills.setExperience(candidateSkillsDTO.getExperience());
    			candidateSkills.setCandidate(candidates);
    			lstCandidateSkills.add(candidateSkills);
    		}
    		candidates.setCandidateSkills(lstCandidateSkills);
    	}
    	
    	List<CandidateQuestionsDTO> lstCandidateQuestionsDto = candidatesDTO.getCandidateQuestions();
    	List<CandidateQuestions> lstCandidateQuestions = new ArrayList<>();
    	if(lstCandidateQuestionsDto != null && !lstCandidateQuestionsDto.isEmpty()) {
    		for(int i=0,size=lstCandidateQuestionsDto.size();i<size;i++) {
    			CandidateQuestionsDTO candidateQuestionsDTO = lstCandidateQuestionsDto.get(i);
    			CandidateQuestions candidateQuestions = new CandidateQuestions();
    			candidateQuestions.setCandidatequeid(candidateQuestionsDTO.getCandidatequeid());
    			candidateQuestions.setQuestionId(candidateQuestionsDTO.getQuestionId());
    			candidateQuestions.setAnswer(candidateQuestionsDTO.getAnswer());  
    			candidateQuestions.setCandidate(candidates);
    			lstCandidateQuestions.add(candidateQuestions);
    		}
    		candidates.setCandidateQuestions(lstCandidateQuestions);
    	}
    	
    	List<CandidateResultDTO> lstCandidateResultDto = candidatesDTO.getCandidateResults();
    	List<CandidateResult> lstCandidateResult = new ArrayList<>();
    	if(lstCandidateResultDto != null && !lstCandidateResultDto.isEmpty()) {
    		for(int i=0,size=lstCandidateResultDto.size();i<size;i++) {
    			CandidateResultDTO candidateResultDTO = lstCandidateResultDto.get(i);
    			CandidateResult candidateResult = new CandidateResult();
    			candidateResult.setCandidateresultid(candidateResultDTO.getCandidateresultid());
    			candidateResult.setFormatType(candidateResultDTO.getFormatType());
    			candidateResult.setInterviewtypeId(candidateResultDTO.getInterviewtypeId());
    			candidateResult.setJobOpeningId(candidateResultDTO.getJobOpeningId());
    			candidateResult.setRecording(candidateResultDTO.getRecording());
    			candidateResult.setResult(candidateResultDTO.getResult());
    			candidateResult.setCandidate(candidates);
    			lstCandidateResult.add(candidateResult);
    		}
    		candidates.setCandidateResults(lstCandidateResult);
    	}
    	
    	List<CandidateJobRelDTO> lstCandidateJobRelDto = candidatesDTO.getCandidateJobRel();
    	List<CandidateJobRel> lstCandidateJobRel = new ArrayList<>();
    	if(lstCandidateJobRelDto != null && !lstCandidateJobRelDto.isEmpty()) {
    		for(int i=0,size=lstCandidateJobRelDto.size();i<size;i++) {
    			CandidateJobRelDTO candidateJobRelDTO = lstCandidateJobRelDto.get(i);
    			CandidateJobRel candidateJobRel = new CandidateJobRel();
    			candidateJobRel.setCandidatejobrelid(candidateJobRelDTO.getCandidatejobrelid());
    			candidateJobRel.setJobOpeningId(candidateJobRelDTO.getJobOpeningId());
    			candidateJobRel.setCandidate(candidates);
    			lstCandidateJobRel.add(candidateJobRel);
    		}
    		candidates.setCandidateJobRel(lstCandidateJobRel);
    	}
    	
    	List<CandidateHistoryDTO> lstCandidateHistoryDto = candidatesDTO.getCandidateHistory();
    	List<CandidateHistory> lstCandidateHistory = new ArrayList<>();
    	if(lstCandidateHistoryDto != null && !lstCandidateHistoryDto.isEmpty()) {
    		for(int i=0,size=lstCandidateHistoryDto.size();i<size;i++) {
    			CandidateHistoryDTO candidateHistoryDTO = lstCandidateHistoryDto.get(i);
    			CandidateHistory candidateHistory = new CandidateHistory();
    			candidateHistory.setCandidatehistoryid(candidateHistoryDTO.getCandidatehistoryid());
    			candidateHistory.setCreationDate(candidateHistoryDTO.getCreationDate());
    			candidateHistory.setCandidate(candidates);
    			lstCandidateHistory.add(candidateHistory);
    		}
    		candidates.setCandidateHistory(lstCandidateHistory);
    	}
		
		return candidates;
	}
	
	public static CandidatesDTO convertCandidatesToCandidatesDTO(Candidates candidates) {
		
		CandidatesDTO candidatesDTO = new CandidatesDTO();
		candidatesDTO.setCandidateid(candidates.getCandidateid());
		candidatesDTO.setName(candidates.getCandidateName());
		candidatesDTO.setEmail(candidates.getEmail());		
		candidatesDTO.setMobile(candidates.getMobile());		
		candidatesDTO.setResume(candidates.getCandidateResume());		
		candidatesDTO.setRating(candidates.getRating());		
		candidatesDTO.setStatus(candidates.getStatus());		
		candidatesDTO.setCreationDate(candidates.getCreationDate());
		candidatesDTO.setLastModifiedDate(candidates.getLastModifiedDate());		
		
		List<CandidateSkillsDTO> lstCandidateSkillsDto = new ArrayList<>();
    	List<CandidateSkills> lstCandidateSkills = candidates.getCandidateSkills();
    	if(lstCandidateSkills != null && !lstCandidateSkills.isEmpty()) {
    		for(int i=0,size=lstCandidateSkills.size();i<size;i++) {
    			CandidateSkills candidateSkills = lstCandidateSkills.get(i);
    			CandidateSkillsDTO candidateSkillsDTO = new CandidateSkillsDTO();
    			candidateSkillsDTO.setCandidateskillid(candidateSkills.getCandidateskillid());
    			candidateSkillsDTO.setJobSkillId(candidateSkills.getJobSkillId());
    			candidateSkillsDTO.setDescription(candidateSkills.getDescription());    			
    			candidateSkillsDTO.setExperience(candidateSkills.getExperience());
    			lstCandidateSkillsDto.add(candidateSkillsDTO);
    		}
    		candidatesDTO.setCandidateSkills(lstCandidateSkillsDto);
    	}
    	
    	List<CandidateQuestionsDTO> lstCandidateQuestionsDto = new ArrayList<>();
    	List<CandidateQuestions> lstCandidateQuestions = candidates.getCandidateQuestions();
    	if(lstCandidateQuestions != null && !lstCandidateQuestions.isEmpty()) {
    		for(int i=0,size=lstCandidateQuestions.size();i<size;i++) {
    			CandidateQuestions candidateQuestions = lstCandidateQuestions.get(i);
    			CandidateQuestionsDTO candidateQuestionsDTO = new CandidateQuestionsDTO();
    			candidateQuestionsDTO.setCandidatequeid(candidateQuestions.getCandidatequeid());
    			candidateQuestionsDTO.setQuestionId(candidateQuestions.getQuestionId());
    			candidateQuestionsDTO.setAnswer(candidateQuestions.getAnswer());  
    			lstCandidateQuestionsDto.add(candidateQuestionsDTO);
    		}
    		candidatesDTO.setCandidateQuestions(lstCandidateQuestionsDto);
    	}
    	
    	List<CandidateResultDTO> lstCandidateResultDto = new ArrayList<>();
    	List<CandidateResult> lstCandidateResult = candidates.getCandidateResults();
    	if(lstCandidateResult != null && !lstCandidateResult.isEmpty()) {
    		for(int i=0,size=lstCandidateResult.size();i<size;i++) {
    			CandidateResult candidateResult = lstCandidateResult.get(i);
    			CandidateResultDTO candidateResultDTO = new CandidateResultDTO();
    			candidateResultDTO.setCandidateresultid(candidateResult.getCandidateresultid());
    			candidateResultDTO.setFormatType(candidateResult.getFormatType());
    			candidateResultDTO.setInterviewtypeId(candidateResult.getInterviewtypeId());
    			candidateResultDTO.setJobOpeningId(candidateResult.getJobOpeningId());
    			candidateResultDTO.setRecording(candidateResult.getRecording());
    			candidateResultDTO.setResult(candidateResult.getResult());
    			lstCandidateResultDto.add(candidateResultDTO);
    		}
    		candidatesDTO.setCandidateResults(lstCandidateResultDto);
    	}
    	
    	List<CandidateJobRelDTO> lstCandidateJobRelDto = new ArrayList<>();
    	List<CandidateJobRel> lstCandidateJobRel = candidates.getCandidateJobRel();
    	if(lstCandidateJobRel != null && !lstCandidateJobRel.isEmpty()) {
    		for(int i=0,size=lstCandidateJobRel.size();i<size;i++) {
    			CandidateJobRel candidateJobRel = lstCandidateJobRel.get(i);
    			CandidateJobRelDTO candidateJobRelDTO = new CandidateJobRelDTO();
    			candidateJobRelDTO.setCandidatejobrelid(candidateJobRel.getCandidatejobrelid());
    			candidateJobRelDTO.setJobOpeningId(candidateJobRel.getJobOpeningId());
    			lstCandidateJobRelDto.add(candidateJobRelDTO);
    		}
    		candidatesDTO.setCandidateJobRel(lstCandidateJobRelDto);
    	}
    	
    	List<CandidateHistoryDTO> lstCandidateHistoryDto = new ArrayList<>();
    	List<CandidateHistory> lstCandidateHistory = candidates.getCandidateHistory();
    	if(lstCandidateHistory != null && !lstCandidateHistory.isEmpty()) {
    		for(int i=0,size=lstCandidateHistory.size();i<size;i++) {
    			CandidateHistory candidateHistory = lstCandidateHistory.get(i);
    			CandidateHistoryDTO candidateHistoryDTO = new CandidateHistoryDTO();
    			candidateHistoryDTO.setCandidatehistoryid(candidateHistory.getCandidatehistoryid());
    			candidateHistoryDTO.setCreationDate(candidateHistory.getCreationDate());
    			lstCandidateHistoryDto.add(candidateHistoryDTO);
    		}
    		candidatesDTO.setCandidateHistory(lstCandidateHistoryDto);
    	}
		
		return candidatesDTO;
	}
	
}
