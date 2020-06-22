package com.momentum.batch.server.manager.controller;

import com.momentum.batch.common.util.MethodTimer;
import com.momentum.batch.server.database.domain.User;
import com.momentum.batch.server.database.repository.UserRepository;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import static java.text.MessageFormat.format;

/**
 * User  REST controller.
 * <p>
 * Uses HATEOAS for specific links. This allows to change the URL for the different REST methods on the server side.
 * </p>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.3
 */
@RestController
@RequestMapping("/api/avatar")
public class AvatarController {

    private static final Logger logger = LoggerFactory.getLogger(AvatarController.class);

    private final MethodTimer t = new MethodTimer();

    private final UserRepository userRepository;

    /**
     * Constructor.
     *
     * @param userRepository repository implementation.
     */
    @Autowired
    public AvatarController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Return the avatar PNG file.
     *
     * <p>
     * If the avatar does not exist for the user, an empty ok result is returned.
     * </p>
     *
     * @param id ID of user.
     * @return avatar image as PNG file.
     * @throws ResourceNotFoundException when the avatar cannot be found.
     */
    @GetMapping(value = "/{id}", produces = {"image/png"})
    public ResponseEntity<byte[]> avatar(@PathVariable String id) throws ResourceNotFoundException {

        t.restart();

        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {

            User user = userOptional.get();
            if (user.getAvatar() == null) {
                return ResponseEntity.ok().build();
            }
            HttpHeaders headers = new HttpHeaders();
            byte[] media = new byte[0];
            try {
                media = IOUtils.toByteArray(userOptional.get().getAvatar().getBinaryStream());
            } catch (IOException | SQLException ex) {
                logger.error(format("Could not read avatar - error: {0}", ex.getMessage()));
            }
            headers.setCacheControl(CacheControl.noCache().getHeaderValue());

            return new ResponseEntity<>(media, headers, HttpStatus.OK);
        }
        throw new ResourceNotFoundException();
    }
}
