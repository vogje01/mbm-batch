package com.momentum.batch.server.manager.service;

import com.momentum.batch.server.database.domain.User;
import com.momentum.batch.server.database.domain.UserGroup;
import com.momentum.batch.server.database.repository.UserGroupRepository;
import com.momentum.batch.server.database.repository.UserRepository;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.Optional;

/**
 * User service implementation.
 *
 * <p>
 * On startup the user cache will be filled.
 * </p>
 *
 * @version 0.0.6-SNAPSHOT
 * @since 0.0.3
 */
@Service
public class UserGroupServiceImpl implements UserGroupService {

    private UserRepository userRepository;

    private UserGroupRepository userGroupRepository;

    private CacheManager cacheManager;

    /**
     * Constructor
     *
     * @param userRepository      user repository.
     * @param userGroupRepository user group repository.
     * @param cacheManager        cache manager.
     */
    @Autowired
    public UserGroupServiceImpl(UserRepository userRepository, UserGroupRepository userGroupRepository, CacheManager cacheManager) {
        this.userRepository = userRepository;
        this.userGroupRepository = userGroupRepository;
        this.cacheManager = cacheManager;
    }

    /**
     * Pre-fill cache with all users.
     */
    @PostConstruct
    public void init() {
        Page<UserGroup> userGroups = userGroupRepository.findAll(Pageable.unpaged());
        userGroups.forEach(userGroup ->
                Objects.requireNonNull(cacheManager.getCache("UserGroup")).put(userGroup.getId(), userGroup));
    }

    @Override
    public Page<UserGroup> findAll(Pageable pageable) {
        return userGroupRepository.findAll(pageable);
    }

    @Override
    public Page<UserGroup> findByUser(String id, Pageable pageable) {
        return userGroupRepository.findByUser(id, pageable);
    }

    @Override
    public long countAll() {
        return userGroupRepository.count();
    }

    @Override
    public long countByUser(String id) {
        return userGroupRepository.countByUser(id);
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
