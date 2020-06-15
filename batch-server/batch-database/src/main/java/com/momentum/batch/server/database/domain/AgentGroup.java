package com.momentum.batch.server.database.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "BATCH_AGENT_GROUP")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true, value = {"handler", "hibernateLazyInitializer"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class AgentGroup extends Auditing implements PrimaryKeyIdentifier<String> {

    /**
     * Primary key
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;
    /**
     * Group name
     */
    @Column(name = "NAME")
    private String name;
    /**
     * Group description
     */
    @Column(name = "DESCRIPTION")
    private String description;
    /**
     * Deleted flag
     */
    @Column(name = "ACTIVE")
    private boolean active = false;
    /**
     * Link to the corresponding agents.
     */
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "agentGroups")
    private List<Agent> agents = new ArrayList<>();

    /**
     * Constructor
     */
    public AgentGroup() {
        // JSON constructor
    }

    public void update(AgentGroup agentGroup) {
        this.name = agentGroup.name;
        this.description = agentGroup.description;
        this.active = agentGroup.active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String hostName) {
        this.name = hostName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean deleted) {
        this.active = deleted;
    }

    public List<Agent> getAgents() {
        return agents;
    }

    public void setAgents(List<Agent> agents) {
        this.agents = agents;
    }

    public void addAgent(Agent agent) {
        if (!agents.contains(agent)) {
            agents.add(agent);
        }
    }

    public void removeAgent(Agent agent) {
        agents.remove(agent);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("description", description)
                .add("active", active)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AgentGroup agentGroup = (AgentGroup) o;
        return active == agentGroup.active &&
                Objects.equal(id, agentGroup.id) &&
                Objects.equal(name, agentGroup.name) &&
                Objects.equal(description, agentGroup.description);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, description, active);
    }
}
