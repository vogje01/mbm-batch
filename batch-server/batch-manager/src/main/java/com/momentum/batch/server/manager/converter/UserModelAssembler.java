package com.momentum.batch.server.manager.converter;

import com.momentum.batch.common.domain.DateTimeFormat;
import com.momentum.batch.common.domain.NumberFormat;
import com.momentum.batch.common.domain.dto.UserDto;
import com.momentum.batch.server.database.domain.User;
import com.momentum.batch.server.manager.controller.UserController;
import com.momentum.batch.server.manager.controller.UserGroupController;
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
 * @version 0.0.4
 * @since 0.0.4
 */
@Component
public class UserModelAssembler extends RepresentationModelAssemblerSupport<User, UserDto> {

    public UserModelAssembler() {
        super(UserController.class, UserDto.class);
    }

    @Override
    public @NotNull UserDto toModel(@NotNull User entity) {
        UserDto userDto = instantiateModel(entity);

        userDto.setId(entity.getId());
        userDto.setUserId(entity.getUserId());
        userDto.setFirstName(entity.getFirstName());
        userDto.setLastName(entity.getLastName());
        userDto.setEmail(entity.getEmail());
        userDto.setPhone(entity.getPhone());
        userDto.setDescription(entity.getDescription());
        userDto.setDateTimeFormat(entity.getDateTimeFormat().name());
        userDto.setNumberFormat(entity.getNumberFormat().name());
        userDto.setActive(entity.getActive());

        userDto.setCreatedAt(entity.getCreatedAt());
        userDto.setCreatedBy(entity.getCreatedBy());
        userDto.setModifiedAt(entity.getModifiedAt());
        userDto.setModifiedBy(entity.getModifiedBy());

        try {
            userDto.add(linkTo(methodOn(UserController.class).findById(userDto.getId())).withSelfRel());
            userDto.add(linkTo(methodOn(UserController.class).insertUser(userDto)).withRel("insert"));
            userDto.add(linkTo(methodOn(UserController.class).updateUser(userDto.getId(), userDto)).withRel("update"));
            userDto.add(linkTo(methodOn(UserController.class).deleteUser(userDto.getId())).withRel("delete"));
            userDto.add(linkTo(methodOn(UserController.class).addUserGroup(null, null)).withRel("addUserGroup").expand(userDto.getId(), ""));
            userDto.add(linkTo(methodOn(UserController.class).removeUserGroup(null, null)).withRel("removeUserGroup").expand(userDto.getId(), ""));
            userDto.add(linkTo(methodOn(UserController.class).avatar(userDto.getId())).withRel("avatar"));
            userDto.add(linkTo(methodOn(UserGroupController.class).findByUser(userDto.getId(), Pageable.unpaged())).withRel("userGroups"));
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
        User entity = new User();
        entity.setId(userDto.getId());
        entity.setUserId(userDto.getUserId());
        entity.setFirstName(userDto.getFirstName());
        entity.setLastName(userDto.getLastName());
        entity.setEmail(userDto.getEmail());
        entity.setPhone(userDto.getPhone());
        entity.setDescription(userDto.getDescription());
        entity.setDateTimeFormat(DateTimeFormat.valueOf(userDto.getDateTimeFormat()));
        entity.setNumberFormat(NumberFormat.valueOf(userDto.getNumberFormat()));
        entity.setActive(userDto.getActive());
        return entity;
    }
}
