package com.momentum.batch.server.database.repository;

import com.momentum.batch.server.database.configuration.H2JpaConfiguration;
import com.momentum.batch.server.database.domain.Agent;
import com.momentum.batch.server.database.domain.AgentBuilder;
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
public class AgentRepositoryTest {

    @Autowired
    private AgentRepository agentRepository;

    @After
    public void cleanUp() {
        agentRepository.deleteAll();
    }

    @Test
    public void whenFindByName_thenReturnAgent() {

        // given
        Agent agent = new AgentBuilder()
                .withRandomId()
                .withNodeName("batch-agent-01")
                .build();
        agentRepository.save(agent);

        // when
        Optional<Agent> found = agentRepository.findByNodeName(agent.getNodeName());

        // then
        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getNodeName()).isEqualTo(agent.getNodeName());
    }

    @Test
    public void whenFindById_thenReturnAgent() {

        // given
        Agent agent = new AgentBuilder()
                .withRandomId()
                .withNodeName("batch-agent-01")
                .build();
        agentRepository.save(agent);

        // when
        Optional<Agent> found = agentRepository.findById(agent.getId());

        // then
        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getNodeName()).isEqualTo(agent.getNodeName());
    }

    @Test
    public void whenFindAll_thenReturnAgentList() {

        // given
        Agent agent1 = new AgentBuilder()
                .withRandomId()
                .withNodeName("batch-agent-01")
                .build();
        Agent agent2 = new AgentBuilder()
                .withRandomId()
                .withNodeName("batch-agent-01")
                .build();
        agentRepository.save(agent1);
        agentRepository.save(agent2);

        // when
        Page<Agent> found = agentRepository.findAll(Pageable.unpaged());

        // then
        assertThat(found.isEmpty()).isFalse();
        assertThat(found.getTotalElements()).isEqualTo(2);
    }
}
