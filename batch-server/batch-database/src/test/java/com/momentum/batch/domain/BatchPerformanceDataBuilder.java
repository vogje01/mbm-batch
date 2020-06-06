package com.momentum.batch.domain;

import java.sql.Timestamp;
import java.util.UUID;

public class BatchPerformanceDataBuilder {

    private BatchPerformance batchPerformance = new BatchPerformance();

    public BatchPerformanceDataBuilder withId(String id) {
        batchPerformance.setId(id);
        return this;
    }

    public BatchPerformanceDataBuilder withRandomId() {
        batchPerformance.setId(UUID.randomUUID().toString());
        return this;
    }

    public BatchPerformanceDataBuilder withQualifier(String qualifier) {
        batchPerformance.setQualifier(qualifier);
        return this;
    }

    public BatchPerformanceDataBuilder withType(BatchPerformanceType type) {
        batchPerformance.setType(type);
        return this;
    }

    public BatchPerformanceDataBuilder withMetric(String metric) {
        batchPerformance.setMetric(metric);
        return this;
    }

    public BatchPerformanceDataBuilder withValue(double value) {
        batchPerformance.setValue(value);
        return this;
    }

    public BatchPerformanceDataBuilder withTimestamp(Timestamp timestamp) {
        batchPerformance.setTimestamp(timestamp);
        return this;
    }

    public BatchPerformance build() {
        return batchPerformance;
    }
}
