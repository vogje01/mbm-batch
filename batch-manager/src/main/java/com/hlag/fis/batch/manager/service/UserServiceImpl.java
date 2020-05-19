package com.hlag.fis.batch.manager.service;

import com.hlag.fis.batch.domain.User;
import com.hlag.fis.batch.manager.service.common.ResourceNotFoundException;
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
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private CacheManager cacheManager;

    /**
     * Constructor
     *
     * @param userRepository user repository.
     * @param cacheManager   cache manager.
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository, CacheManager cacheManager) {
        this.userRepository = userRepository;
        this.cacheManager = cacheManager;
    }

    /**
     * Pre-fill cache with all users.
     */
    @PostConstruct
    public void init() {
        Page<User> users = userRepository.findAll(Pageable.unpaged());
        users.forEach(user ->
                Objects.requireNonNull(cacheManager.getCache("User")).put(user.getId(), user));
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public long countAll() {
        return userRepository.count();
    }

    @Override
    @Cacheable(cacheNames = "User", key = "#userId")
    public Optional<User> findById(String userId) {
        return userRepository.findById(userId);
    }

    @Override
    @CachePut(cacheNames = "User", key = "#user.id")
    public User updateUser(User user) throws ResourceNotFoundException {
        Optional<User> userOldOptional = userRepository.findById(user.getId());
        if (userOldOptional.isPresent()) {
            User userNew = userOldOptional.get();
            userNew.update(user);
            return userRepository.save(userNew);
        }
        throw new ResourceNotFoundException();
    }

    @Override
    @CacheEvict(cacheNames = "User", key = "#user.id")
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}
