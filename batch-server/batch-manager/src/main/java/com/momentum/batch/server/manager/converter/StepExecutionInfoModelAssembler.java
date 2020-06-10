package com.momentum.batch.server.manager.converter;

import com.momentum.batch.common.domain.dto.StepExecutionDto;
import com.momentum.batch.server.database.domain.StepExecutionInfo;
import com.momentum.batch.server.manager.controller.JobExecutionLogController;
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
public class StepExecutionInfoModelAssembler extends RepresentationModelAssemblerSupport<StepExecutionInfo, StepExecutionDto> {

    public StepExecutionInfoModelAssembler() {
        super(StepExecutionController.class, StepExecutionDto.class);
    }

    @Override
    public @NotNull StepExecutionDto toModel(@NotNull StepExecutionInfo entity) {
        StepExecutionDto stepExecutionDto = instantiateModel(entity);

        try {
            stepExecutionDto.add(linkTo(methodOn(StepExecutionController.class).findById(stepExecutionDto.getId())).withSelfRel());
            stepExecutionDto.add(linkTo(methodOn(StepExecutionController.class).findByJobId(stepExecutionDto.getJobId(), Pageable.unpaged())).withRel("byStepId"));
            stepExecutionDto.add(linkTo(methodOn(StepExecutionController.class).delete(stepExecutionDto.getId())).withRel("delete"));
            stepExecutionDto.add(linkTo(methodOn(JobExecutionLogController.class).findByStepId(stepExecutionDto.getId(), Pageable.unpaged())).withRel("logs"));
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }

        stepExecutionDto.setId(entity.getId());
        stepExecutionDto.setHostName(entity.getHostName());
        stepExecutionDto.setNodeName(entity.getNodeName());
        stepExecutionDto.setJobName(entity.getJobExecutionInfo().getJobExecutionInstance().getJobName());
        stepExecutionDto.setStatus(entity.getStatus());
        stepExecutionDto.setStepExecutionId(entity.getStepExecutionId());
        stepExecutionDto.setStartTime(entity.getStartTime());
        stepExecutionDto.setEndTime(entity.getEndTime());
        stepExecutionDto.setLastUpdated(entity.getLastUpdated());
        stepExecutionDto.setRunningTime(entity.getRunningTime());

        stepExecutionDto.setCreatedAt(entity.getCreatedAt());
        stepExecutionDto.setCreatedBy(entity.getCreatedBy());
        stepExecutionDto.setModifiedAt(entity.getModifiedAt());
        stepExecutionDto.setModifiedBy(entity.getModifiedBy());

        return stepExecutionDto;
    }

    @Override
    public @NotNull CollectionModel<StepExecutionDto> toCollectionModel(@NotNull Iterable<? extends StepExecutionInfo> entities) {
        CollectionModel<StepExecutionDto> StepExecutionDtos = super.toCollectionModel(entities);

        StepExecutionDtos.add(linkTo(methodOn(StepExecutionController.class).findAll(null)).withSelfRel());

        return StepExecutionDtos;
    }
}
