package com.stl.iminds.candidate.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.stl.iminds.candidate.model.Candidates;

@Repository
public interface CandidateRepository extends CrudRepository<Candidates, Long>{

}
