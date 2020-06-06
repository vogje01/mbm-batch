package com.momentum.batch.client.jobs.common.writer.policy;

import com.mysql.cj.jdbc.exceptions.MySQLTransactionRollbackException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;

public class MysqlSkipPolicy implements SkipPolicy {

    private static final Logger logger = LoggerFactory.getLogger(MysqlSkipPolicy.class);

    @Override
    public boolean shouldSkip(Throwable throwable, int i) throws SkipLimitExceededException {
        if (throwable instanceof MySQLTransactionRollbackException) {
            logger.warn("Rollback exception found - count: " + i);
            return true;
        }
        return false;
    }
}
