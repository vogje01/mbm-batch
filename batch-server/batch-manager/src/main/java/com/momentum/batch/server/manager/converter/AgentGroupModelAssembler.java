package com.momentum.batch.server.manager.converter;

import com.momentum.batch.server.database.converter.ModelConverter;
import com.momentum.batch.server.database.domain.AgentGroup;
import com.momentum.batch.server.database.domain.dto.AgentGroupDto;
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
 * @version 0.0.6-RELEASE
 * @since 0.0.4
 */
@Component
public class AgentGroupModelAssembler extends RepresentationModelAssemblerSupport<AgentGroup, AgentGroupDto> {

    private final ModelConverter modelConverter;

    @Autowired
    public AgentGroupModelAssembler(ModelConverter modelConverter) {
        super(AgentGroupController.class, AgentGroupDto.class);
        this.modelConverter = modelConverter;
    }

    @Override
    public @NotNull AgentGroupDto toModel(@NotNull AgentGroup entity) {

        AgentGroupDto agentGroupDto = modelConverter.convertAgentGroupToDto(entity);

        try {
            agentGroupDto.add(linkTo(methodOn(AgentGroupController.class).findById(agentGroupDto.getId())).withSelfRel());
            agentGroupDto.add(linkTo(methodOn(AgentGroupController.class).update(agentGroupDto.getId(), agentGroupDto)).withRel("update"));
            agentGroupDto.add(linkTo(methodOn(AgentGroupController.class).delete(agentGroupDto.getId())).withRel("delete"));
            agentGroupDto.add(linkTo(methodOn(AgentGroupController.class).addAgent(null, null)).withRel("addAgent").expand(agentGroupDto.getId(), ""));
            agentGroupDto.add(linkTo(methodOn(AgentGroupController.class).removeAgent(null, null)).withRel("removeAgent").expand(agentGroupDto.getId(), ""));
            agentGroupDto.add(linkTo(methodOn(AgentController.class).findByAgentGroup(agentGroupDto.getId(), Pageable.unpaged())).withRel("agents"));
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }

        return agentGroupDto;
    }

    @Override
    public @NotNull CollectionModel<AgentGroupDto> toCollectionModel(@NotNull Iterable<? extends AgentGroup> entities) {
        CollectionModel<AgentGroupDto> agentGroupDtos = super.toCollectionModel(entities);
        try {
            agentGroupDtos.add(linkTo(methodOn(AgentGroupController.class).findAll(Pageable.unpaged())).withSelfRel());
            agentGroupDtos.add(linkTo(methodOn(AgentGroupController.class).insert(new AgentGroupDto())).withRel("insert"));
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
        return agentGroupDtos;
    }

    public @NotNull AgentGroup toEntity(AgentGroupDto agentGroupDto) {
        return modelConverter.convertAgentGroupToEntity(agentGroupDto);
    }
}
