package com.hlag.fis.batch.logging;

import org.apache.logging.log4j.core.layout.ExtendedJsonAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BatchLogger {

    private static Logger logger = null;

    public BatchLogger(Class<?> clazz) {
        logger = LoggerFactory.getLogger(clazz);
    }

    public static Logger getStepLogger(Class<?> clazz) {
        logger = LoggerFactory.getLogger(clazz);
        return logger;
    }

    public static Logger getStepLogger(String stepName, Class<?> clazz) {
        ExtendedJsonAdapter.addMixedFields("stepName", stepName);
        logger = LoggerFactory.getLogger(clazz);
        return logger;
    }

    public static Logger getStepLogger(String stepName, String stepId, Class<?> clazz) {
        ExtendedJsonAdapter.addMixedFields("stepName", stepName);
        ExtendedJsonAdapter.addMixedFields("stepUuid", stepId);
        logger = LoggerFactory.getLogger(clazz);
        return logger;
    }

    public static Logger getJobLogger(String jobName, String jobId, String jobVersion, Class<?> clazz) {
        ExtendedJsonAdapter.addMixedFields("jobName", jobName);
        ExtendedJsonAdapter.addMixedFields("jobUuid", jobId);
        ExtendedJsonAdapter.addMixedFields("jobVersion", jobVersion);
        logger = LoggerFactory.getLogger(clazz);
        return logger;
    }

    public static Logger getJobLogger(String jobName, Class<?> clazz) {
        ExtendedJsonAdapter.addMixedFields("jobName", jobName);
        logger = LoggerFactory.getLogger(clazz);
        return logger;
    }

    public static Logger setJobName(String jobName) {
        ExtendedJsonAdapter.addMixedFields("jobName", jobName);
        return logger;
    }

    public static void setStepName(String stepName) {
        ExtendedJsonAdapter.addMixedFields("stepName", stepName);
    }

    public static void setStepId(String stepId) {
        ExtendedJsonAdapter.addMixedFields("stepUuid", stepId);
    }

    public void setStep(Object stepId, Object stepName) {
        ExtendedJsonAdapter.addMixedFields("stepUuid", stepId);
        ExtendedJsonAdapter.addMixedFields("stepName", stepName);
    }

    public static Logger getLogger(Class<?> clazz) {
        logger = LoggerFactory.getLogger(clazz);
        return logger;
    }
}
