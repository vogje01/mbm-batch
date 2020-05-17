package com.hlag.fis.batch.logging;

import org.apache.logging.log4j.core.layout.ExtendedJsonAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.hlag.fis.batch.util.ExecutionParameter.*;

public class BatchLogger {

    private static Logger logger = null;

	public BatchLogger(Class<?> clazz) {
		logger = LoggerFactory.getLogger(clazz);
	}

	public static Logger getStepLogger(Class<?>clazz) {
		logger = LoggerFactory.getLogger(clazz);
		return logger;
	}

	public static Logger getStepLogger(String stepName, Class<?>clazz) {
		ExtendedJsonAdapter.addMixedFields(STEP_NAME_NAME, stepName);
		logger = LoggerFactory.getLogger(clazz);
		return logger;
	}

	public static Logger getStepLogger(String stepName, String stepId, Class<?>clazz) {
		ExtendedJsonAdapter.addMixedFields(STEP_NAME_NAME, stepName);
		ExtendedJsonAdapter.addMixedFields(STEP_UUID_NAME, stepId);
		logger = LoggerFactory.getLogger(clazz);
		return logger;
    }

	public static Logger getJobLogger(String jobName, String jobId, String jobVersion, Class<?>clazz) {
		ExtendedJsonAdapter.addMixedFields(JOB_NAME_NAME, jobName);
		ExtendedJsonAdapter.addMixedFields(JOB_UUID_NAME, jobId);
		ExtendedJsonAdapter.addMixedFields(JOB_VERSION_NAME, jobVersion);
		logger = LoggerFactory.getLogger(clazz);
		return logger;
	}

	public static Logger getJobLogger(String jobName, Class<?>clazz) {
		ExtendedJsonAdapter.addMixedFields(JOB_NAME_NAME, jobName);
		logger = LoggerFactory.getLogger(clazz);
		return logger;
	}

	public static Logger setJobName(String jobName) {
		ExtendedJsonAdapter.addMixedFields(JOB_NAME_NAME, jobName);
		return logger;
	}

	public static void setStepName(String stepName) {
		ExtendedJsonAdapter.addMixedFields(STEP_NAME_NAME, stepName);
	}

	public static void setStepId(String stepId) {
		ExtendedJsonAdapter.addMixedFields(STEP_UUID_NAME, stepId);
	}

	public void setStep(Object stepId, Object stepName) {
		ExtendedJsonAdapter.addMixedFields(STEP_UUID_NAME, stepId);
		ExtendedJsonAdapter.addMixedFields(STEP_NAME_NAME, stepName);
	}

	public static Logger getLogger(Class<?> clazz){
		logger = LoggerFactory.getLogger(clazz);
		return logger;
	}
}
