package com.hlag.fis.batch.writer;

import com.ibm.db2.jcc.am.SqlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.PessimisticLockingFailureException;

public class DefaultSkipPolicy implements SkipPolicy {

    private static final Logger logger = LoggerFactory.getLogger(DefaultSkipPolicy.class);

    @Override
    public boolean shouldSkip(Throwable throwable, int i) throws SkipLimitExceededException {
        if (throwable instanceof CannotAcquireLockException) {
            logger.warn("Rollback exception found - count: " + i);
            return true;
        } else if (throwable instanceof SqlException) {
            logger.warn("SQL Exception found - count: " + i);
            return true;
        } else if (throwable instanceof PessimisticLockingFailureException) {
            logger.warn("Pessimistic lock Exception found - count: " + i);
        }
        return false;
    }
}
