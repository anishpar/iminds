package com.stl.iminds.candidate.service;

import com.stl.iminds.candidate.resource.CandidatesDTO;

public interface CandidateService {

	public CandidatesDTO searchCandidate();
	
	public CandidatesDTO viewCandidates(Long candidateId);
	
	
}
