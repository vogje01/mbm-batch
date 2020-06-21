package com.momentum.batch.server.manager.converter;

import com.momentum.batch.server.database.converter.ModelConverter;
import com.momentum.batch.server.database.domain.JobExecutionInfo;
import com.momentum.batch.server.database.domain.dto.JobExecutionDto;
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
 * @version 0.0.6-SNAPSHOT
 * @since 0.0.4
 */
@Component
public class JobExecutionInfoModelAssembler extends RepresentationModelAssemblerSupport<JobExecutionInfo, JobExecutionDto> {

    private final ModelConverter modelConverter;

    @Autowired
    public JobExecutionInfoModelAssembler(ModelConverter modelConverter) {
        super(JobExecutionController.class, JobExecutionDto.class);
        this.modelConverter = modelConverter;
    }

    @Override
    public @NotNull JobExecutionDto toModel(@NotNull JobExecutionInfo entity) {

        JobExecutionDto jobExecutionDto = modelConverter.convertJobExecutionToDto(entity);

        try {
            jobExecutionDto.add(linkTo(methodOn(JobExecutionController.class).findById(jobExecutionDto.getId())).withSelfRel());
            jobExecutionDto.add(linkTo(methodOn(JobExecutionController.class).delete(jobExecutionDto.getId())).withRel("delete"));
            jobExecutionDto.add(linkTo(methodOn(JobExecutionController.class).restart(jobExecutionDto.getId())).withRel("restart"));

            jobExecutionDto.add(linkTo(methodOn(StepExecutionController.class).findByJobId(jobExecutionDto.getId(), Pageable.unpaged())).withRel("byJobId"));
            jobExecutionDto.add(linkTo(methodOn(JobExecutionParamController.class).findByJobId(jobExecutionDto.getId(), Pageable.unpaged())).withRel("params"));
            jobExecutionDto.add(linkTo(methodOn(JobExecutionLogController.class).findByJobId(jobExecutionDto.getId(), Pageable.unpaged())).withRel("logs"));
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }

        return jobExecutionDto;
    }

    @Override
    public @NotNull CollectionModel<JobExecutionDto> toCollectionModel(@NotNull Iterable<? extends JobExecutionInfo> entities) {
        CollectionModel<JobExecutionDto> JobExecutionDtos = super.toCollectionModel(entities);
        JobExecutionDtos.add(linkTo(methodOn(JobExecutionController.class).findAll(Pageable.unpaged())).withSelfRel());
        return JobExecutionDtos;
    }

    public @NotNull JobExecutionInfo toEntity(JobExecutionDto jobExecutionDto) {
        return modelConverter.convertJobExecutionToEntity(jobExecutionDto);
    }
}
