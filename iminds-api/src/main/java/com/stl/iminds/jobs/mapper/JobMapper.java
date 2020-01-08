package com.stl.iminds.jobs.mapper;

import java.util.ArrayList;
import java.util.List;

import com.stl.iminds.jobs.model.JobInterviewRel;
import com.stl.iminds.jobs.model.JobOpening;
import com.stl.iminds.jobs.model.JobQuestionsRel;
import com.stl.iminds.jobs.model.JobSkills;
import com.stl.iminds.jobs.resource.JobInterviewRelDTO;
import com.stl.iminds.jobs.resource.JobOpeningsDTO;
import com.stl.iminds.jobs.resource.JobQuestionsRelDTO;
import com.stl.iminds.jobs.resource.JobSkillsDTO;

public class JobMapper {
	
	
	public static JobOpening convertJobOpeningDTOToJobOpening(JobOpeningsDTO jobOpeningsDTO) {
		JobOpening jobOpening = new JobOpening();
		jobOpening.setJobOpeningid(jobOpeningsDTO.getJobOpeningid());
		jobOpening.setTitle(jobOpeningsDTO.getTitle());
		jobOpening.setJobStatus(jobOpeningsDTO.getJobStatus());		
		jobOpening.setHiringLead(jobOpeningsDTO.getHiringLead());		
		jobOpening.setDepartment(jobOpeningsDTO.getDepartment());		
		jobOpening.setEmployeeType(jobOpeningsDTO.getEmployeeType());		
		jobOpening.setMinimumExp(jobOpeningsDTO.getMinimumExp());		
		jobOpening.setJobDescription(jobOpeningsDTO.getJobDescription());		
		jobOpening.setLocation(jobOpeningsDTO.getLocation());
		jobOpening.setCity(jobOpeningsDTO.getCity());		
		jobOpening.setState(jobOpeningsDTO.getState());		
		jobOpening.setCountry(jobOpeningsDTO.getCountry());		
		jobOpening.setCompensation(jobOpeningsDTO.getCompensation());
		jobOpening.setCreationDate(jobOpeningsDTO.getCreationDate());
		jobOpening.setLastModifiedDate(jobOpeningsDTO.getLastModifiedDate());		
		
		List<JobSkillsDTO> lstJobSkillsDto = jobOpeningsDTO.getJobSkills();
    	List<JobSkills> lstJobSkills = new ArrayList<>();
    	if(lstJobSkillsDto != null && !lstJobSkillsDto.isEmpty()) {
    		for(int i=0,size=lstJobSkillsDto.size();i<size;i++) {
    			JobSkillsDTO jobSkillsDTO = lstJobSkillsDto.get(i);
    			JobSkills jobSkills = new JobSkills();
    			jobSkills.setJobskillid(jobSkillsDTO.getJobskillid());
    			jobSkills.setType(jobSkillsDTO.getType());
    			jobSkills.setName(jobSkillsDTO.getName());    			
    			jobSkills.setDescription(jobSkillsDTO.getDescription());
    			jobSkills.setMinimumExp(jobSkillsDTO.getMinimumExp());   
    			jobSkills.setJobOpening(jobOpening);
    			lstJobSkills.add(jobSkills);
    		}
    		jobOpening.setJobSkills(lstJobSkills);
    	}
    	
    	List<JobQuestionsRelDTO> lstQuestionsRelDto = jobOpeningsDTO.getJobQuestionsRels();
    	List<JobQuestionsRel> lstJobQuestionsRel = new ArrayList<>();
    	if(lstQuestionsRelDto != null && !lstQuestionsRelDto.isEmpty()) {
    		for(int i=0,size=lstQuestionsRelDto.size();i<size;i++) {
    			JobQuestionsRelDTO jobQuestionsRelDTO = lstQuestionsRelDto.get(i);
    			JobQuestionsRel jobQuestionsRel = new JobQuestionsRel();
    			jobQuestionsRel.setJobquesionrelId(jobQuestionsRelDTO.getJobquesionrelId());
    			jobQuestionsRel.setQuestionId(jobQuestionsRelDTO.getQuestionId());
    			jobQuestionsRel.setIsMandatory(jobQuestionsRelDTO.getIsMandatory());  
    			jobQuestionsRel.setJobOpening(jobOpening);
    			lstJobQuestionsRel.add(jobQuestionsRel);
    		}
    		jobOpening.setJobQuestionsRels(lstJobQuestionsRel);
    	}
    	
    	List<JobInterviewRelDTO> lstInterviewRelDto = jobOpeningsDTO.getJobInterviewRels();
    	List<JobInterviewRel> lstJobInterviewRel = new ArrayList<>();
    	if(lstInterviewRelDto != null && !lstInterviewRelDto.isEmpty()) {
    		for(int i=0,size=lstInterviewRelDto.size();i<size;i++) {
    			JobInterviewRelDTO jobInterviewRelDTO = lstInterviewRelDto.get(i);
    			JobInterviewRel jobInterviewRel = new JobInterviewRel();
    			jobInterviewRel.setJobinterviewrelid(jobInterviewRelDTO.getJobinterviewrelid());
    			jobInterviewRel.setInterviewtypeid(jobInterviewRelDTO.getInterviewTypeId());
    			jobInterviewRel.setOrder(jobInterviewRelDTO.getOrder());  
    			jobInterviewRel.setJobOpening(jobOpening);
    			lstJobInterviewRel.add(jobInterviewRel);
    		}
    		jobOpening.setJobInterviewRels(lstJobInterviewRel);
    	}
		
		return jobOpening;
	}
	
	
	public static JobOpeningsDTO convertJobOpeningToJobOpeningDTO(JobOpening jobOpening) {
		JobOpeningsDTO jobOpeningsDTO = new JobOpeningsDTO();
		
		jobOpeningsDTO.setJobOpeningid(jobOpening.getJobOpeningid());
		jobOpeningsDTO.setTitle(jobOpening.getTitle());
		jobOpeningsDTO.setJobStatus(jobOpening.getJobStatus());		
		jobOpeningsDTO.setHiringLead(jobOpening.getHiringLead());		
		jobOpeningsDTO.setDepartment(jobOpening.getDepartment());		
		jobOpeningsDTO.setEmployeeType(jobOpening.getEmployeeType());		
		jobOpeningsDTO.setMinimumExp(jobOpening.getMinimumExp());		
		jobOpeningsDTO.setJobDescription(jobOpening.getJobDescription());		
		jobOpeningsDTO.setLocation(jobOpening.getLocation());
		jobOpeningsDTO.setCity(jobOpening.getCity());		
		jobOpeningsDTO.setState(jobOpening.getState());		
		jobOpeningsDTO.setCountry(jobOpening.getCountry());		
		jobOpeningsDTO.setCompensation(jobOpening.getCompensation());
		jobOpeningsDTO.setCreationDate(jobOpening.getCreationDate());
		jobOpeningsDTO.setLastModifiedDate(jobOpening.getLastModifiedDate());		
		
		List<JobSkills> lstJobSkills = jobOpening.getJobSkills();
    	List<JobSkillsDTO> lstJobSkillsDto = new ArrayList<>();
    	if(lstJobSkills != null && !lstJobSkills.isEmpty()) {
    		for(int i=0,size=lstJobSkills.size();i<size;i++) {
    			JobSkills jobSkills = lstJobSkills.get(i);
    			JobSkillsDTO jobSkillsDto = new JobSkillsDTO();
    			jobSkillsDto.setJobskillid(jobSkills.getJobskillid());
    			jobSkillsDto.setType(jobSkills.getType());
    			jobSkillsDto.setName(jobSkills.getName());    			
    			jobSkillsDto.setDescription(jobSkills.getDescription());
    			jobSkillsDto.setMinimumExp(jobSkills.getMinimumExp());   
    			lstJobSkillsDto.add(jobSkillsDto);
    		}
    		jobOpeningsDTO.setJobSkills(lstJobSkillsDto);
    	}
    	
    	List<JobQuestionsRel> lstQuestionsRel = jobOpening.getJobQuestionsRels();
    	List<JobQuestionsRelDTO> lstQuestionsRelDto = new ArrayList<>();
    	if(lstQuestionsRel != null && !lstQuestionsRel.isEmpty()) {
    		for(int i=0,size=lstQuestionsRel.size();i<size;i++) {
    			JobQuestionsRel jobQuestionsRel = lstQuestionsRel.get(i);
    			JobQuestionsRelDTO jobQuestionsRelDto = new JobQuestionsRelDTO();
    			jobQuestionsRelDto.setJobquesionrelId(jobQuestionsRel.getJobquesionrelId());
    			jobQuestionsRelDto.setQuestionId(jobQuestionsRel.getQuestionId());
    			jobQuestionsRelDto.setIsMandatory(jobQuestionsRel.getIsMandatory());  
    			lstQuestionsRelDto.add(jobQuestionsRelDto);
    		}
    		jobOpeningsDTO.setJobQuestionsRels(lstQuestionsRelDto);
    	}
    	
    	List<JobInterviewRel> lstInterviewRel = jobOpening.getJobInterviewRels();
    	List<JobInterviewRelDTO> lstJobInterviewRelDto = new ArrayList<>();
    	if(lstInterviewRel != null && !lstInterviewRel.isEmpty()) {
    		for(int i=0,size=lstInterviewRel.size();i<size;i++) {
    			JobInterviewRel jobInterviewRel = lstInterviewRel.get(i);
    			JobInterviewRelDTO jobInterviewRelDto = new JobInterviewRelDTO();
    			jobInterviewRelDto.setJobinterviewrelid(jobInterviewRel.getJobinterviewrelid());
    			jobInterviewRelDto.setInterviewTypeId(jobInterviewRel.getInterviewtypeid());
    			jobInterviewRelDto.setOrder(jobInterviewRel.getOrder());  
    			lstJobInterviewRelDto.add(jobInterviewRelDto);
    		}
    		jobOpeningsDTO.setJobInterviewRels(lstJobInterviewRelDto);
    	}
		
		return jobOpeningsDTO;
	}
}
