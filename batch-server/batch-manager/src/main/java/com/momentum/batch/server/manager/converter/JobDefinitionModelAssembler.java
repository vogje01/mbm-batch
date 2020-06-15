package com.momentum.batch.server.manager.converter;

import com.momentum.batch.server.database.converter.ModelConverter;
import com.momentum.batch.server.database.domain.JobDefinition;
import com.momentum.batch.server.database.domain.dto.JobDefinitionDto;
import com.momentum.batch.server.database.domain.dto.JobDefinitionParamDto;
import com.momentum.batch.server.manager.controller.JobDefinitionController;
import com.momentum.batch.server.manager.controller.JobDefinitionParamController;
import com.momentum.batch.server.manager.controller.JobExecutionController;
import com.momentum.batch.server.manager.controller.JobGroupController;
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
public class JobDefinitionModelAssembler extends RepresentationModelAssemblerSupport<JobDefinition, JobDefinitionDto> {

    private final ModelConverter modelConverter;

    @Autowired
    public JobDefinitionModelAssembler(ModelConverter modelConverter) {
        super(JobExecutionController.class, JobDefinitionDto.class);
        this.modelConverter = modelConverter;
    }

    @Override
    public @NotNull JobDefinitionDto toModel(@NotNull JobDefinition entity) {

        JobDefinitionDto jobDefinitionDto = modelConverter.convertJobDefinitionToDto(entity);

        try {
            jobDefinitionDto.add(linkTo(methodOn(JobDefinitionController.class).findById(jobDefinitionDto.getId())).withSelfRel());
            jobDefinitionDto.add(linkTo(methodOn(JobDefinitionController.class).update(jobDefinitionDto.getId(), jobDefinitionDto)).withRel("update"));
            jobDefinitionDto.add(linkTo(methodOn(JobDefinitionController.class).delete(jobDefinitionDto.getId())).withRel("delete"));

            // Job groups links
            jobDefinitionDto.add(linkTo(methodOn(JobGroupController.class).findByJobDefinition(jobDefinitionDto.getId(), Pageable.unpaged())).withRel("jobGroups"));
            jobDefinitionDto.add(linkTo(methodOn(JobDefinitionController.class).addJobGroup(null, null)).withRel("addJobGroup").expand(jobDefinitionDto.getId(), ""));
            jobDefinitionDto.add(linkTo(methodOn(JobDefinitionController.class).removeJobGroup(null, null)).withRel("removeJobGroup").expand(jobDefinitionDto.getId(), ""));

            // Job parameters links
            jobDefinitionDto.add(linkTo(methodOn(JobDefinitionParamController.class).addJobDefinitionParam(jobDefinitionDto.getId(), new JobDefinitionParamDto())).withRel("addParam"));
            jobDefinitionDto.add(linkTo(methodOn(JobDefinitionParamController.class).findByJobDefinitionId(jobDefinitionDto.getId(), Pageable.unpaged())).withRel("params"));

            // Start stop links
            jobDefinitionDto.add(linkTo(methodOn(JobDefinitionController.class).start(null, null)).withRel("startJob").expand(jobDefinitionDto.getId(), ""));
            jobDefinitionDto.add(linkTo(methodOn(JobDefinitionController.class).stop(null)).withRel("stopJob").expand(jobDefinitionDto.getId(), ""));

        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
        return jobDefinitionDto;
    }

    @Override
    public @NotNull CollectionModel<JobDefinitionDto> toCollectionModel(@NotNull Iterable<? extends JobDefinition> entities) {
        CollectionModel<JobDefinitionDto> JobDefinitionDtos = super.toCollectionModel(entities);
        JobDefinitionDtos.add(linkTo(methodOn(JobExecutionController.class).findAll(null)).withSelfRel());
        return JobDefinitionDtos;
    }

    public @NotNull JobDefinition toEntity(@NotNull JobDefinitionDto jobDefinitionDto) {
        return modelConverter.convertJobDefinitionToEntity(jobDefinitionDto);
    }
}
