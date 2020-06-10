package com.momentum.batch.server.manager.converter;

import com.momentum.batch.common.domain.dto.JobExecutionDto;
import com.momentum.batch.server.database.domain.JobExecutionInfo;
import com.momentum.batch.server.manager.controller.JobExecutionController;
import com.momentum.batch.server.manager.controller.JobExecutionLogController;
import com.momentum.batch.server.manager.controller.JobExecutionParamController;
import com.momentum.batch.server.manager.controller.StepExecutionController;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.1
 * @since 0.0.1
 */
@Component
public class JobExecutionInfoModelAssembler extends RepresentationModelAssemblerSupport<JobExecutionInfo, JobExecutionDto> {

    public JobExecutionInfoModelAssembler() {
        super(JobExecutionController.class, JobExecutionDto.class);
    }

    @Override
    public @NotNull JobExecutionDto toModel(@NotNull JobExecutionInfo entity) {
        JobExecutionDto jobExecutionDto = instantiateModel(entity);

        try {
            jobExecutionDto.add(linkTo(methodOn(JobExecutionController.class).findById(entity.getId())).withSelfRel());
            jobExecutionDto.add(linkTo(methodOn(JobExecutionController.class).delete(entity.getId())).withRel("delete"));
            jobExecutionDto.add(linkTo(methodOn(JobExecutionController.class).restart(entity.getId())).withRel("restart"));

            jobExecutionDto.add(linkTo(methodOn(StepExecutionController.class).findByJobId(entity.getId(), Pageable.unpaged())).withRel("byJobId"));
            jobExecutionDto.add(linkTo(methodOn(JobExecutionParamController.class).findByJobId(entity.getId(), Pageable.unpaged())).withRel("params"));
            jobExecutionDto.add(linkTo(methodOn(JobExecutionLogController.class).findByJobId(entity.getId(), Pageable.unpaged())).withRel("logs"));
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }

        jobExecutionDto.setId(entity.getId());
        jobExecutionDto.setHostName(entity.getHostName());
        jobExecutionDto.setNodeName(entity.getNodeName());
        jobExecutionDto.setJobName(entity.getJobExecutionInstance().getJobName());
        jobExecutionDto.setStatus(entity.getStatus());
        jobExecutionDto.setJobExecutionId(entity.getJobExecutionId());
        jobExecutionDto.setJobPid(entity.getJobPid());
        jobExecutionDto.setJobVersion(entity.getJobVersion());
        jobExecutionDto.setCreateTime(entity.getCreateTime());
        jobExecutionDto.setStartTime(entity.getStartTime());
        jobExecutionDto.setEndTime(entity.getEndTime());
        jobExecutionDto.setStartedBy(entity.getStartedBy());
        jobExecutionDto.setLastUpdated(entity.getLastUpdated());
        jobExecutionDto.setRunningTime(entity.getRunningTime());
        jobExecutionDto.setExitCode(entity.getExitCode());
        jobExecutionDto.setExitMessage(entity.getExitMessage());

        jobExecutionDto.setCreatedAt(entity.getCreatedAt());
        jobExecutionDto.setCreatedBy(entity.getCreatedBy());
        jobExecutionDto.setModifiedAt(entity.getModifiedAt());
        jobExecutionDto.setModifiedBy(entity.getModifiedBy());

        return jobExecutionDto;
    }

    @Override
    public @NotNull CollectionModel<JobExecutionDto> toCollectionModel(@NotNull Iterable<? extends JobExecutionInfo> entities) {
        CollectionModel<JobExecutionDto> JobExecutionDtos = super.toCollectionModel(entities);

        JobExecutionDtos.add(linkTo(methodOn(JobExecutionController.class).findAll(null)).withSelfRel());

        return JobExecutionDtos;
    }
}
