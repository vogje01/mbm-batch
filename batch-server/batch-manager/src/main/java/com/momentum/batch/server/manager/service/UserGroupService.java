package com.momentum.batch.server.manager.service;

import com.momentum.batch.common.domain.dto.UserGroupDto;
import com.momentum.batch.server.database.domain.UserGroup;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.Optional;

public interface UserGroupService {

    PagedModel<UserGroupDto> findAll(Pageable pageable);

    PagedModel<UserGroupDto> findByUser(String id, Pageable pageable);

    Optional<UserGroup> findById(String id);

    Optional<UserGroup> findByName(String name);

    UserGroup insertUserGroup(UserGroup userGroup);

    UserGroup updateUserGroup(UserGroup userGroup) throws ResourceNotFoundException;

    void deleteUserGroup(String userGroup);

    UserGroup addUser(String userGroupId, String id) throws ResourceNotFoundException;

    UserGroup removeUser(String userGroupId, String id) throws ResourceNotFoundException;
}
