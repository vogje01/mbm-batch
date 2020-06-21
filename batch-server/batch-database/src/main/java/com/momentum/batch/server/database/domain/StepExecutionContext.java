package com.momentum.batch.server.database.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.momentum.batch.server.database.domain.dto.StepExecutionContextDto;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * Step execution context.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-SNAPSHOT
 * @since 0.0.2
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "BATCH_STEP_EXECUTION_CONTEXT")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true, value = {"handler", "hibernateLazyInitializer"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class StepExecutionContext implements PrimaryKeyIdentifier<String> {

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
    @JoinColumn(name = "STEP_EXECUTION_ID", nullable = false)
    private StepExecutionInfo stepExecutionInfo;

    /**
     * Updates the step execution context.
     *
     * @param stepExecutionContextDto original step execution context.
     */
    public void update(StepExecutionContextDto stepExecutionContextDto) {
        this.shortContext = stepExecutionContextDto.getShortContext();
        this.serializedContext = stepExecutionContextDto.getSerializedContext();
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

    public StepExecutionInfo getStepExecutionInfo() {
        return stepExecutionInfo;
    }

    public void setStepExecutionInfo(StepExecutionInfo stepExecutionInfo) {
        this.stepExecutionInfo = stepExecutionInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StepExecutionContext that = (StepExecutionContext) o;
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
