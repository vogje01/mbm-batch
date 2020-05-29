package com.hlag.fis.batch.manager.service;

import com.hlag.fis.batch.domain.PasswordResetToken;
import com.hlag.fis.batch.domain.User;
import com.hlag.fis.batch.domain.UserGroup;
import com.hlag.fis.batch.manager.service.common.ResourceNotFoundException;
import com.hlag.fis.batch.repository.PasswordResetTokenRepository;
import com.hlag.fis.batch.repository.UserGroupRepository;
import com.hlag.fis.batch.repository.UserRepository;
import com.hlag.fis.batch.util.PasswordHash;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
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
import java.util.UUID;

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

    private UserGroupRepository userGroupRepository;

    private PasswordResetTokenRepository passwordResetTokenRepository;

    private CacheManager cacheManager;

    /**
     * Constructor
     *
     * @param userRepository user repository.
     * @param cacheManager   cache manager.
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserGroupRepository userGroupRepository, PasswordResetTokenRepository passwordResetTokenRepository, CacheManager cacheManager) {
        this.userRepository = userRepository;
        this.userGroupRepository = userGroupRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
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
    public long countByUserGroup(String id) {
        return userRepository.countByUserGroup(id);
    }

    @Override
    @Cacheable(cacheNames = "User", key = "#id")
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    @Override
    @Cacheable(cacheNames = "User", key = "#userId")
    public Optional<User> findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Cacheable(cacheNames = "User", key = "#id")
    public Page<User> findByUserGroup(String id, Pageable pageable) {
        return userRepository.findByUserGroup(id, pageable);
    }

    @Override
    public User insertUser(User user) {
        return userRepository.save(user);
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

    /**
     * Adds a user group to an user.
     *
     * @param id   user ID.
     * @param name user group name to add.
     */
    @Override
    @CachePut(cacheNames = "User", key = "#id")
    public User addUserGroup(String id, String name) {
        Optional<User> userOptional = userRepository.findById(id);
        Optional<UserGroup> userGroupOptional = userGroupRepository.findByName(name);
        if (userOptional.isPresent() && userGroupOptional.isPresent()) {
            User user = userOptional.get();
            UserGroup userGroup = userGroupOptional.get();
            user.addUserGroup(userGroup);
            return userRepository.save(user);
        }
        return null;
    }

    /**
     * Removes a user group from an user.
     *
     * @param id          user ID.
     * @param userGroupId user group ID to remove.
     */
    @Override
    @CachePut(cacheNames = "User", key = "#id")
    public User removeUserGroup(String id, String userGroupId) {
        Optional<User> userOptional = findById(id);
        Optional<UserGroup> userGroupOptional = userGroupRepository.findById(userGroupId);
        if (userOptional.isPresent() && userGroupOptional.isPresent()) {
            User user = userOptional.get();
            UserGroup userGroup = userGroupOptional.get();
            user.removeUserGroup(userGroup);
            return userRepository.save(user);
        }
        return null;
    }

    public void changePassword(User user, String password) {
        user.setPassword(PasswordHash.encryptPassword(password));
        userRepository.save(user);
    }

    public void resetPassword(User user) throws ResourceNotFoundException {
        if (user.getEmail() != null) {
            PasswordResetToken token = new PasswordResetToken();
            token.setToken(UUID.randomUUID().toString());
            token.setUser(user);
            token.setExpiryDate(30);
            passwordResetTokenRepository.save(token);
            sendResetPasswordEmail(user, token);
            return;
        }
        throw new ResourceNotFoundException();
    }

    private void sendResetPasswordEmail(User user, PasswordResetToken token) {
        Email email = new SimpleEmail();
        email.setHostName("smtp.googlemail.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator("jensvogt47@gmail.com", "Dilbert_01"));
        email.setSSLOnConnect(true);
        try {
            email.setFrom("no-reply@momentum-ch");
            email.setSubject("MBM Password Reset");
            email.setMsg("Follow the link to reset the password.\n\nhttp://localhost:3000/#/change-password/" + token.getToken());
            email.addTo(user.getEmail());
            email.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }
}
