package com.momentum.batch.server.manager.service;

import com.momentum.batch.server.database.domain.PasswordResetToken;
import com.momentum.batch.server.database.domain.User;
import com.momentum.batch.server.database.domain.UserGroup;
import com.momentum.batch.server.database.repository.PasswordResetTokenRepository;
import com.momentum.batch.server.database.repository.UserGroupRepository;
import com.momentum.batch.server.database.repository.UserRepository;
import com.momentum.batch.server.database.util.util.PasswordHash;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
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

    private final UserRepository userRepository;

    private final UserGroupRepository userGroupRepository;

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    private final CacheManager cacheManager;

    /**
     * Constructor
     *
     * @param userRepository               user repository.
     * @param userGroupRepository          user group repository.
     * @param passwordResetTokenRepository password reset repository.
     * @param cacheManager                 cache manager.
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

    /**
     * Returns all users which are not member of the current user group.
     *
     * @param userGroupId user group ID.
     * @param pageable    paging parameters.
     * @return page of job definitions belong to the given ob group.
     */
    @Override
    public Page<User> findWithoutUserGroup(String userGroupId, Pageable pageable) {
        return userRepository.findWithoutUserGroup(userGroupId, pageable);
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

    /**
     * Deletes a user by ID.
     * <p>
     * The user 'admin' cannot be deleted.
     *
     * @param id user ID.
     */
    @Override
    @CacheEvict(cacheNames = "User", key = "#id")
    public void deleteUser(String id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent() && !userOptional.get().getUserId().equals("admin")) {
            userRepository.deleteById(id);
        }
    }

    /**
     * Adds a user group to an user.
     *
     * @param id          user ID.
     * @param userGroupId user group ID.
     */
    @Override
    @CachePut(cacheNames = "User", key = "#id")
    public User addUserGroup(String id, String userGroupId) throws ResourceNotFoundException {
        Optional<User> userOptional = userRepository.findById(id);
        Optional<UserGroup> userGroupOptional = userGroupRepository.findById(userGroupId);
        if (userOptional.isPresent() && userGroupOptional.isPresent()) {
            User user = userOptional.get();
            UserGroup userGroup = userGroupOptional.get();
            user.addUserGroup(userGroup);
            return userRepository.save(user);
        }
        throw new ResourceNotFoundException();
    }

    /**
     * Removes a user group from an user.
     *
     * @param id          user ID.
     * @param userGroupId user group ID to remove.
     */
    @Override
    @CachePut(cacheNames = "User", key = "#id")
    public User removeUserGroup(String id, String userGroupId) throws ResourceNotFoundException {
        Optional<User> userOptional = userRepository.findById(id);
        Optional<UserGroup> userGroupOptional = userGroupRepository.findById(userGroupId);
        if (userOptional.isPresent() && userGroupOptional.isPresent()) {
            User user = userOptional.get();
            UserGroup userGroup = userGroupOptional.get();
            user.removeUserGroup(userGroup);
            return userRepository.save(user);
        }
        throw new ResourceNotFoundException();
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
