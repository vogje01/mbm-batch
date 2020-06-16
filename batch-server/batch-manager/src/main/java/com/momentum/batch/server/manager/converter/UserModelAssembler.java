package com.momentum.batch.server.manager.converter;

import com.momentum.batch.common.domain.dto.UserDto;
import com.momentum.batch.server.database.converter.ModelConverter;
import com.momentum.batch.server.database.domain.User;
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
 * @version 0.0.5-RELEASE
 * @since 0.0.4
 */
@Component
public class UserModelAssembler extends RepresentationModelAssemblerSupport<User, UserDto> {

    private final ModelConverter modelConverter;

    @Autowired
    public UserModelAssembler(ModelConverter modelConverter) {
        super(UserController.class, UserDto.class);
        this.modelConverter = modelConverter;
    }

    @Override
    public @NotNull UserDto toModel(@NotNull User entity) {

        UserDto userDto = modelConverter.convertUserToDto(entity);

        try {
            userDto.add(linkTo(methodOn(UserController.class).findById(userDto.getId())).withSelfRel());
            userDto.add(linkTo(methodOn(UserController.class).insertUser(userDto)).withRel("insert"));
            userDto.add(linkTo(methodOn(UserController.class).updateUser(userDto.getId(), userDto)).withRel("update"));
            userDto.add(linkTo(methodOn(UserController.class).deleteUser(userDto.getId())).withRel("delete"));

            // User group links
            userDto.add(linkTo(methodOn(UserGroupController.class).findByUser(userDto.getId(), Pageable.unpaged())).withRel("userGroups"));
            userDto.add(linkTo(methodOn(UserController.class).addUserGroup(null, null)).withRel("addUserGroup").expand(userDto.getId(), ""));
            userDto.add(linkTo(methodOn(UserController.class).removeUserGroup(null, null)).withRel("removeUserGroup").expand(userDto.getId(), ""));

            // Avatar links
            userDto.add(linkTo(methodOn(UserController.class).avatar(userDto.getId())).withRel("avatar"));
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }

        return userDto;
    }

    @Override
    public @NotNull CollectionModel<UserDto> toCollectionModel(@NotNull Iterable<? extends User> entities) {
        CollectionModel<UserDto> UserDtos = super.toCollectionModel(entities);
        UserDtos.add(linkTo(methodOn(UserController.class).findAll(Pageable.unpaged())).withSelfRel());
        return UserDtos;
    }

    public @NotNull User toEntity(UserDto userDto) {
        return modelConverter.convertUserToEntity(userDto);
    }
}
