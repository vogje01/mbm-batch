package com.momentum.batch.server.manager.converter;

import com.momentum.batch.server.database.converter.ModelConverter;
import com.momentum.batch.server.database.domain.UserGroup;
import com.momentum.batch.server.database.domain.dto.UserGroupDto;
import com.momentum.batch.server.manager.controller.UserController;
import com.momentum.batch.server.manager.controller.UserGroupController;
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
public class UserGroupModelAssembler extends RepresentationModelAssemblerSupport<UserGroup, UserGroupDto> {

    private final ModelConverter modelConverter;

    @Autowired
    public UserGroupModelAssembler(ModelConverter modelConverter) {
        super(UserGroupController.class, UserGroupDto.class);
        this.modelConverter = modelConverter;
    }

    @Override
    public @NotNull UserGroupDto toModel(@NotNull UserGroup entity) {

        UserGroupDto userGroupDto = modelConverter.convertUserGroupToDto(entity);

        try {
            userGroupDto.add(linkTo(methodOn(UserGroupController.class).findById(userGroupDto.getId())).withSelfRel());
            userGroupDto.add(linkTo(methodOn(UserGroupController.class).insertUserGroup(userGroupDto)).withRel("insert"));
            userGroupDto.add(linkTo(methodOn(UserGroupController.class).updateUserGroup(userGroupDto.getId(), userGroupDto)).withRel("update"));
            userGroupDto.add(linkTo(methodOn(UserGroupController.class).deleteUserGroup(userGroupDto.getId())).withRel("delete"));

            // User links
            userGroupDto.add(linkTo(methodOn(UserController.class).findByUserGroup(userGroupDto.getId(), Pageable.unpaged())).withRel("users"));
            userGroupDto.add(linkTo(methodOn(UserGroupController.class).addUser(null, null)).withRel("addUser").expand(userGroupDto.getId(), ""));
            userGroupDto.add(linkTo(methodOn(UserGroupController.class).removeUser(null, null)).withRel("removeUser").expand(userGroupDto.getId(), ""));
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }

        return userGroupDto;
    }

    @Override
    public @NotNull CollectionModel<UserGroupDto> toCollectionModel(@NotNull Iterable<? extends UserGroup> entities) {
        CollectionModel<UserGroupDto> userGroupDtos = super.toCollectionModel(entities);
        userGroupDtos.add(linkTo(methodOn(UserGroupController.class).findAll(Pageable.unpaged())).withSelfRel());
        return userGroupDtos;
    }

    public @NotNull UserGroup toEntity(UserGroupDto userGroupDto) {
        return modelConverter.convertUserGroupToEntity(userGroupDto);
    }
}
