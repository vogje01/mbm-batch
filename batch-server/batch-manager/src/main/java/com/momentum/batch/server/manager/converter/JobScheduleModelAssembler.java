package com.momentum.batch.server.manager.converter;

import com.momentum.batch.common.domain.dto.JobScheduleDto;
import com.momentum.batch.server.database.converter.ModelConverter;
import com.momentum.batch.server.database.domain.JobSchedule;
import com.momentum.batch.server.manager.controller.JobExecutionController;
import com.momentum.batch.server.manager.controller.JobExecutionLogController;
import com.momentum.batch.server.manager.controller.JobExecutionParamController;
import com.momentum.batch.server.manager.controller.StepExecutionController;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final ModelConverter modelConverter;

    @Autowired
    public JobScheduleModelAssembler(ModelConverter modelConverter) {
        super(JobExecutionController.class, JobScheduleDto.class);
        this.modelConverter = modelConverter;
    }

    @Override
    public @NotNull JobScheduleDto toModel(@NotNull JobSchedule entity) {

        JobScheduleDto jobScheduleDto = modelConverter.convertJobScheduleToDto(entity);

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
        return jobScheduleDto;
    }

    @Override
    public @NotNull CollectionModel<JobScheduleDto> toCollectionModel(@NotNull Iterable<? extends JobSchedule> entities) {
        CollectionModel<JobScheduleDto> JobScheduleDtos = super.toCollectionModel(entities);
        JobScheduleDtos.add(linkTo(methodOn(JobExecutionController.class).findAll(null)).withSelfRel());
        return JobScheduleDtos;
    }

    public @NotNull JobSchedule toEntity(@NotNull JobScheduleDto jobScheduleDto) {
        return modelConverter.convertJobScheduleToEntity(jobScheduleDto);
    }
}
