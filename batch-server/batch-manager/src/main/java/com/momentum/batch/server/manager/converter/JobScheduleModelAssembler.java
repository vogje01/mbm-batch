package com.momentum.batch.server.manager.converter;

import com.momentum.batch.common.domain.dto.JobScheduleDto;
import com.momentum.batch.server.database.domain.JobSchedule;
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
 * @version 0.0.4
 * @since 0.0.4
 */
@Component
public class JobScheduleModelAssembler extends RepresentationModelAssemblerSupport<JobSchedule, JobScheduleDto> {

    public JobScheduleModelAssembler() {
        super(JobExecutionController.class, JobScheduleDto.class);
    }

    @Override
    public @NotNull JobScheduleDto toModel(@NotNull JobSchedule entity) {
        JobScheduleDto jobScheduleDto = instantiateModel(entity);

        try {
            jobScheduleDto.add(linkTo(methodOn(JobExecutionController.class).findById(entity.getId())).withSelfRel());
            jobScheduleDto.add(linkTo(methodOn(JobExecutionController.class).delete(entity.getId())).withRel("delete"));
            jobScheduleDto.add(linkTo(methodOn(JobExecutionController.class).restart(entity.getId())).withRel("restart"));

            jobScheduleDto.add(linkTo(methodOn(StepExecutionController.class).findByJobId(entity.getId(), Pageable.unpaged())).withRel("byJobId"));
            jobScheduleDto.add(linkTo(methodOn(JobExecutionParamController.class).findByJobId(entity.getId(), Pageable.unpaged())).withRel("params"));
            jobScheduleDto.add(linkTo(methodOn(JobExecutionLogController.class).findByJobId(entity.getId(), Pageable.unpaged())).withRel("logs"));
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }

        jobScheduleDto.setId(entity.getId());
        /*jobScheduleDto.setHostName(entity.getHostName());
        jobScheduleDto.setNodeName(entity.getNodeName());
        jobScheduleDto.setJobName(entity.getJobExecutionInstance().getJobName());
        jobScheduleDto.setStatus(entity.getStatus());
        jobScheduleDto.setJobExecutionId(entity.getJobExecutionId());
        jobScheduleDto.setJobPid(entity.getJobPid());
        jobScheduleDto.setJobVersion(entity.getJobVersion());
        jobScheduleDto.setCreateTime(entity.getCreateTime());
        jobScheduleDto.setStartTime(entity.getStartTime());
        jobScheduleDto.setEndTime(entity.getEndTime());
        jobScheduleDto.setStartedBy(entity.getStartedBy());
        jobScheduleDto.setLastUpdated(entity.getLastUpdated());
        jobScheduleDto.setRunningTime(entity.getRunningTime());
        jobScheduleDto.setExitCode(entity.getExitCode());
        jobScheduleDto.setExitMessage(entity.getExitMessage());*/

        jobScheduleDto.setCreatedAt(entity.getCreatedAt());
        jobScheduleDto.setCreatedBy(entity.getCreatedBy());
        jobScheduleDto.setModifiedAt(entity.getModifiedAt());
        jobScheduleDto.setModifiedBy(entity.getModifiedBy());

        return jobScheduleDto;
    }

    @Override
    public @NotNull CollectionModel<JobScheduleDto> toCollectionModel(@NotNull Iterable<? extends JobSchedule> entities) {
        CollectionModel<JobScheduleDto> JobScheduleDtos = super.toCollectionModel(entities);

        JobScheduleDtos.add(linkTo(methodOn(JobExecutionController.class).findAll(null)).withSelfRel());

        return JobScheduleDtos;
    }
}
