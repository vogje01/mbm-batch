package com.momentum.batch.server.manager.converter;

import com.momentum.batch.common.domain.dto.JobExecutionParamDto;
import com.momentum.batch.server.database.converter.ModelConverter;
import com.momentum.batch.server.database.domain.JobExecutionParam;
import com.momentum.batch.server.manager.controller.JobExecutionParamController;
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
public class JobExecutionParamModelAssembler extends RepresentationModelAssemblerSupport<JobExecutionParam, JobExecutionParamDto> {

    private final ModelConverter modelConverter;

    public JobExecutionParamModelAssembler(ModelConverter modelConverter) {
        super(JobExecutionParamController.class, JobExecutionParamDto.class);
        this.modelConverter = modelConverter;
    }

    @Override
    public @NotNull JobExecutionParamDto toModel(@NotNull JobExecutionParam entity) {

        JobExecutionParamDto jobExecutionParamDto = modelConverter.convertJobExecutionParamToDto(entity);

        try {
            jobExecutionParamDto.add(linkTo(methodOn(JobExecutionParamController.class).findById(jobExecutionParamDto.getId())).withSelfRel());
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }

        return jobExecutionParamDto;
    }

    @Override
    public @NotNull CollectionModel<JobExecutionParamDto> toCollectionModel(@NotNull Iterable<? extends JobExecutionParam> entities) {
        CollectionModel<JobExecutionParamDto> jobExecutionParamDtos = super.toCollectionModel(entities);
        jobExecutionParamDtos.add(linkTo(methodOn(JobExecutionParamController.class).findAll(null)).withSelfRel());
        return jobExecutionParamDtos;
    }

    public @NotNull JobExecutionParam toEntity(JobExecutionParamDto jobExecutionParamDto) {
        return modelConverter.convertJobExecutionParamToEntity(jobExecutionParamDto);
    }
}
