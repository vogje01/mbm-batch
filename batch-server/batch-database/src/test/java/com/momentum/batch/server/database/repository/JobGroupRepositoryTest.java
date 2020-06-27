package com.momentum.batch.server.database.repository;

import com.momentum.batch.server.database.configuration.H2JpaConfiguration;
import com.momentum.batch.server.database.domain.JobDefinition;
import com.momentum.batch.server.database.domain.JobDefinitionBuilder;
import com.momentum.batch.server.database.domain.JobGroup;
import com.momentum.batch.server.database.domain.JobGroupBuilder;
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
public class JobGroupRepositoryTest {

    @Autowired
    private JobGroupRepository jobGroupRepository;

    @Autowired
    private JobDefinitionRepository jobDefinitionRepository;

    @After
    public void cleanUp() {
        jobDefinitionRepository.deleteAll();
        jobGroupRepository.deleteAll();
    }

    @Test
    public void whenFindByName_thenReturnJobGroup() {

        // given
        JobGroup jobGroup = new JobGroupBuilder()
                .withRandomId()
                .withName("batch-jobGroup-01")
                .withActive(true)
                .build();
        jobGroupRepository.save(jobGroup);

        // when
        Optional<JobGroup> found = jobGroupRepository.findByName(jobGroup.getName());

        // then
        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getName()).isEqualTo(jobGroup.getName());
    }

    @Test
    public void whenFindById_thenReturnJobGroup() {

        // given
        JobGroup jobGroup = new JobGroupBuilder()
                .withRandomId()
                .withName("batch-jobGroup-01")
                .build();
        jobGroupRepository.save(jobGroup);

        // when
        Optional<JobGroup> found = jobGroupRepository.findById(jobGroup.getId());

        // then
        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getName()).isEqualTo(jobGroup.getName());
    }

    @Test
    public void whenFindAll_thenReturnJobGroupList() {

        // given
        JobGroup jobGroup1 = new JobGroupBuilder()
                .withRandomId()
                .withName("batch-jobGroup-01")
                .build();
        JobGroup jobGroup2 = new JobGroupBuilder()
                .withRandomId()
                .withName("batch-jobGroup-01")
                .build();
        jobGroupRepository.save(jobGroup1);
        jobGroupRepository.save(jobGroup2);

        // when
        Page<JobGroup> found = jobGroupRepository.findAll(Pageable.unpaged());

        // then
        assertThat(found.isEmpty()).isFalse();
        assertThat(found.getTotalElements()).isEqualTo(2);
    }

    @Test
    @Transactional
    public void whenFindByJobId_thenReturnJobGroup() {

        // given
        JobDefinition jobDefinition = new JobDefinitionBuilder()
                .withRandomId()
                .withName("batch-job-01")
                .build();
        JobGroup jobGroup = new JobGroupBuilder()
                .withRandomId()
                .withName("batch-jobGroup-01")
                .withJobDefinition(jobDefinition)
                .withDescription("Test group")
                .withActive(true)
                .build();
        jobGroup = jobGroupRepository.save(jobGroup);
        jobDefinition.addJobGroup(jobGroup);
        jobDefinitionRepository.save(jobDefinition);

        // when
        Page<JobGroup> found = jobGroupRepository.findByJobDefinition(jobDefinition.getId(), Pageable.unpaged());

        // then
        assertThat(found.isEmpty()).isFalse();
        assertThat(found.getTotalElements()).isEqualTo(1L);
        assertThat(found.getContent().get(0).getJobDefinitions().get(0).getId()).isEqualTo(jobDefinition.getId());
    }
}
