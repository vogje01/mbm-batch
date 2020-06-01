package com.hlag.fis.batch.logging;

import com.hlag.fis.batch.domain.JobExecutionLog;
import com.hlag.fis.batch.domain.JobLogMessageLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.springframework.stereotype.Component;

@Component
public class BatchLogger implements Logger {

    private String jobName;

    private String jobUuid;

    private String stepName;

    private String stepUuid;

    private String jobVersion;

    private Class<?> clazz;

    private static Logger logger;

    private JobExecutionLog jobExecutionLog = new JobExecutionLog();

    private BatchLogProducer batchLogProducer;

    public BatchLogger(String hostName, String nodeName, BatchLogProducer batchLogProducer) {
        this.batchLogProducer = batchLogProducer;
        jobExecutionLog.setJobPid(ProcessHandle.current().pid());
        jobExecutionLog.setHostName(hostName);
        jobExecutionLog.setNodeName(nodeName);
    }

    @Override
    public String getName() {
        return logger.getName();
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
        jobExecutionLog.setJobName(jobName);
    }

    public String getJobUuid() {
        return jobUuid;
    }

    public void setJobUuid(String jobUuid) {
        this.jobUuid = jobUuid;
        jobExecutionLog.setJobUuid(jobUuid);
    }

    public String getJobVersion() {
        return jobVersion;
    }

    public void setJobVersion(String jobVersion) {
        this.jobVersion = jobVersion;
        jobExecutionLog.setJobVersion(jobVersion);
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
        jobExecutionLog.setStepName(stepName);
    }

    public String getStepUuid() {
        return stepUuid;
    }

    public void setStepUuid(String stepUuid) {
        this.stepUuid = stepUuid;
        jobExecutionLog.setStepUuid(stepUuid);
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
        logger = LoggerFactory.getLogger(clazz);
        jobExecutionLog.setLoggerName(clazz.getName());
    }

    @Override
    public boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

    @Override
    public void trace(String s) {
        logger.trace(s);
        if (logger.isTraceEnabled()) {
            jobExecutionLog.setMessage(s);
            jobExecutionLog.setLevel(JobLogMessageLevel.TRACE);
            jobExecutionLog.setTimestamp(System.currentTimeMillis());
            batchLogProducer.sendBatchLog(jobExecutionLog);
        }
    }

    @Override
    public void trace(String s, Object o) {
        logger.trace(s, o);
    }

    @Override
    public void trace(String s, Object o, Object o1) {
        logger.trace(s, o, o1);
    }

    @Override
    public void trace(String s, Object... objects) {
        logger.trace(s, objects);
    }

    @Override
    public void trace(String s, Throwable throwable) {
        logger.trace(s, throwable);
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return false;
    }

    @Override
    public void trace(Marker marker, String s) {
        logger.trace(marker, s);
    }

    @Override
    public void trace(Marker marker, String s, Object o) {
        logger.trace(marker, s, o);
    }

    @Override
    public void trace(Marker marker, String s, Object o, Object o1) {
        logger.trace(marker, s, o, o1);
    }

    @Override
    public void trace(Marker marker, String s, Object... objects) {
        logger.trace(marker, s, objects);
    }

    @Override
    public void trace(Marker marker, String s, Throwable throwable) {
        logger.trace(marker, s, throwable);
    }

    @Override
    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    @Override
    public void debug(String s) {
        logger.debug(s);
        if (logger.isDebugEnabled()) {
            jobExecutionLog.setLevel(JobLogMessageLevel.DEBUG);
            jobExecutionLog.setMessage(s);
            jobExecutionLog.setTimestamp(System.currentTimeMillis());
            batchLogProducer.sendBatchLog(jobExecutionLog);
        }
    }

    @Override
    public void debug(String s, Object o) {
        logger.debug(s, o);
    }

    @Override
    public void debug(String s, Object o, Object o1) {
        logger.debug(s, o, o1);
    }

    @Override
    public void debug(String s, Object... objects) {
        logger.debug(s, objects);
    }

    @Override
    public void debug(String s, Throwable throwable) {
        logger.debug(s, throwable);
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return logger.isDebugEnabled(marker);
    }

    @Override
    public void debug(Marker marker, String s) {
        logger.debug(marker, s);
    }

    @Override
    public void debug(Marker marker, String s, Object o) {
        logger.debug(marker, s, o);
    }

    @Override
    public void debug(Marker marker, String s, Object o, Object o1) {
        logger.debug(marker, s, o, o1);
    }

    @Override
    public void debug(Marker marker, String s, Object... objects) {
        logger.debug(marker, s, objects);
    }

