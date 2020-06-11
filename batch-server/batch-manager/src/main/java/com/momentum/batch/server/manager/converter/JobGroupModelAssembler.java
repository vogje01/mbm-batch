package com.momentum.batch.server.manager.converter;

import com.momentum.batch.common.domain.dto.JobGroupDto;
import com.momentum.batch.server.database.converter.ModelConverter;
import com.momentum.batch.server.database.domain.JobGroup;
import com.momentum.batch.server.manager.controller.JobDefinitionController;
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
public class JobGroupModelAssembler extends RepresentationModelAssemblerSupport<JobGroup, JobGroupDto> {

    private final ModelConverter modelConverter;

    @Autowired
    public JobGroupModelAssembler(ModelConverter modelConverter) {
        super(JobGroupController.class, JobGroupDto.class);
        this.modelConverter = modelConverter;
    }

    @Override
    public @NotNull JobGroupDto toModel(@NotNull JobGroup entity) {

        JobGroupDto jobGroupDto = modelConverter.convertJobGroupToDto(entity);

        try {
            jobGroupDto.add(linkTo(methodOn(JobGroupController.class).findById(jobGroupDto.getId())).withSelfRel());
            jobGroupDto.add(linkTo(methodOn(JobGroupController.class).update(jobGroupDto.getId(), jobGroupDto)).withRel("update"));
            jobGroupDto.add(linkTo(methodOn(JobGroupController.class).delete(jobGroupDto.getId())).withRel("delete"));

            // Job definition links
            jobGroupDto.add(linkTo(methodOn(JobDefinitionController.class).findByJobGroup(jobGroupDto.getId(), Pageable.unpaged())).withRel("jobDefinitions"));
            jobGroupDto.add(linkTo(methodOn(JobGroupController.class).addJobDefinition(null, null)).withRel("addJobDefinition").expand(jobGroupDto.getId(), ""));
            jobGroupDto.add(linkTo(methodOn(JobGroupController.class).removeJobDefinition(null, null)).withRel("removeJobDefinition").expand(jobGroupDto.getId(), ""));
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }

        return jobGroupDto;
    }

    @Override
    public @NotNull CollectionModel<JobGroupDto> toCollectionModel(@NotNull Iterable<? extends JobGroup> entities) {
        CollectionModel<JobGroupDto> jobGroupDtos = super.toCollectionModel(entities);
        jobGroupDtos.add(linkTo(methodOn(JobGroupController.class).findAll(Pageable.unpaged())).withSelfRel());
        return jobGroupDtos;
    }

    public @NotNull JobGroup toEntity(JobGroupDto jobGroupDto) {
        return modelConverter.convertJobGroupToEntity(jobGroupDto);
    }
}
