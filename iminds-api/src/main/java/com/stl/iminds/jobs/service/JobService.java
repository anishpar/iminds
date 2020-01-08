package com.stl.iminds.jobs.service;

import com.stl.iminds.jobs.resource.JobOpeningsDTO;

public interface JobService {

	public JobOpeningsDTO createJobOpenings(JobOpeningsDTO jobOpeningsDTO);
	
	public JobOpeningsDTO updateJobOpenings(Long jobOpeningId, JobOpeningsDTO jobOpeningsDTO);
	
	public JobOpeningsDTO viewJobOpenings(Long jobOpeningId);

}
