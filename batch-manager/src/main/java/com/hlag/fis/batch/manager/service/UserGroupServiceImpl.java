package com.hlag.fis.batch.manager.service;

import com.hlag.fis.batch.domain.UserGroup;
import com.hlag.fis.batch.manager.service.common.ResourceNotFoundException;
import com.hlag.fis.batch.repository.UserGroupRepository;
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

    private UserGroupRepository userGroupRepository;

    private CacheManager cacheManager;

    /**
     * Constructor
     *
     * @param userGroupRepository user repository.
     * @param cacheManager        cache manager.
     */
    @Autowired
    public UserGroupServiceImpl(UserGroupRepository userGroupRepository, CacheManager cacheManager) {
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
}
