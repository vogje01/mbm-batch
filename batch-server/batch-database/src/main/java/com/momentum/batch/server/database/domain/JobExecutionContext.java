package com.momentum.batch.server.database.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.momentum.batch.common.domain.PrimaryKeyIdentifier;
import com.momentum.batch.common.domain.dto.JobExecutionContextDto;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * Job execution context.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.5-RELEASE
 * @since 0.0.2
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "BATCH_JOB_EXECUTION_CONTEXT")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true, value = {"handler", "hibernateLazyInitializer"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class JobExecutionContext implements PrimaryKeyIdentifier<String> {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @Column(name = "SHORT_CONTEXT")
    private String shortContext;

    @Lob
    @Column(name = "SERIALIZED_CONTEXT")
    private String serializedContext;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "JOB_EXECUTION_ID", nullable = false)
    private JobExecutionInfo jobExecutionInfo;

    /**
     * Updates the job execution context.
     *
     * @param jobExecutionContextDto original job execution context.
     */
    public void update(JobExecutionContextDto jobExecutionContextDto) {
        this.shortContext = jobExecutionContextDto.getShortContext();
        this.serializedContext = jobExecutionContextDto.getSerializedContext();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShortContext() {
        return shortContext;
    }

    public void setShortContext(String shortContext) {
        this.shortContext = shortContext;
    }

    public String getSerializedContext() {
        return serializedContext;
    }

    public void setSerializedContext(String serializedContext) {
        this.serializedContext = serializedContext;
    }

    public JobExecutionInfo getJobExecutionInfo() {
        return jobExecutionInfo;
    }

    public void setJobExecutionInfo(JobExecutionInfo jobExecutionInfo) {
        this.jobExecutionInfo = jobExecutionInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobExecutionContext that = (JobExecutionContext) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(shortContext, that.shortContext) &&
                Objects.equal(serializedContext, that.serializedContext);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, shortContext, serializedContext);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("shortContext", shortContext)
                .add("serializedContext", serializedContext)
                .toString();
    }
}
