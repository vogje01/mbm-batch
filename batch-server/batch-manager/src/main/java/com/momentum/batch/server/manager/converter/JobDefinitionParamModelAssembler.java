package com.momentum.batch.server.manager.converter;

import com.momentum.batch.server.database.converter.ModelConverter;
import com.momentum.batch.server.database.domain.JobDefinitionParam;
import com.momentum.batch.server.database.domain.dto.JobDefinitionParamDto;
import com.momentum.batch.server.manager.controller.JobDefinitionParamController;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static java.text.MessageFormat.format;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.4
 */
@Component
public class JobDefinitionParamModelAssembler extends RepresentationModelAssemblerSupport<JobDefinitionParam, JobDefinitionParamDto> {

    private static final Logger logger = LoggerFactory.getLogger(JobDefinitionParamModelAssembler.class);

    private final ModelConverter modelConverter;

    public JobDefinitionParamModelAssembler(ModelConverter modelConverter) {
        super(JobDefinitionParamController.class, JobDefinitionParamDto.class);
        this.modelConverter = modelConverter;
    }

    @Override
    public @NotNull JobDefinitionParamDto toModel(@NotNull JobDefinitionParam entity) {

        JobDefinitionParamDto jobDefinitionParamDto = modelConverter.convertJobDefinitionParamToDto(entity);

        try {
            jobDefinitionParamDto.add(linkTo(methodOn(JobDefinitionParamController.class).findById(jobDefinitionParamDto.getId())).withSelfRel());
            jobDefinitionParamDto.add(linkTo(methodOn(JobDefinitionParamController.class).addJobDefinitionParam(jobDefinitionParamDto.getId(), jobDefinitionParamDto)).withRel("add"));
            jobDefinitionParamDto.add(linkTo(methodOn(JobDefinitionParamController.class).updateJobDefinitionParam(jobDefinitionParamDto.getId(), jobDefinitionParamDto)).withRel("update"));
            jobDefinitionParamDto.add(linkTo(methodOn(JobDefinitionParamController.class).deleteJobDefinitionParam(jobDefinitionParamDto.getId())).withRel("delete"));
        } catch (ResourceNotFoundException e) {
            logger.error(format("Resource not found - jobDefinitionParamId: {0}", jobDefinitionParamDto.getId()));
        }

        return jobDefinitionParamDto;
    }

    @Override
    public @NotNull CollectionModel<JobDefinitionParamDto> toCollectionModel(@NotNull Iterable<? extends JobDefinitionParam> entities) {
        CollectionModel<JobDefinitionParamDto> jobDefinitionParamDtos = super.toCollectionModel(entities);
        jobDefinitionParamDtos.add(linkTo(methodOn(JobDefinitionParamController.class).findAll(null)).withSelfRel());
        return jobDefinitionParamDtos;
    }

    public @NotNull JobDefinitionParam toEntity(JobDefinitionParamDto jobDefinitionParamDto) {
        return modelConverter.convertJobDefinitionParamToEntity(jobDefinitionParamDto);
    }
}
