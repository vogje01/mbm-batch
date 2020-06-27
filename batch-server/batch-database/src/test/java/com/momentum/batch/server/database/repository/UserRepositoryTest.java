package com.momentum.batch.server.database.repository;

import com.momentum.batch.server.database.configuration.H2JpaConfiguration;
import com.momentum.batch.server.database.domain.User;
import com.momentum.batch.server.database.domain.UserBuilder;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = H2JpaConfiguration.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @After
    public void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    public void whenFindByName_thenReturnUser() {

        // given
        User user = new UserBuilder()
                .withRandomId()
                .withUserId("batch-user-01")
                .withActive(true)
                .build();
        userRepository.save(user);

        // when
        Optional<User> found = userRepository.findByUserId(user.getUserId());

        // then
        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getUserId()).isEqualTo(user.getUserId());
    }

    @Test
    public void whenFindById_thenReturnUser() {

        // given
        User user = new UserBuilder()
                .withRandomId()
                .withUserId("batch-user-01")
                .build();
        userRepository.save(user);

        // when
        Optional<User> found = userRepository.findById(user.getId());

        // then
        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getUserId()).isEqualTo(user.getUserId());
    }

    @Test
    public void whenFindAll_thenReturnUserList() {

        // given
        User user1 = new UserBuilder()
                .withRandomId()
                .withUserId("batch-user-01")
                .build();
        User user2 = new UserBuilder()
                .withRandomId()
                .withUserId("batch-user-01")
                .build();
        userRepository.save(user1);
        userRepository.save(user2);

        // when
        Page<User> found = userRepository.findAll(Pageable.unpaged());

        // then
        assertThat(found.isEmpty()).isFalse();
        assertThat(found.getTotalElements()).isEqualTo(2);
    }
}
