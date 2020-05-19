package com.hlag.fis.batch.manager.service;

import com.hlag.fis.batch.domain.User;
import com.hlag.fis.batch.manager.service.common.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {

    Page<User> findAll(Pageable pageable);

    long countAll();

    Optional<User> findById(String id);

    User updateUser(User user) throws ResourceNotFoundException;

    void deleteUser(String userId);
}
