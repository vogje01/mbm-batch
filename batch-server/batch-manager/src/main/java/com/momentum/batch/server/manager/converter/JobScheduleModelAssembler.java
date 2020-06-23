package com.momentum.batch.server.manager.converter;

import com.momentum.batch.server.database.converter.ModelConverter;
import com.momentum.batch.server.database.domain.JobSchedule;
import com.momentum.batch.server.database.domain.dto.AgentDto;
import com.momentum.batch.server.database.domain.dto.JobScheduleDto;
import com.momentum.batch.server.manager.controller.AgentController;
import com.momentum.batch.server.manager.controller.JobExecutionController;
import com.momentum.batch.server.manager.controller.JobScheduleController;
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
 * @version 0.0.6-RELEASE
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
            jobScheduleDto.add(linkTo(methodOn(JobScheduleController.class).findById(jobScheduleDto.getId())).withSelfRel());
            jobScheduleDto.add(linkTo(methodOn(JobScheduleController.class).update(jobScheduleDto.getId(), jobScheduleDto)).withRel("update"));
            jobScheduleDto.add(linkTo(methodOn(JobScheduleController.class).delete(jobScheduleDto.getId())).withRel("delete"));

            // Agent links
            jobScheduleDto.add(linkTo(methodOn(JobScheduleController.class).getAgents(jobScheduleDto.getId(), Pageable.unpaged())).withRel("agents"));
            jobScheduleDto.add(linkTo(methodOn(JobScheduleController.class).addAgent(null, null)).withRel("addAgent").expand(jobScheduleDto.getId(), ""));
            jobScheduleDto.add(linkTo(methodOn(JobScheduleController.class).removeAgent(null, null)).withRel("removeAgent").expand(jobScheduleDto.getId(), ""));

            // Agent group links
            jobScheduleDto.add(linkTo(methodOn(JobScheduleController.class).getAgentGroups(jobScheduleDto.getId(), Pageable.unpaged())).withRel("agentGroups"));
            jobScheduleDto.add(linkTo(methodOn(JobScheduleController.class).addAgentGroup(null, null)).withRel("addAgentGroup").expand(jobScheduleDto.getId(), ""));
            jobScheduleDto.add(linkTo(methodOn(JobScheduleController.class).removeAgentGroup(null, null)).withRel("removeAgentGroup").expand(jobScheduleDto.getId(), ""));

            jobScheduleDto.add(linkTo(methodOn(AgentController.class).updateAgent(new AgentDto())).withRel("updateAgent"));

            // Job schedule start / stop
            jobScheduleDto.add(linkTo(methodOn(JobScheduleController.class).startJobSchedule(jobScheduleDto.getId())).withRel("startJobSchedule"));

        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
        return jobScheduleDto;
    }

    @Override
    public @NotNull CollectionModel<JobScheduleDto> toCollectionModel(@NotNull Iterable<? extends JobSchedule> entities) {
        CollectionModel<JobScheduleDto> JobScheduleDtos = super.toCollectionModel(entities);
        JobScheduleDtos.add(linkTo(methodOn(JobExecutionController.class).findAll(null, null, null)).withSelfRel());
        return JobScheduleDtos;
    }

    public @NotNull JobSchedule toEntity(@NotNull JobScheduleDto jobScheduleDto) {
        return modelConverter.convertJobScheduleToEntity(jobScheduleDto);
    }
}
