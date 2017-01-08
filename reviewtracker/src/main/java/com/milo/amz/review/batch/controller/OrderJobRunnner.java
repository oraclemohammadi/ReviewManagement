package com.milo.amz.review.batch.controller;

import java.util.Date;
import java.util.Properties;


import javax.inject.Inject;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobExecutionNotRunningException;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.milo.amz.review.web.rest.util.HeaderUtil;
import com.milo.amz.review.web.rest.vm.ManagedUserVM;

@RestController
@RequestMapping("/api")
public class OrderJobRunnner {

	@Autowired
	private Job job;

	@Autowired
	private JobOperator jobOperator;
	// @PostMapping("/runJob")
	// @DeleteMapping("/runJob")
	/*
	 * public void lauchJob() throws JobExecutionAlreadyRunningException,
	 * JobRe* Date().toString()).toJobParameters(); this.jobLauncher.run(job,
	 * jobParameters); }startException, JobInstanceAlreadyCompleteException,
	 * JobParametersInvalidException{ JobParameters jobParameters=new
	 * JobParametersBuilder().addString("name", new
	 
	 */

	@PostMapping("/runJob")
	public ResponseEntity<Long> lauchJob(@RequestBody String jobName) throws NoSuchJobException, JobInstanceAlreadyExistsException, JobParametersInvalidException {
		return  ResponseEntity.ok().body(this.jobOperator.start(jobName, new Date().toString()));
	}

	@PostMapping("/runJob/{id}")
	public void stopJob(@PathVariable Long id) throws NoSuchJobExecutionException, JobExecutionNotRunningException {
		this.jobOperator.stop(id);
	}
}
