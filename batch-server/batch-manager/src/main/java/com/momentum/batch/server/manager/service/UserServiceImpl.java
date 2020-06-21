package com.momentum.batch.server.manager.service;

import com.momentum.batch.common.util.MethodTimer;
import com.momentum.batch.server.database.domain.*;
import com.momentum.batch.server.database.domain.dto.UserDto;
import com.momentum.batch.server.database.repository.PasswordResetTokenRepository;
import com.momentum.batch.server.database.repository.UserGroupRepository;
import com.momentum.batch.server.database.repository.UserRepository;
import com.momentum.batch.server.manager.converter.UserModelAssembler;
import com.momentum.batch.server.manager.service.common.BadRequestException;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.jasypt.encryption.StringEncryptor;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static java.text.MessageFormat.format;

/**
 * User service implementation.
 *
 * @version 0.0.6-SNAPSHOT
 * @since 0.0.3
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final MethodTimer t = new MethodTimer();

    private final UserRepository userRepository;

    private final UserGroupRepository userGroupRepository;

    private final PagedResourcesAssembler<User> userPagedResourcesAssembler;

    private final UserModelAssembler userModelAssembler;

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    private final StringEncryptor stringEncryptor;

    /**
     * Constructor
     *
     * @param userRepository               user repository.
     * @param userGroupRepository          user group repository.
     * @param passwordResetTokenRepository password reset repository.
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserGroupRepository userGroupRepository, PasswordResetTokenRepository passwordResetTokenRepository,
                           StringEncryptor stringEncryptor, PagedResourcesAssembler<User> userPagedResourcesAssembler, UserModelAssembler userModelAssembler) {
        this.userRepository = userRepository;
        this.userGroupRepository = userGroupRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.stringEncryptor = stringEncryptor;
        this.userPagedResourcesAssembler = userPagedResourcesAssembler;
        this.userModelAssembler = userModelAssembler;
    }

    @Override
    public PagedModel<UserDto> findAll(Pageable pageable) {
        t.restart();

        Page<User> users = userRepository.findAll(pageable);
        PagedModel<UserDto> collectionModel = userPagedResourcesAssembler.toModel(users, userModelAssembler);
        logger.debug(format("Agent list request finished - count: {0}/{1} {2}",
                Objects.requireNonNull(collectionModel.getMetadata()).getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

        return collectionModel;
    }

    /**
     * Returns all users which are not member of the current user group.
     *
     * @param userGroupId user group ID.
     * @param pageable    paging parameters.
     * @return page of job definitions belong to the given ob group.
     */
    @Override
    public PagedModel<UserDto> findWithoutUserGroup(String userGroupId, Pageable pageable) {
        t.restart();

        Page<User> users = userRepository.findWithoutUserGroup(userGroupId, pageable);
        PagedModel<UserDto> collectionModel = userPagedResourcesAssembler.toModel(users, userModelAssembler);
        logger.debug(format("Agent list request finished - count: {0}/{1} {2}",
                Objects.requireNonNull(collectionModel.getMetadata()).getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

        return collectionModel;
    }

    @Override
    @Cacheable(cacheNames = "User", key = "#id")
    public UserDto findById(String id) throws ResourceNotFoundException {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userModelAssembler.toModel(userOptional.get());
        }
        throw new ResourceNotFoundException("User not found");
    }

    @Override
    @Cacheable(cacheNames = "User", key = "#userId")
    public UserDto findByUserId(String userId) throws ResourceNotFoundException {
        Optional<User> userOptional = userRepository.findByUserId(userId);
        if (userOptional.isPresent()) {
            return userModelAssembler.toModel(userOptional.get());
        }
        throw new ResourceNotFoundException("User not found");
    }

    @Override
    @Cacheable(cacheNames = "User", key = "#id")
    public PagedModel<UserDto> findByUserGroup(String id, Pageable pageable) {
        t.restart();

        Page<User> users = userRepository.findByUserGroup(id, pageable);
        PagedModel<UserDto> collectionModel = userPagedResourcesAssembler.toModel(users, userModelAssembler);
        logger.debug(format("Agent list request finished - count: {0}/{1} {2}",
                Objects.requireNonNull(collectionModel.getMetadata()).getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

        return collectionModel;
    }

    @Override
    public UserDto insertUser(UserDto userDto) throws BadRequestException {
        t.restart();

        Optional<User> userOptional = userRepository.findByUserId(userDto.getUserId());
        if (userOptional.isPresent()) {
            throw new BadRequestException();
        }

        User user = userModelAssembler.toEntity(userDto);
        if (validateUser(user)) {
            user = userRepository.save(user);
            logger.debug(format("Finished insert user request - userId: {0} {1}", user.getUserId(), t.elapsedStr()));
            return userModelAssembler.toModel(user);
        }
        throw new BadRequestException();
    }

    @Override
    @CachePut(cacheNames = "User", key = "#userDto.id")
    public UserDto updateUser(UserDto userDto) throws ResourceNotFoundException {
        User userNew = userModelAssembler.toEntity(userDto);
        Optional<User> userOldOptional = userRepository.findById(userDto.getId());
        if (userOldOptional.isPresent()) {
            User userOld = userOldOptional.get();
            userOld.update(userNew);
            return userModelAssembler.toModel(userOld);
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
    public UserDto addUserGroup(String id, String userGroupId) throws ResourceNotFoundException {
        Optional<User> userOptional = userRepository.findById(id);
        Optional<UserGroup> userGroupOptional = userGroupRepository.findById(userGroupId);
        if (userOptional.isPresent() && userGroupOptional.isPresent()) {
            User user = userOptional.get();
            UserGroup userGroup = userGroupOptional.get();
            user.addUserGroup(userGroup);
            user = userRepository.save(user);
            return userModelAssembler.toModel(user);
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
    public UserDto removeUserGroup(String id, String userGroupId) throws ResourceNotFoundException {
        Optional<User> userOptional = userRepository.findById(id);
        Optional<UserGroup> userGroupOptional = userGroupRepository.findById(userGroupId);
        if (userOptional.isPresent() && userGroupOptional.isPresent()) {
            User user = userOptional.get();
            UserGroup userGroup = userGroupOptional.get();
            user.removeUserGroup(userGroup);
            user = userRepository.save(user);
            return userModelAssembler.toModel(user);
        }
        throw new ResourceNotFoundException();
    }

    @Override
    public void changePassword(User user, String password) {
        user.setPassword(stringEncryptor.encrypt(password));
        userRepository.save(user);
    }

    @Override
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
            logger.error(format("Could not send email to user - error: {0}", e.getMessage()));
        }
    }

    private boolean validateUser(User user) {

        if (user.getPassword() == null) {
            return false;
        }
        user.setPassword(stringEncryptor.encrypt(user.getPassword()));

        if (user.getDateTimeFormat() == null) {
            user.setDateTimeFormat(DateTimeFormat.DE);
        }
        if (user.getDateTimeFormat() == null) {
            user.setNumberFormat(NumberFormat.DE);
        }
        if (user.getTheme() == null) {
            user.setTheme("material.blue.light.compact");
        }
        return true;
    }
}
