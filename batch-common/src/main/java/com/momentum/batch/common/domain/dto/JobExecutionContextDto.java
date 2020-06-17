package com.momentum.batch.common.domain.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.springframework.hateoas.RepresentationModel;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * Job execution context DTO.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-SNAPSHOT
 * @since 0.0.3
 */
@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true, value = {"handler", "hibernateLazyInitializer"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class JobExecutionContextDto extends RepresentationModel<JobExecutionContextDto> {

    private String id;

    private String shortContext;

    private String serializedContext;

    private JobExecutionDto jobExecutionDto;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobExecutionContextDto that = (JobExecutionContextDto) o;
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
