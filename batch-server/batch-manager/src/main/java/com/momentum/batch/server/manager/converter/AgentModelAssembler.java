package com.momentum.batch.server.manager.converter;

import com.momentum.batch.common.domain.dto.AgentDto;
import com.momentum.batch.server.database.converter.ModelConverter;
import com.momentum.batch.server.database.domain.Agent;
import com.momentum.batch.server.manager.controller.AgentController;
import com.momentum.batch.server.manager.controller.AgentGroupController;
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
public class AgentModelAssembler extends RepresentationModelAssemblerSupport<Agent, AgentDto> {

    private final ModelConverter modelConverter;

    @Autowired
    public AgentModelAssembler(ModelConverter modelConverter) {
        super(AgentController.class, AgentDto.class);
        this.modelConverter = modelConverter;
    }

    @Override
    public @NotNull AgentDto toModel(@NotNull Agent entity) {

        AgentDto agentDto = modelConverter.convertAgentToDto(entity);

        try {
            // Agent links
            agentDto.add(linkTo(methodOn(AgentController.class).findById(agentDto.getId())).withSelfRel());
            agentDto.add(linkTo(methodOn(AgentController.class).updateAgent(agentDto)).withRel("update"));
            agentDto.add(linkTo(methodOn(AgentController.class).deleteAgent(agentDto.getId())).withRel("delete"));

            // Agent status links
            agentDto.add(linkTo(methodOn(AgentController.class).pauseAgent(agentDto.getId())).withRel("pauseAgent"));

            // Agent group links
            agentDto.add(linkTo(methodOn(AgentGroupController.class).findByAgent(agentDto.getId(), Pageable.unpaged())).withRel("agentGroups"));
            agentDto.add(linkTo(methodOn(AgentController.class).addAgentGroup(null, null)).withRel("addAgentGroup").expand(agentDto.getId(), ""));
            agentDto.add(linkTo(methodOn(AgentController.class).removeAgentGroup(null, null)).withRel("removeAgentGroup").expand(agentDto.getId(), ""));

            // Scheduler links
            agentDto.add(linkTo(methodOn(AgentController.class).getSchedules(agentDto.getId(), Pageable.unpaged())).withRel("schedules"));
            agentDto.add(linkTo(methodOn(AgentController.class).addSchedule(null, null)).withRel("addJobSchedule").expand(agentDto.getId(), ""));
            agentDto.add(linkTo(methodOn(AgentController.class).removeSchedule(null, null)).withRel("removeJobSchedule").expand(agentDto.getId(), ""));
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }

        return agentDto;
    }

    @Override
    public @NotNull CollectionModel<AgentDto> toCollectionModel(@NotNull Iterable<? extends Agent> entities) {
        CollectionModel<AgentDto> AgentDtos = super.toCollectionModel(entities);
        AgentDtos.add(linkTo(methodOn(AgentController.class).findAll(null)).withSelfRel());
        return AgentDtos;
    }

    public @NotNull Agent toEntity(AgentDto agentDto) {
        return modelConverter.convertAgentToEntity(agentDto);
    }
}
