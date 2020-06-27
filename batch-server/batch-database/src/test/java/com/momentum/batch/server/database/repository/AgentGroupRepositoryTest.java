package com.momentum.batch.server.database.repository;

import com.momentum.batch.server.database.configuration.H2JpaConfiguration;
import com.momentum.batch.server.database.domain.Agent;
import com.momentum.batch.server.database.domain.AgentBuilder;
import com.momentum.batch.server.database.domain.AgentGroup;
import com.momentum.batch.server.database.domain.AgentGroupBuilder;
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
public class AgentGroupRepositoryTest {

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private AgentGroupRepository agentGroupRepository;

    @After
    public void cleanUp() {
        agentRepository.deleteAll();
        agentGroupRepository.deleteAll();
    }

    @Test
    public void whenFindByName_thenReturnAgentGroup() {

        // given
        AgentGroup agentGroup = new AgentGroupBuilder()
                .withRandomId()
                .withName("batch-agentGroup-01")
                .withActive(true)
                .build();
        agentGroupRepository.save(agentGroup);

        // when
        Optional<AgentGroup> found = agentGroupRepository.findByName(agentGroup.getName());

        // then
        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getName()).isEqualTo(agentGroup.getName());
    }

    @Test
    public void whenFindById_thenReturnAgentGroup() {

        // given
        AgentGroup agentGroup = new AgentGroupBuilder()
                .withRandomId()
                .withName("batch-agentGroup-01")
                .build();
        agentGroupRepository.save(agentGroup);

        // when
        Optional<AgentGroup> found = agentGroupRepository.findById(agentGroup.getId());

        // then
        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getName()).isEqualTo(agentGroup.getName());
    }

    @Test
    public void whenFindAll_thenReturnAgentGroupList() {

        // given
        AgentGroup agentGroup1 = new AgentGroupBuilder()
                .withRandomId()
                .withName("batch-agentGroup-01")
                .build();
        AgentGroup agentGroup2 = new AgentGroupBuilder()
                .withRandomId()
                .withName("batch-agentGroup-01")
                .build();
        agentGroupRepository.save(agentGroup1);
        agentGroupRepository.save(agentGroup2);

        // when
        Page<AgentGroup> found = agentGroupRepository.findAll(Pageable.unpaged());

        // then
        assertThat(found.isEmpty()).isFalse();
        assertThat(found.getTotalElements()).isEqualTo(2);
    }

    @Test
    @Transactional
    public void whenFindByAgentId_thenReturnAgentGroup() {

        // given
        Agent agent = new AgentBuilder()
                .withRandomId()
                .withNodeName("batch-agent-01")
                .build();
        AgentGroup agentGroup = new AgentGroupBuilder()
                .withRandomId()
                .withName("batch-agentGroup-01")
                .withAgent(agent)
                .withDescription("Test group")
                .build();
        agentGroup = agentGroupRepository.save(agentGroup);
        agent.addAgentGroup(agentGroup);
        agentRepository.save(agent);

        // when
        Page<AgentGroup> found = agentGroupRepository.findByAgentId(agent.getId(), Pageable.unpaged());

        // then
        assertThat(found.isEmpty()).isFalse();
        assertThat(found.getTotalElements()).isEqualTo(1L);
        assertThat(found.getContent().get(0).getAgents().get(0).getId()).isEqualTo(agent.getId());
    }
}
