package com.momentum.batch.server.manager.converter;

import com.momentum.batch.common.domain.dto.StepExecutionDto;
import com.momentum.batch.server.database.converter.ModelConverter;
import com.momentum.batch.server.database.domain.StepExecutionInfo;
import com.momentum.batch.server.manager.controller.JobExecutionLogController;
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
public class StepExecutionInfoModelAssembler extends RepresentationModelAssemblerSupport<StepExecutionInfo, StepExecutionDto> {

    private final ModelConverter modelConverter;

    @Autowired
    public StepExecutionInfoModelAssembler(ModelConverter modelConverter) {
        super(StepExecutionController.class, StepExecutionDto.class);
        this.modelConverter = modelConverter;
    }

    @Override
    public @NotNull StepExecutionDto toModel(@NotNull StepExecutionInfo entity) {

        StepExecutionDto stepExecutionDto = modelConverter.convertStepExecutionToDto(entity);

        try {
            stepExecutionDto.add(linkTo(methodOn(StepExecutionController.class).findById(stepExecutionDto.getId())).withSelfRel());
            stepExecutionDto.add(linkTo(methodOn(StepExecutionController.class).findByJobId(stepExecutionDto.getJobId(), Pageable.unpaged())).withRel("byStepId"));
            stepExecutionDto.add(linkTo(methodOn(StepExecutionController.class).delete(stepExecutionDto.getId())).withRel("delete"));
            stepExecutionDto.add(linkTo(methodOn(JobExecutionLogController.class).findByStepId(stepExecutionDto.getId(), Pageable.unpaged())).withRel("logs"));
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
        return stepExecutionDto;
    }

    @Override
    public @NotNull CollectionModel<StepExecutionDto> toCollectionModel(@NotNull Iterable<? extends StepExecutionInfo> entities) {
        CollectionModel<StepExecutionDto> StepExecutionDtos = super.toCollectionModel(entities);
        StepExecutionDtos.add(linkTo(methodOn(StepExecutionController.class).findAll(null)).withSelfRel());
        return StepExecutionDtos;
    }

    public @NotNull StepExecutionInfo toEntity(StepExecutionDto stepExecutionDto) {
        return modelConverter.convertStepExecutionToEntity(stepExecutionDto);
    }
}
