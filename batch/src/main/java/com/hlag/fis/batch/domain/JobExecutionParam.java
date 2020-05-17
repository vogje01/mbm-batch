package com.hlag.fis.batch.domain;

import com.fasterxml.jackson.annotation.*;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.batch.core.JobParameter;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "BATCH_JOB_EXECUTION_PARAMS")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true, value = {"handler", "hibernateLazyInitializer"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class JobExecutionParam extends RepresentationModel<JobExecutionParam> {

	/**
	 * ID of the parameter
	 */
	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private String id;
    /**
     * Parameter name
     */
    @Column(name = "KEY_NAME")
    private String keyName;
    /**
     * Parameter type
     */
    @Column(name = "TYPE_CD")
    private String typeCd;
    /**
     * Parameter string value
     */
    @Column(name = "STRING_VAL")
    private String stringVal;
    /**
     * Parameter date value
     */
    @Column(name = "DATE_VAL")
    private Date dateVal;
    /**
     * Parameter long integer value
     */
    @Column(name = "LONG_VAL")
    private Long longVal;

    @Column(name = "DOUBLE_VAL")
    private Double doubleVal;
    /**
     * Parameter identifier flag
     */
    @Column(name = "IDENTIFYING")
    private Boolean identifying;
    /**
     * Relationship to the corresponding job execution
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "JOB_EXECUTION_ID")
    private JobExecutionInfo jobExecutionInfo;

	/**
	 * Default constructor
	 */
	public JobExecutionParam() {
		// JSON constructor
	}

	/**
	 * Copy constructor.
	 *
	 * @param keyName parameter key.
	 * @param origin  parameter value.
	 */
	public JobExecutionParam(String keyName, JobParameter origin, JobExecutionInfo jobExecutionInfo) {
        this.jobExecutionInfo = jobExecutionInfo;
        this.keyName = keyName;
        switch (origin.getType()) {
            case STRING:
                this.stringVal = (String) origin.getValue();
                break;
            case DATE:
                this.dateVal = (Date) origin.getValue();
                break;
            case LONG:
                this.longVal = (Long) origin.getValue();
                break;
            case DOUBLE:
                this.doubleVal = (Double) origin.getValue();
                break;
        }
        this.typeCd = origin.getType().name();
        this.identifying = origin.isIdentifying();
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

	public JobExecutionInfo getJobExecutionInfo() {
		return jobExecutionInfo;
	}

	public void setJobExecutionInfo(JobExecutionInfo jobExecutionInfo) {
		this.jobExecutionInfo = jobExecutionInfo;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobExecutionParam that = (JobExecutionParam) o;
        return Objects.equal(id, that.id) &&
            Objects.equal(keyName, that.keyName) &&
            Objects.equal(typeCd, that.typeCd) &&
            Objects.equal(stringVal, that.stringVal) &&
            Objects.equal(dateVal, that.dateVal) &&
            Objects.equal(longVal, that.longVal) &&
            Objects.equal(doubleVal, that.doubleVal) &&
            Objects.equal(identifying, that.identifying) &&
            Objects.equal(jobExecutionInfo, that.jobExecutionInfo);
    }

	@Override
	public int hashCode() {
        return Objects.hashCode(id, keyName, typeCd, stringVal, dateVal, longVal, doubleVal, identifying, jobExecutionInfo);
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
}
