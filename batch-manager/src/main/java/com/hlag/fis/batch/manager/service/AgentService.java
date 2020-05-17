package com.hlag.fis.batch.manager.service;

import com.hlag.fis.batch.domain.Agent;
import com.hlag.fis.batch.manager.service.common.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AgentService {

    Page<Agent> findAll(Pageable pageable);

    long countAll();

    List<String> findAllAgentNames();

    Optional<Agent> findById(String id);

    Agent updateAgent(Agent agent) throws ResourceNotFoundException;

    void deleteAgent(String agentId);
}
