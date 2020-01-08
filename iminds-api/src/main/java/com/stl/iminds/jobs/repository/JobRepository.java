package com.stl.iminds.jobs.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.stl.iminds.jobs.model.JobOpening;

@Repository
public interface JobRepository extends CrudRepository<JobOpening, Long>{

}
