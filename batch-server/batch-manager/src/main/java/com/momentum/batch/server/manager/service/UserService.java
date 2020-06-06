package com.momentum.batch.server.manager.service;

import com.momentum.batch.server.database.domain.User;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {

    Page<User> findAll(Pageable pageable);

    long countAll();

    long countByUserGroup(String id);

    Optional<User> findById(String id);

    Optional<User> findByUserId(String userId);

    Optional<User> findByEmail(String email);

    Page<User> findByUserGroup(String id, Pageable pageable);

    User insertUser(User user);

    User updateUser(User user) throws ResourceNotFoundException;

    void deleteUser(String userId);

    User addUserGroup(String id, String userGroupId) throws ResourceNotFoundException;

    User removeUserGroup(String id, String userGroupId) throws ResourceNotFoundException;

    void resetPassword(User user) throws ResourceNotFoundException;

    void changePassword(User user, String password);
}
