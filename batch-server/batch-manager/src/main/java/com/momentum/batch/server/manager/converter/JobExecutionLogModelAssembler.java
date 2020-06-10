package com.momentum.batch.server.manager.converter;

import com.momentum.batch.common.domain.dto.JobExecutionLogDto;
import com.momentum.batch.server.database.domain.JobExecutionLog;
import com.momentum.batch.server.manager.controller.JobExecutionLogController;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.4
 * @since 0.0.4
 */
@Component
public class JobExecutionLogModelAssembler extends RepresentationModelAssemblerSupport<JobExecutionLog, JobExecutionLogDto> {

    public JobExecutionLogModelAssembler() {
        super(JobExecutionLogController.class, JobExecutionLogDto.class);
    }

    @Override
    public @NotNull JobExecutionLogDto toModel(@NotNull JobExecutionLog entity) {
        JobExecutionLogDto jobExecutionLogDto = instantiateModel(entity);

        jobExecutionLogDto.setId(entity.getId());
        jobExecutionLogDto.setHostName(entity.getHostName());
        jobExecutionLogDto.setNodeName(entity.getNodeName());
        jobExecutionLogDto.setJobName(entity.getJobName());
        jobExecutionLogDto.setStepName(entity.getStepName());
        jobExecutionLogDto.setLevel(entity.getLevel().name());
        jobExecutionLogDto.setTimestamp(entity.getTimestamp());
        jobExecutionLogDto.setThread(entity.getThread());
        jobExecutionLogDto.setThreadId(entity.getThreadId());
        jobExecutionLogDto.setThreadPriority(entity.getThreadPriority());

        try {
            jobExecutionLogDto.add(linkTo(methodOn(JobExecutionLogController.class).findById(jobExecutionLogDto.getId())).withSelfRel());
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }

        return jobExecutionLogDto;
    }

    @Override
    public @NotNull CollectionModel<JobExecutionLogDto> toCollectionModel(@NotNull Iterable<? extends JobExecutionLog> entities) {
        CollectionModel<JobExecutionLogDto> jobExecutionLogDtos = super.toCollectionModel(entities);

        jobExecutionLogDtos.add(linkTo(methodOn(JobExecutionLogController.class).findAll(null)).withSelfRel());

        return jobExecutionLogDtos;
    }
}
