package com.momentum.batch.server.database.domain.dto;

import com.momentum.batch.server.database.domain.BatchPerformanceType;

import java.sql.Timestamp;
import java.util.UUID;

public class BatchPerformanceDtoBuilder {

    private BatchPerformanceDto batchPerformance = new BatchPerformanceDto();

    public BatchPerformanceDtoBuilder withId(String id) {
        batchPerformance.setId(id);
        return this;
    }

    public BatchPerformanceDtoBuilder withRandomId() {
        batchPerformance.setId(UUID.randomUUID().toString());
        return this;
    }

    public BatchPerformanceDtoBuilder withQualifier(String qualifier) {
        batchPerformance.setQualifier(qualifier);
        return this;
    }

    public BatchPerformanceDtoBuilder withMetric(String metric) {
        batchPerformance.setMetric(metric);
        return this;
    }

    public BatchPerformanceDtoBuilder withType(BatchPerformanceType type) {
        batchPerformance.setType(type);
        return this;
    }

    public BatchPerformanceDtoBuilder withValue(double value) {
        batchPerformance.setValue(value);
        return this;
    }

    public BatchPerformanceDtoBuilder withTimestamp(Timestamp timestamp) {
        batchPerformance.setTimestamp(timestamp);
        return this;
    }

    public BatchPerformanceDto build() {
        return batchPerformance;
    }
}
