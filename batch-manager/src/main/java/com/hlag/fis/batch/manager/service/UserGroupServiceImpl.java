package com.hlag.fis.batch.manager.service;

import com.hlag.fis.batch.domain.User;
import com.hlag.fis.batch.domain.UserGroup;
import com.hlag.fis.batch.manager.service.common.ResourceNotFoundException;
import com.hlag.fis.batch.repository.UserGroupRepository;
import com.hlag.fis.batch.repository.UserRepository;
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
 * @version 0.0.4
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

    @Override
    @CacheEvict(cacheNames = "User", key = "#id")
    public void deleteUserGroup(String id) {
        userGroupRepository.deleteById(id);
    }

    /**
     * Adds a user to an user group.
     *
     * @param id     user group ID.
     * @param userId user id to add.
     */
    @Override
    @CachePut(cacheNames = "UserGroup", key = "#id")
    public UserGroup addUser(String id, String userId) {
        Optional<User> userOptional = userRepository.findByUserId(userId);
        Optional<UserGroup> userGroupOptional = userGroupRepository.findById(id);
        if (userOptional.isPresent() && userGroupOptional.isPresent()) {
            User user = userOptional.get();
            UserGroup userGroup = userGroupOptional.get();
            user.addUserGroup(userGroup);
            userRepository.save(user);
            return userGroup;
        }
        return null;
    }

    /**
     * Removes a user from an user group.
     *
     * @param id     user group ID.
     * @param userId user ID to remove.
     */
    @Override
    @CachePut(cacheNames = "UserGroup", key = "#id")
    public UserGroup removeUser(String id, String userId) {
        Optional<User> userOptional = userRepository.findByUserId(userId);
        Optional<UserGroup> userGroupOptional = userGroupRepository.findById(id);
        if (userOptional.isPresent() && userGroupOptional.isPresent()) {
            User user = userOptional.get();
            UserGroup userGroup = userGroupOptional.get();
            user.removeUserGroup(userGroup);
            userRepository.save(user);
            return userGroup;
        }
        return null;
    }
}
