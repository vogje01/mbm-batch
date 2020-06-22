package com.momentum.batch.server.manager.service;

import com.momentum.batch.common.util.MethodTimer;
import com.momentum.batch.server.database.domain.User;
import com.momentum.batch.server.database.domain.UserGroup;
import com.momentum.batch.server.database.domain.dto.UserGroupDto;
import com.momentum.batch.server.database.repository.UserGroupRepository;
import com.momentum.batch.server.database.repository.UserRepository;
import com.momentum.batch.server.manager.converter.UserGroupModelAssembler;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static java.text.MessageFormat.format;

/**
 * User service implementation.
 *
 * <p>
 * On startup the user cache will be filled.
 * </p>
 *
 * @version 0.0.6-RELEASE
 * @since 0.0.3
 */
@Service
public class UserGroupServiceImpl implements UserGroupService {

    private static final Logger logger = LoggerFactory.getLogger(UserGroupServiceImpl.class);

    private final MethodTimer t = new MethodTimer();

    private final UserRepository userRepository;

    private final UserGroupRepository userGroupRepository;

    private final PagedResourcesAssembler<UserGroup> userGroupPagedResourcesAssembler;

    private final UserGroupModelAssembler userGroupModelAssembler;

    /**
     * Constructor
     *
     * @param userRepository                   user repository.
     * @param userGroupRepository              user group repository.
     * @param userGroupPagedResourcesAssembler paging resource assembler.
     * @param userGroupModelAssembler          user group assembler.
     */
    @Autowired
    public UserGroupServiceImpl(UserRepository userRepository, UserGroupRepository userGroupRepository,
                                PagedResourcesAssembler<UserGroup> userGroupPagedResourcesAssembler, UserGroupModelAssembler userGroupModelAssembler) {
        this.userRepository = userRepository;
        this.userGroupRepository = userGroupRepository;
        this.userGroupPagedResourcesAssembler = userGroupPagedResourcesAssembler;
        this.userGroupModelAssembler = userGroupModelAssembler;
    }

    @Override
    public PagedModel<UserGroupDto> findAll(Pageable pageable) {
        t.restart();

        Page<UserGroup> userGroups = userGroupRepository.findAll(pageable);
        PagedModel<UserGroupDto> collectionModel = userGroupPagedResourcesAssembler.toModel(userGroups, userGroupModelAssembler);
        logger.debug(format("User group list request finished - count: {0}/{1} {2}",
                Objects.requireNonNull(collectionModel.getMetadata()).getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

        return collectionModel;
    }

    @Override
    public PagedModel<UserGroupDto> findByUser(String id, Pageable pageable) {
        t.restart();

        Page<UserGroup> userGroups = userGroupRepository.findByUser(id, pageable);
        PagedModel<UserGroupDto> collectionModel = userGroupPagedResourcesAssembler.toModel(userGroups, userGroupModelAssembler);
        logger.debug(format("User group list request finished - count: {0}/{1} {2}",
                Objects.requireNonNull(collectionModel.getMetadata()).getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

        return collectionModel;
    }

    @Override
    @Cacheable(cacheNames = "UserGroup", key = "#id")
    public Optional<UserGroup> findById(String id) {
        return userGroupRepository.findById(id);
    }

    @Override
    @Cacheable(cacheNames = "UserGroup", key = "#name")
    public Optional<UserGroup> findByName(String name) {
        return userGroupRepository.findByName(name);
    }

    @Override
    public UserGroup insertUserGroup(UserGroup userGroup) {
        return userGroupRepository.save(userGroup);
    }

    @Override
    @CachePut(cacheNames = "UserGroup", key = "#userGroup.id")
    public UserGroup updateUserGroup(UserGroup userGroup) throws ResourceNotFoundException {
        Optional<UserGroup> userGroupOldOptional = userGroupRepository.findById(userGroup.getId());
        if (userGroupOldOptional.isPresent()) {
            UserGroup userGroupNew = userGroupOldOptional.get();
            userGroupNew.update(userGroup);
            return userGroupRepository.save(userGroupNew);
        }
        throw new ResourceNotFoundException();
    }

    /**
     * Deletes a user group by ID.
     * <p>
     * The user groups 'admins' and 'users' cannot be deleted.
     *
     * @param id user group ID.
     */
    @Override
    @CacheEvict(cacheNames = "UserGroup", key = "#id")
    public void deleteUserGroup(String id) {
        Optional<UserGroup> userGroupOptional = userGroupRepository.findById(id);
        if (userGroupOptional.isPresent() && !userGroupOptional.get().getName().equals("admins") && !userGroupOptional.get().getName().equals("users")) {
            userGroupRepository.deleteById(id);
        }
    }

    /**
     * Adds a user to an user group.
     *
     * @param userGroupId user group ID.
     * @param id          user id to add.
     */
    @Override
    @CachePut(cacheNames = "UserGroup", key = "#userGroupId")
    public UserGroup addUser(String userGroupId, String id) throws ResourceNotFoundException {
        Optional<User> userOptional = userRepository.findById(id);
        Optional<UserGroup> userGroupOptional = userGroupRepository.findById(userGroupId);
        if (userOptional.isPresent() && userGroupOptional.isPresent()) {
            User user = userOptional.get();
            UserGroup userGroup = userGroupOptional.get();
            user.addUserGroup(userGroup);
            userRepository.save(user);
            return userGroup;
        }
        throw new ResourceNotFoundException();
    }

    /**
     * Removes a user from an user group.
     *
     * @param userGroupId user group ID.
     * @param id          user ID to remove.
     */
    @Override
    @CachePut(cacheNames = "UserGroup", key = "#userGroupId")
    public UserGroup removeUser(String userGroupId, String id) throws ResourceNotFoundException {
        Optional<User> userOptional = userRepository.findById(id);
        Optional<UserGroup> userGroupOptional = userGroupRepository.findById(userGroupId);
        if (userOptional.isPresent() && userGroupOptional.isPresent()) {
            User user = userOptional.get();
            UserGroup userGroup = userGroupOptional.get();
            user.removeUserGroup(userGroup);
            userRepository.save(user);
            return userGroup;
        }
        throw new ResourceNotFoundException();
    }
}
