package com.hlag.fis.batch.manager.service;

import com.hlag.fis.batch.domain.UserGroup;
import com.hlag.fis.batch.manager.service.common.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserGroupService {

    Page<UserGroup> findAll(Pageable pageable);

    long countAll();

    Optional<UserGroup> findById(String id);

    Optional<UserGroup> findByName(String name);

    UserGroup insertUserGroup(UserGroup userGroup);

    UserGroup updateUserGroup(UserGroup userGroup) throws ResourceNotFoundException;

    void deleteUserGroup(String userGroup);
}
