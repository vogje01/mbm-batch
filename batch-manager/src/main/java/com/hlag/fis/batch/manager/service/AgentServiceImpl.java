package com.hlag.fis.batch.manager.service;

import com.hlag.fis.batch.domain.Agent;
import com.hlag.fis.batch.domain.JobSchedule;
import com.hlag.fis.batch.manager.service.common.ResourceNotFoundException;
import com.hlag.fis.batch.repository.AgentRepository;
import com.hlag.fis.batch.repository.JobScheduleRepository;
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

    private AgentRepository agentRepository;

    private JobScheduleRepository jobScheduleRepository;

    private CacheManager cacheManager;

    /**
     * Constructor
     *
     * @param agentRepository agent repository.
     * @param cacheManager    cache manager.
     */
    @Autowired
    public AgentServiceImpl(AgentRepository agentRepository, JobScheduleRepository jobScheduleRepository, CacheManager cacheManager) {
        this.agentRepository = agentRepository;
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
    @CacheEvict(cacheNames = "Agent", key = "#agent.id")
    public void deleteAgent(String agentId) {
        agentRepository.deleteById(agentId);
    }
}
