package com.momentum.batch.server.manager.service;

import com.momentum.batch.common.util.MethodTimer;
import com.momentum.batch.server.database.domain.User;
import com.momentum.batch.server.database.domain.dto.UserDto;
import com.momentum.batch.server.database.repository.UserRepository;
import com.momentum.batch.server.manager.converter.UserModelAssembler;
import com.momentum.batch.server.manager.service.common.BadRequestException;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.apache.commons.io.IOUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import static java.text.MessageFormat.format;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
@Service
@Transactional
public class AvatarServiceImpl implements AvatarService {

    private static final Logger logger = LoggerFactory.getLogger(AvatarServiceImpl.class);

    private final MethodTimer t = new MethodTimer();

    private final UserRepository userRepository;

    private final UserModelAssembler userModelAssembler;

    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public AvatarServiceImpl(UserRepository userRepository, UserModelAssembler userModelAssembler, EntityManagerFactory entityManagerFactory) {
        this.userRepository = userRepository;
        this.userModelAssembler = userModelAssembler;
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public byte[] download(String id) throws ResourceNotFoundException {

        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            try {
                if (user.getAvatar() == null) {
                    return IOUtils.resourceToByteArray("/images/default_avatar.png");
                }
                return IOUtils.toByteArray(user.getAvatar().getBinaryStream());
            } catch (IOException | SQLException ex) {
                logger.error(format("Could not load avatar image - userId: {0}", user.getUserId()));
                throw new BadRequestException();
            }
        }
        throw new ResourceNotFoundException();
    }

    @Override
    public UserDto upload(String id, MultipartFile file) throws ResourceNotFoundException {
        logger.debug(format("Storing avatar - id: {0}", id));
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            try {
                User user = userOptional.get();
                byte[] bytes = file.getBytes();

                Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
                user.setAvatar(session.getLobHelper().createBlob(bytes));

                return userModelAssembler.toModel(user);

            } catch (Exception e) {
                throw new BadRequestException();
            }
        } else {
            throw new ResourceNotFoundException();
        }
    }
}
