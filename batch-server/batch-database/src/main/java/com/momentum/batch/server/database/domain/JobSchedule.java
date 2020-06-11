package com.momentum.batch.server.database.domain;

import com.fasterxml.jackson.annotation.*;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.momentum.batch.common.domain.PrimaryKeyIdentifier;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "BATCH_JOB_SCHEDULE")
@EntityListeners(AuditingEntityListener.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true, value = {"handler", "hibernateLazyInitializer"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class JobSchedule extends Auditing implements PrimaryKeyIdentifier<String> {

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @Column(name = "SCHEDULE")
    private String schedule;

    @Column(name = "LAST_EXECUTION")
    private Date lastExecution;

    @Column(name = "NEXT_EXECUTION")
    private Date nextExecution;

    @Column(name = "NAME")
    private String name;

    @Column(name = "MODE")
    @Enumerated(EnumType.STRING)
    private String mode;

    @Column(name = "ACTIVE")
    private Boolean active;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "JOB_DEFINITION_ID", nullable = false)
    private JobDefinition jobDefinition;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "BATCH_AGENT_SCHEDULE",
            joinColumns = @JoinColumn(name = "SCHEDULE_ID"),
            inverseJoinColumns = @JoinColumn(name = "AGENT_ID"))
    private List<Agent> agents = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "BATCH_AGENT_GROUP_SCHEDULE",
            joinColumns = @JoinColumn(name = "SCHEDULE_ID"),
            inverseJoinColumns = @JoinColumn(name = "AGENT_GROUP_ID"))
    private List<AgentGroup> agentGroups = new ArrayList<>();

    @JsonCreator
    public JobSchedule() {
        // JSON constructor
    }

    /**
     * Updates only the native data, not the relationships.
     *
     * @param origin original to copy from.
     */
    public void update(JobSchedule origin) {
        this.schedule = origin.schedule;
        this.lastExecution = origin.lastExecution;
        this.nextExecution = origin.nextExecution;
        this.name = origin.name;
        this.active = origin.active;
        this.jobDefinition = origin.jobDefinition;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public JobDefinition getJobDefinition() {
        return jobDefinition;
    }

    public void setJobDefinition(JobDefinition jobDefinition) {
        this.jobDefinition = jobDefinition;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String label) {
        this.schedule = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String hostName) {
        this.name = hostName;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Date getNextExecution() {
        return nextExecution;
    }

    public void setNextExecution(Date nextExecution) {
        this.nextExecution = nextExecution;
    }

    public Date getLastExecution() {
        return lastExecution;
    }

    public void setLastExecution(Date lastExecution) {
        this.lastExecution = lastExecution;
    }

    public Boolean isActive() {
        return active;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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
        if (agents.contains(agent)) {
            agents.remove(agent);
        }
    }

    public List<AgentGroup> getAgentGroups() {
        return agentGroups;
    }

    public void setAgentGroups(List<AgentGroup> agentGroups) {
        this.agentGroups = agentGroups;
    }

    /**
     * Adds a agent group to the schedule. All agents in the agent group will be removed from the agent list.
     *
     * @param agentGroup agent group to add.
     */
    public void addAgentGroup(AgentGroup agentGroup) {
        if (!agentGroups.contains(agentGroup)) {
            agentGroups.add(agentGroup);
        }
        agents.removeAll(agentGroup.getAgents());
    }

    public void removeAgentGroup(AgentGroup agentGroup) {
        if (agentGroups.contains(agentGroup)) {
            agentGroups.remove(agentGroup);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        JobSchedule that = (JobSchedule) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(schedule, that.schedule) &&
                Objects.equal(lastExecution, that.lastExecution) &&
                Objects.equal(nextExecution, that.nextExecution) &&
                Objects.equal(name, that.name) &&
                Objects.equal(mode, that.mode) &&
                Objects.equal(active, that.active) &&
                Objects.equal(jobDefinition, that.jobDefinition) &&
                Objects.equal(agents, that.agents) &&
                Objects.equal(agentGroups, that.agentGroups);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), id, schedule, lastExecution, nextExecution, name, mode, active, jobDefinition, agents, agentGroups);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("schedule", schedule)
                .add("lastExecution", lastExecution)
                .add("nextExecution", nextExecution)
                .add("name", name)
                .add("mode", mode)
                .add("active", active)
                .toString();
    }
}
