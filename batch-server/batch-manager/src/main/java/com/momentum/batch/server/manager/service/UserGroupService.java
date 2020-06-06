package com.momentum.batch.server.manager.service;

import com.momentum.batch.server.database.domain.UserGroup;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserGroupService {

    Page<UserGroup> findAll(Pageable pageable);

    Page<UserGroup> findByUser(String id, Pageable pageable);

    long countAll();

    long countByUser(String id);

    Optional<UserGroup> findById(String id);

    Optional<UserGroup> findByName(String name);

    UserGroup insertUserGroup(UserGroup userGroup);

    UserGroup updateUserGroup(UserGroup userGroup) throws ResourceNotFoundException;

    void deleteUserGroup(String userGroup);

    UserGroup addUser(String userGroupId, String id) throws ResourceNotFoundException;

    UserGroup removeUser(String userGroupId, String id) throws ResourceNotFoundException;
}
