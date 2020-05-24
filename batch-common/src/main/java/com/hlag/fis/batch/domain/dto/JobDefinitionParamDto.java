package com.hlag.fis.batch.domain.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;

/**
 * Job definition parameter DTO.
 *
 * @author Jens Vogt (jens.vogt@ext.hlag.com)
 * @version 0.0.3
 * @since 0.0.3
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class JobDefinitionParamDto extends RepresentationModel<JobDefinitionParamDto> {

    /**
     * Primary key
     */
    private String id;
    /**
     * Key name
     */
    private String keyName;
    /**
     * Value type
     */
    private String type;
    /**
     * Value
     */
    private String value;
    /**
     * String value
     */
    private String stringValue;
    /**
     * Date value
     */
    private Date dateValue;
    /**
     * Long value
     */
    private Long longValue;
    /**
     * Double value
     */
    private Double doubleValue;
    /**
     * Boolean value
     */
    private Boolean booleanValue;
    /**
     * Total count
     */
    private Long totalSize;
    /**
     * Job definition DTO
     */
    private JobDefinitionDto jobDefinitionDto;

    /**
     * Constructor
     */
    public JobDefinitionParamDto() {
        // JPA constructor
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    public Date getDateValue() {
        return dateValue;
    }

    public void setDateValue(Date dateValue) {
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

    public Long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Long totalSize) {
        this.totalSize = totalSize;
    }

    public JobDefinitionDto getJobDefinitionDto() {
        return jobDefinitionDto;
    }

    public void setJobDefinitionDto(JobDefinitionDto jobDefinitionDto) {
        this.jobDefinitionDto = jobDefinitionDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        JobDefinitionParamDto that = (JobDefinitionParamDto) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(keyName, that.keyName) &&
                Objects.equal(type, that.type) &&
                Objects.equal(value, that.value) &&
                Objects.equal(stringValue, that.stringValue) &&
                Objects.equal(dateValue, that.dateValue) &&
                Objects.equal(longValue, that.longValue) &&
                Objects.equal(doubleValue, that.doubleValue) &&
                Objects.equal(booleanValue, that.booleanValue) &&
                Objects.equal(totalSize, that.totalSize) &&
                Objects.equal(jobDefinitionDto, that.jobDefinitionDto);
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
                .add("totalCount", totalSize)
                .add("jobDefinitionDto", jobDefinitionDto)
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), id, keyName, type, value, stringValue, dateValue, longValue, doubleValue, booleanValue, totalSize, jobDefinitionDto);
    }

}
