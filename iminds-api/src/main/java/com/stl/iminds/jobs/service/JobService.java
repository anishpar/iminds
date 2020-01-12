package com.stl.iminds.jobs.service;

import java.util.List;

import com.stl.iminds.candidate.resource.CandidatesDTO;
import com.stl.iminds.jobs.resource.JobOpeningsDTO;

public interface JobService {

	public JobOpeningsDTO createJobOpenings(JobOpeningsDTO jobOpeningsDTO);
	
	public JobOpeningsDTO updateJobOpenings(Long jobOpeningId, JobOpeningsDTO jobOpeningsDTO);
	
	public JobOpeningsDTO viewJobOpenings(Long jobOpeningId);
	
	public List<JobOpeningsDTO> searchJobOpenings(String location, String title);
	
	public CandidatesDTO applyJob(CandidatesDTO candidatesDTO);

}
