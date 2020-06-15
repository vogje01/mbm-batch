package com.momentum.batch.server.database.domain.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.momentum.batch.server.database.domain.BatchPerformanceType;

import java.sql.Timestamp;

/**
 * Class description.
 *
 * @author Jens Vogt jensvogt47@gmail.com
 * @version 0.0.4
 * @since 0.0.3
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class BatchPerformanceDto {

    /**
     * Primary key
     */
    private String id;
    /**
     * Qualifier
     */
    private String qualifier;
    /**
     * Metric
     */
    private String metric;
    /**
     * Value
     */
    private Double value;
    /**
     * Type
     */
    private BatchPerformanceType type;
    /**
     * Timestamp
     */
    private Timestamp timestamp;

    public BatchPerformanceDto() {
        // Intentionally empty
    }

    public BatchPerformanceDto(String qualifier, String metric, BatchPerformanceType type, double value) {
        this.qualifier = qualifier;
        this.metric = metric;
        this.value = value;
        this.type = type;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String hostName) {
        this.qualifier = hostName;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public BatchPerformanceType getType() {
        return type;
    }

    public void setType(BatchPerformanceType type) {
        this.type = type;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BatchPerformanceDto that = (BatchPerformanceDto) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(qualifier, that.qualifier) &&
                Objects.equal(metric, that.metric) &&
                Objects.equal(value, that.value) &&
                type == that.type &&
                Objects.equal(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, qualifier, metric, value, type, timestamp);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("qualifier", qualifier)
                .add("metric", metric)
                .add("value", value)
                .add("type", type)
                .add("timestamp", timestamp)
                .toString();
    }

}
