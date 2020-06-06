package com.momentum.batch.server.manager.service;

import com.momentum.batch.server.database.domain.Agent;
import com.momentum.batch.server.database.domain.AgentGroup;
import com.momentum.batch.server.database.domain.JobSchedule;
import com.momentum.batch.server.database.repository.AgentGroupRepository;
import com.momentum.batch.server.database.repository.AgentRepository;
import com.momentum.batch.server.database.repository.JobScheduleRepository;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Agent service implementation.
 *
 * <p>
 * On startup the agent cache will be filled.
 * </p>
 *
 * @version 0.0.4
 * @since 0.0.3
 */
@Service
public class AgentServiceImpl implements AgentService {

    private final AgentRepository agentRepository;

    private final AgentGroupRepository agentGroupRepository;

    private final JobScheduleRepository jobScheduleRepository;

    private final CacheManager cacheManager;

    /**
     * Constructor.
     *
     * @param agentRepository agent repository.
     * @param cacheManager    cache manager.
     */
    @Autowired
    public AgentServiceImpl(AgentRepository agentRepository, AgentGroupRepository agentGroupRepository,
                            JobScheduleRepository jobScheduleRepository, CacheManager cacheManager) {
        this.agentRepository = agentRepository;
        this.agentGroupRepository = agentGroupRepository;
        this.jobScheduleRepository = jobScheduleRepository;
        this.cacheManager = cacheManager;
    }

    /**
     * Pre-fill cache with all agents.
     */
    @PostConstruct
    public void init() {
        Page<Agent> agents = agentRepository.findAll(Pageable.unpaged());
        agents.forEach(agent ->
                Objects.requireNonNull(cacheManager.getCache("Agent")).put(agent.getId(), agent));
    }

    @Override
    public Page<Agent> findAll(Pageable pageable) {
        return agentRepository.findAll(pageable);
    }

    @Override
    public long countAll() {
        return agentRepository.count();
    }

    @Override
    public long countByAgentGroup(String agentGroupId) {
        return agentRepository.countByAgentGroupId(agentGroupId);
    }

    @Override
    public long countSchedules(String agentId) {
        return agentRepository.countSchedules(agentId);
    }

    @Override
    public Page<JobSchedule> getSchedules(String agentId, Pageable pageable) {
        return jobScheduleRepository.findByAgentId(agentId, pageable);
    }

    @Override
    public List<String> findAllAgentNames() {
        return agentRepository.findAllAgentNames();
    }

    @Override
    @Cacheable(cacheNames = "Agent", key = "#agentId")
    public Optional<Agent> findById(String agentId) {
        return agentRepository.findById(agentId);
    }

    @Override
    @Cacheable(cacheNames = "Agent", key = "#nodeName")
    public Optional<Agent> findByNodeName(String nodeName) {
        return agentRepository.findByNodeName(nodeName);
    }

    @Override
    public Page<Agent> findByAgentGroup(String agentGroupId, Pageable pageable) {
        return agentRepository.findByAgentGroupId(agentGroupId, pageable);
    }

    @Override
    @CachePut(cacheNames = "Agent", key = "#agent.id")
    public Agent updateAgent(Agent agent) throws ResourceNotFoundException {
        Optional<Agent> agentOldOptional = agentRepository.findById(agent.getId());
        if (agentOldOptional.isPresent()) {
            Agent agentNew = agentOldOptional.get();
            agentNew.update(agent);
            return agentRepository.save(agentNew);
        }
        throw new ResourceNotFoundException();
    }

    @Override
    @CacheEvict(cacheNames = "Agent", key = "#agentId")
    public void deleteAgent(String agentId) {
        agentRepository.deleteById(agentId);
    }

    /**
     * Adds a agent group to an agent.
     *
     * @param agentId      agent ID.
     * @param agentGroupId agent group name to add.
     */
    @Override
    @CachePut(cacheNames = "Agent", key = "#agentId")
    public Agent addAgentGroup(String agentId, String agentGroupId) throws ResourceNotFoundException {
        Optional<Agent> agentOptional = agentRepository.findById(agentId);
        Optional<AgentGroup> agentGroupOptional = agentGroupRepository.findById(agentGroupId);
        if (agentOptional.isPresent() && agentGroupOptional.isPresent()) {
            Agent agent = agentOptional.get();
            AgentGroup agentGroup = agentGroupOptional.get();
            agent.addAgentGroup(agentGroup);
            return agentRepository.save(agent);
        }
        throw new ResourceNotFoundException();
    }

    /**
     * Removes a agent group from an agent.
     *
     * @param agentId      agent ID.
     * @param agentGroupId agent group ID to remove.
     */
    @Override
    @CachePut(cacheNames = "Agent", key = "#agentId")
    public Agent removeAgentGroup(String agentId, String agentGroupId) throws ResourceNotFoundException {
        Optional<Agent> agentOptional = agentRepository.findById(agentId);
        Optional<AgentGroup> agentGroupOptional = agentGroupRepository.findById(agentGroupId);
        if (agentOptional.isPresent() && agentGroupOptional.isPresent()) {
            Agent agent = agentOptional.get();
            AgentGroup agentGroup = agentGroupOptional.get();
            agent.removeAgentGroup(agentGroup);
            return agentRepository.save(agent);
        }
        throw new ResourceNotFoundException();
    }


    /**
     * Adds a schedule to an agent.
     *
     * @param agentId       agent ID.
     * @param jobScheduleId job schedule ID.
     * @return updated agent.
     */
    @Override
    @CachePut(cacheNames = "Agent", key = "#agentId")
    public Agent addSchedule(String agentId, String jobScheduleId) throws ResourceNotFoundException {
        Optional<Agent> agentOptional = agentRepository.findById(agentId);
        Optional<JobSchedule> jobScheduleOptional = jobScheduleRepository.findById(jobScheduleId);
        if (agentOptional.isPresent() && jobScheduleOptional.isPresent()) {
            Agent agent = agentOptional.get();
            JobSchedule jobSchedule = jobScheduleOptional.get();
            jobSchedule.addAgent(agent);
            jobScheduleRepository.save(jobSchedule);
            return agent;
        }
        throw new ResourceNotFoundException();
    }

    /**
     * Removes a job schedule from an agent.
     *
     * @param agentId       agent ID.
     * @param jobScheduleId job schedule ID to remove.
     * @return updated agent.
     */
    @Override
    @CachePut(cacheNames = "Agent", key = "#agentId")
    public Agent removeSchedule(String agentId, String jobScheduleId) throws ResourceNotFoundException {
        Optional<Agent> agentOptional = findById(agentId);
        Optional<JobSchedule> jobScheduleOptional = jobScheduleRepository.findById(jobScheduleId);
        if (agentOptional.isPresent() && jobScheduleOptional.isPresent()) {
            Agent agent = agentOptional.get();
            JobSchedule jobSchedule = jobScheduleOptional.get();
            jobSchedule.removeAgent(agent);
            jobScheduleRepository.save(jobSchedule);
            return agent;
        }
        throw new ResourceNotFoundException();
    }
}
