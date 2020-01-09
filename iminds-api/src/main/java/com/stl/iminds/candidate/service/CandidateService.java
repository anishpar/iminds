package com.stl.iminds.candidate.service;

import java.util.List;

import com.stl.iminds.candidate.resource.CandidatesDTO;

public interface CandidateService {

	public List<CandidatesDTO> searchCandidate();
	
	public CandidatesDTO viewCandidates(Long candidateId);
	
	
}
