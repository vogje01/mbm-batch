package com.momentum.batch.server.database.domain.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class JobExecutionParamDto extends RepresentationModel<JobExecutionParamDto> {

    /**
     * ID of the parameter
     */
    private String id;
    /**
     * Parameter name
     */
    private String keyName;
    /**
     * Parameter type
     */
    private String typeCd;
    /**
     * Parameter string value
     */
    private String stringVal;
    /**
     * Parameter date value
     */
    private Date dateVal;
    /**
     * Parameter long integer value
     */
    private Long longVal;
    /**
     * Parameter double integer value
     */
    private Double doubleVal;
    /**
     * Parameter identifier flag
     */
    private Boolean identifying;

    /**
     * Default constructor
     */
    public JobExecutionParamDto() {
        // JSON constructor
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

    public String getTypeCd() {
        return typeCd;
    }

    public void setTypeCd(String typeCd) {
        this.typeCd = typeCd;
    }

    public String getStringVal() {
        return stringVal;
    }

    public void setStringVal(String stringVal) {
        this.stringVal = stringVal;
    }

    public Date getDateVal() {
        return dateVal;
    }

    public void setDateVal(Date dateVal) {
        this.dateVal = dateVal;
    }

    public Long getLongVal() {
        return longVal;
    }

    public void setLongVal(Long longVal) {
        this.longVal = longVal;
    }

    public Double getDoubleVal() {
        return doubleVal;
    }

    public void setDoubleVal(Double doubleVal) {
        this.doubleVal = doubleVal;
    }

    public Boolean getIdentifying() {
        return identifying;
    }

    public void setIdentifying(Boolean identifying) {
        this.identifying = identifying;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("keyName", keyName)
                .add("typeCd", typeCd)
                .add("stringVal", stringVal)
                .add("dateVal", dateVal)
                .add("longVal", longVal)
                .add("doubleVal", doubleVal)
                .add("identifying", identifying)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        JobExecutionParamDto that = (JobExecutionParamDto) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(keyName, that.keyName) &&
                Objects.equal(typeCd, that.typeCd) &&
                Objects.equal(stringVal, that.stringVal) &&
                Objects.equal(dateVal, that.dateVal) &&
                Objects.equal(longVal, that.longVal) &&
                Objects.equal(doubleVal, that.doubleVal) &&
                Objects.equal(identifying, that.identifying);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), id, keyName, typeCd, stringVal, dateVal, longVal, doubleVal, identifying);
    }
}
