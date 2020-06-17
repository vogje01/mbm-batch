package com.momentum.batch.server.database.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.momentum.batch.common.domain.JobDefinitionParamType;
import com.momentum.batch.common.domain.PrimaryKeyIdentifier;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Job definition parameter.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-SNAPSHOT
 * @since 0.0.3
 */
@Entity
@Table(name = "BATCH_JOB_DEFINITION_PARAMS")
@EntityListeners(AuditingEntityListener.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true, value = {"handler", "hibernateLazyInitializer"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class JobDefinitionParam extends Auditing implements PrimaryKeyIdentifier<String> {

    /**
     * Primary key
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;
    /**
     * Key name
     */
    @Column(name = "KEY_NAME")
    private String keyName;
    /**
     * Value type
     */
    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private JobDefinitionParamType type;
    /**
     * Value
     */
    @Column(name = "VALUE")
    private String value;
    /**
     * String value
     */
    @Column(name = "STRING_VAL")
    private String stringValue;
    /**
     * Date value
     */
    @Column(name = "DATE_VAL")
    private LocalDate dateValue;
    /**
     * Long value
     */
    @Column(name = "LONG_VAL")
    private Long longValue;
    /**
     * Double value
     */
    @Column(name = "DOUBLE_VAL")
    private Double doubleValue;
    /**
     * Boolean value
     */
    @Column(name = "BOOLEAN_VAL")
    private Boolean booleanValue;
    /**
     * Link to the corresponding job execution info.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private JobDefinition jobDefinition;

    public JobDefinitionParam() {
        // JPA constructor
    }

    public void update(JobDefinitionParam origin) {
        this.keyName = origin.getKeyName();
        this.type = origin.getType();
        this.value = origin.getValue();
        this.stringValue = origin.getStringValue();
        this.longValue = origin.getLongValue();
        this.dateValue = origin.getDateValue();
        this.doubleValue = origin.getDoubleValue();
        this.booleanValue = origin.getBooleanValue();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public JobDefinitionParamType getType() {
        return type;
    }

    public void setType(JobDefinitionParamType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public LocalDate getDateValue() {
        return dateValue;
    }

    public void setDateValue(LocalDate dateValue) {
        this.dateValue = dateValue;
    }

    public Long getLongValue() {
        return longValue;
    }

    public void setLongValue(Long longValue) {
        this.longValue = longValue;
    }

    public Double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(Double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public Boolean getBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(Boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public JobDefinition getJobDefinition() {
        return jobDefinition;
    }

    public void setJobDefinition(JobDefinition jobDefinition) {
        this.jobDefinition = jobDefinition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobDefinitionParam that = (JobDefinitionParam) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(keyName, that.keyName) &&
                type == that.type &&
                Objects.equals(value, that.value) &&
                Objects.equals(stringValue, that.stringValue) &&
                Objects.equals(dateValue, that.dateValue) &&
                Objects.equals(longValue, that.longValue) &&
                Objects.equals(doubleValue, that.doubleValue) &&
                Objects.equals(booleanValue, that.booleanValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, keyName, type, value, stringValue, dateValue, longValue, doubleValue, booleanValue);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("keyName", keyName)
                .add("type", type)
                .add("value", value)
                .add("stringValue", stringValue)
                .add("dateValue", dateValue)
                .add("longValue", longValue)
                .add("doubleValue", doubleValue)
                .add("booleanValue", booleanValue)
                .toString();
    }
}