    @Override
    public void debug(Marker marker, String s, Throwable throwable) {
        logger.debug(marker, s, throwable);
    }

    @Override
    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    @Override
    public void info(String s) {
        logger.info(s);
        if (logger.isInfoEnabled()) {
            jobExecutionLog.setLevel(JobLogMessageLevel.INFO);
            jobExecutionLog.setMessage(s);
            jobExecutionLog.setTimestamp(System.currentTimeMillis());
            batchLogProducer.sendBatchLog(jobExecutionLog);
        }
    }

    @Override
    public void info(String s, Object o) {
        logger.debug(s, o);
    }

    @Override
    public void info(String s, Object o, Object o1) {
        logger.debug(s, o, o1);
    }

    @Override
    public void info(String s, Object... objects) {
        logger.debug(s, objects);
    }

    @Override
    public void info(String s, Throwable throwable) {
        logger.debug(s, throwable);
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return logger.isInfoEnabled(marker);
    }

    @Override
    public void info(Marker marker, String s) {
        logger.debug(marker, s);
    }

    @Override
    public void info(Marker marker, String s, Object o) {
        logger.debug(marker, s, o);
    }

    @Override
    public void info(Marker marker, String s, Object o, Object o1) {
        logger.debug(marker, s, o, o1);
    }

    @Override
    public void info(Marker marker, String s, Object... objects) {
        logger.debug(marker, s, objects);
    }

    @Override
    public void info(Marker marker, String s, Throwable throwable) {
        logger.debug(marker, s, throwable);
    }

    @Override
    public boolean isWarnEnabled() {
        return logger.isWarnEnabled();
    }

    @Override
    public void warn(String s) {
        logger.warn(s);
        if (logger.isWarnEnabled()) {
            jobExecutionLog.setLevel(JobLogMessageLevel.WARN);
            jobExecutionLog.setMessage(s);
            jobExecutionLog.setTimestamp(System.currentTimeMillis());
            batchLogProducer.sendBatchLog(jobExecutionLog);
        }
    }

    @Override
    public void warn(String s, Object o) {
        logger.warn(s, o);
    }

    @Override
    public void warn(String s, Object... objects) {
        logger.warn(s, objects);
    }

    @Override
    public void warn(String s, Object o, Object o1) {
        logger.warn(s, o, o1);
    }

    @Override
    public void warn(String s, Throwable throwable) {
        logger.warn(s, throwable);
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return logger.isWarnEnabled();
    }

    @Override
    public void warn(Marker marker, String s) {
        logger.warn(marker, s);
    }

    @Override
    public void warn(Marker marker, String s, Object o) {
        logger.warn(marker, s, o);
    }

    @Override
    public void warn(Marker marker, String s, Object o, Object o1) {
        logger.warn(marker, s, o, o1);
    }

    @Override
    public void warn(Marker marker, String s, Object... objects) {
        logger.warn(marker, s, objects);
    }

    @Override
    public void warn(Marker marker, String s, Throwable throwable) {
        logger.warn(marker, s, throwable);
    }

    @Override
    public boolean isErrorEnabled() {
        return logger.isErrorEnabled();
    }

    @Override
    public void error(String s) {
        logger.error(s);
        if (logger.isErrorEnabled()) {
            jobExecutionLog.setLevel(JobLogMessageLevel.ERROR);
            jobExecutionLog.setMessage(s);
            jobExecutionLog.setTimestamp(System.currentTimeMillis());
            batchLogProducer.sendBatchLog(jobExecutionLog);
        }
    }

    @Override
    public void error(String s, Object o) {
        logger.error(s, o);
    }

    @Override
    public void error(String s, Object o, Object o1) {
        logger.error(s, o, o1);
    }

    @Override
    public void error(String s, Object... objects) {
        logger.error(s, objects);
    }

    @Override
    public void error(String s, Throwable throwable) {
        logger.error(s, throwable);
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return logger.isErrorEnabled(marker);
    }

    @Override
    public void error(Marker marker, String s) {
        logger.error(marker, s);
    }

    @Override
    public void error(Marker marker, String s, Object o) {
        logger.error(marker, s, o);
    }

    @Override
    public void error(Marker marker, String s, Object o, Object o1) {
        logger.error(marker, s, o, o1);
    }

    @Override
    public void error(Marker marker, String s, Object... objects) {
        logger.error(marker, s, objects);
    }

    @Override
    public void error(Marker marker, String s, Throwable throwable) {
        logger.error(marker, s, throwable);
    }
}
