package com.hlag.fis.batch.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Class description.
 *
 * @author Jens Vogt jensvogt47@gmail.com
 * @version 0.0.3
 * @since 0.0.3
 */
@Entity
@Table(name = "BATCH_PERFORMANCE")
public class BatchPerformance implements PrimaryKeyIdentifier<String> {

    /**
     * Primary key
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;
    /**
     * Version
     */
    @Version
    private Long version;
    /**
     * Qualifier
     */
    @Column(name = "QUALIFIER")
    private String qualifier;
    /**
     * Metric
     */
    @Column(name = "METRIC")
    private String metric;
    /**
     * Value
     */
    @Column(name = "VALUE")
    private Double value;
    /**
     * Type
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE")
    private BatchPerformanceType type;
    /**
     * Timestamp
     */
    @Column(name = "TIMESTAMP")
    private Timestamp timestamp;

    public BatchPerformance() {
        // Intentionally empty
    }

    public BatchPerformance(String qualifier, String metric, BatchPerformanceType type, double value) {
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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
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
        BatchPerformance that = (BatchPerformance) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(version, that.version) &&
                Objects.equal(qualifier, that.qualifier) &&
                Objects.equal(metric, that.metric) &&
                Objects.equal(value, that.value) &&
                type == that.type &&
                Objects.equal(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, version, qualifier, metric, value, type, timestamp);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("version", version)
                .add("qualifier", qualifier)
                .add("metric", metric)
                .add("value", value)
                .add("type", type)
                .add("timestamp", timestamp)
                .toString();
    }

}
