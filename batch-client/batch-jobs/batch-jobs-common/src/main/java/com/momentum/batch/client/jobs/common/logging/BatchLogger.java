package com.momentum.batch.client.jobs.common.logging;

import com.momentum.batch.server.database.domain.JobLogMessageLevel;
import com.momentum.batch.server.database.domain.dto.JobExecutionLogDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

import java.io.PrintWriter;
import java.io.StringWriter;

public class BatchLogger implements Logger {

    private final Logger logger;

    private final JobExecutionLogDto jobExecutionLogDto = new JobExecutionLogDto();

    private final BatchLogProducer batchLogProducer;

    public BatchLogger(String hostName, String nodeName, BatchLogProducer batchLogProducer, Class<?> clazz) {
        this.batchLogProducer = batchLogProducer;
        jobExecutionLogDto.setJobPid(ProcessHandle.current().pid());
        jobExecutionLogDto.setHostName(hostName);
        jobExecutionLogDto.setNodeName(nodeName);
        jobExecutionLogDto.setLoggerName(clazz.getName());
        logger = LoggerFactory.getLogger(clazz);
    }

    @Override
    public String getName() {
        return logger.getName();
    }

    public void setJobName(String jobName) {
        jobExecutionLogDto.setJobName(jobName);
    }

    public void setJobUuid(String jobUuid) {
        jobExecutionLogDto.setJobUuid(jobUuid);
    }

    public void setJobVersion(String jobVersion) {
        jobExecutionLogDto.setJobVersion(jobVersion);
    }

    public void setStepName(String stepName) {
        jobExecutionLogDto.setStepName(stepName);
    }

    public void setStepUuid(String stepUuid) {
        jobExecutionLogDto.setStepUuid(stepUuid);
    }

    private String getStacktrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        throwable.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    private void sendLog(JobLogMessageLevel level, String message) {
        jobExecutionLogDto.setThread(Thread.currentThread().getName());
        jobExecutionLogDto.setThreadId(Thread.currentThread().getId());
        jobExecutionLogDto.setThreadPriority(Thread.currentThread().getPriority());
        jobExecutionLogDto.setMessage(message);
        jobExecutionLogDto.setLevel(level.name());
        jobExecutionLogDto.setTimestamp(System.currentTimeMillis());
        batchLogProducer.sendBatchLog(jobExecutionLogDto);
    }

    private void sendLog(JobLogMessageLevel level, String message, Throwable throwable) {
        jobExecutionLogDto.setThread(Thread.currentThread().getName());
        jobExecutionLogDto.setThreadId(Thread.currentThread().getId());
        jobExecutionLogDto.setThreadPriority(Thread.currentThread().getPriority());
        jobExecutionLogDto.setMessage(message);
        jobExecutionLogDto.setLevel(level.name());
        jobExecutionLogDto.setTimestamp(System.currentTimeMillis());
        jobExecutionLogDto.setException(throwable.getClass().getCanonicalName());
        jobExecutionLogDto.setExtendedStackTrace(getStacktrace(throwable));
        batchLogProducer.sendBatchLog(jobExecutionLogDto);
    }

    @Override
    public boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

    @Override
    public void trace(String s) {
        if (logger.isTraceEnabled()) {
            logger.trace(s);
            sendLog(JobLogMessageLevel.TRACE, s);
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
        if (logger.isTraceEnabled()) {
            logger.trace(s);
            sendLog(JobLogMessageLevel.TRACE, s, throwable);
        }
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
        if (logger.isDebugEnabled()) {
            logger.debug(s);
            sendLog(JobLogMessageLevel.DEBUG, s);
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
        if (logger.isDebugEnabled()) {
            logger.debug(s);
            sendLog(JobLogMessageLevel.DEBUG, s, throwable);
        }
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
        if (logger.isInfoEnabled()) {
            logger.info(s);
            sendLog(JobLogMessageLevel.INFO, s);
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
        if (logger.isInfoEnabled()) {
            logger.info(s);
            sendLog(JobLogMessageLevel.INFO, s, throwable);
        }
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
        if (logger.isWarnEnabled()) {
            logger.warn(s);
            sendLog(JobLogMessageLevel.WARN, s);
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
        if (logger.isWarnEnabled()) {
            logger.warn(s);
            sendLog(JobLogMessageLevel.WARN, s, throwable);
        }
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
        if (logger.isErrorEnabled()) {
            logger.error(s);
            sendLog(JobLogMessageLevel.ERROR, s);
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
        if (logger.isErrorEnabled()) {
            logger.error(s);
            sendLog(JobLogMessageLevel.ERROR, s, throwable);
        }
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
