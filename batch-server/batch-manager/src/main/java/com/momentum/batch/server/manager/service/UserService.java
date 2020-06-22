package com.momentum.batch.server.manager.service;

import com.momentum.batch.server.database.domain.User;
import com.momentum.batch.server.database.domain.dto.UserDto;
import com.momentum.batch.server.manager.service.common.BadRequestException;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

public interface UserService {

    PagedModel<UserDto> findAll(Pageable pageable);

    PagedModel<UserDto> findWithoutUserGroup(String userGroupId, Pageable pageable);

    UserDto findById(String id) throws ResourceNotFoundException;

    UserDto findByUserId(String userId) throws ResourceNotFoundException;

    PagedModel<UserDto> findByUserGroup(String id, Pageable pageable);

    UserDto insertUser(UserDto userDto) throws BadRequestException, ResourceNotFoundException;

    UserDto updateUser(UserDto userDto) throws ResourceNotFoundException;

    void deleteUser(String userId);

    UserDto addUserGroup(String id, String userGroupId) throws ResourceNotFoundException;

    UserDto removeUserGroup(String id, String userGroupId) throws ResourceNotFoundException;

    void resetPassword(User user) throws ResourceNotFoundException;

    void changePassword(User user, String password);
}
