package com.momentum.batch.server.database.repository;

import com.momentum.batch.server.database.configuration.H2JpaConfiguration;
import com.momentum.batch.server.database.domain.User;
import com.momentum.batch.server.database.domain.UserBuilder;
import com.momentum.batch.server.database.domain.UserGroup;
import com.momentum.batch.server.database.domain.UserGroupBuilder;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = H2JpaConfiguration.class)
public class UserGroupRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserGroupRepository userGroupRepository;

    @After
    public void cleanUp() {
        userRepository.deleteAll();
        userGroupRepository.deleteAll();
    }

    @Test
    public void whenFindByName_thenReturnUserGroup() {

        // given
        UserGroup userGroup = new UserGroupBuilder()
                .withRandomId()
                .withName("batch-userGroup-01")
                .withActive(true)
                .build();
        userGroupRepository.save(userGroup);

        // when
        Optional<UserGroup> found = userGroupRepository.findByName(userGroup.getName());

        // then
        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getName()).isEqualTo(userGroup.getName());
    }

    @Test
    public void whenFindById_thenReturnUserGroup() {

        // given
        UserGroup userGroup = new UserGroupBuilder()
                .withRandomId()
                .withName("batch-userGroup-01")
                .build();
        userGroupRepository.save(userGroup);

        // when
        Optional<UserGroup> found = userGroupRepository.findById(userGroup.getId());

        // then
        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getName()).isEqualTo(userGroup.getName());
    }

    @Test
    public void whenFindAll_thenReturnUserGroupList() {

        // given
        UserGroup userGroup1 = new UserGroupBuilder()
                .withRandomId()
                .withName("batch-userGroup-01")
                .build();
        UserGroup userGroup2 = new UserGroupBuilder()
                .withRandomId()
                .withName("batch-userGroup-01")
                .build();
        userGroupRepository.save(userGroup1);
        userGroupRepository.save(userGroup2);

        // when
        Page<UserGroup> found = userGroupRepository.findAll(Pageable.unpaged());

        // then
        assertThat(found.isEmpty()).isFalse();
        assertThat(found.getTotalElements()).isEqualTo(2);
    }

    @Test
    @Transactional
    public void whenFindByUserId_thenReturnUserGroup() {

        // given
        User user = new UserBuilder()
                .withRandomId()
                .withUserId("batch-user-01")
                .build();
        UserGroup userGroup = new UserGroupBuilder()
                .withRandomId()
                .withName("batch-userGroup-01")
                .withUser(user)
                .withDescription("Test group")
                .withActive(true)
                .build();
        userGroup = userGroupRepository.save(userGroup);
        user.addUserGroup(userGroup);
        userRepository.save(user);

        // when
        Page<UserGroup> found = userGroupRepository.findByUser(user.getId(), Pageable.unpaged());

        // then
        assertThat(found.isEmpty()).isFalse();
        assertThat(found.getTotalElements()).isEqualTo(1L);
        assertThat(found.getContent().get(0).getUsers().get(0).getId()).isEqualTo(user.getId());
    }
}
