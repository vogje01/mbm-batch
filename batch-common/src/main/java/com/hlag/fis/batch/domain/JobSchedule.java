package com.hlag.fis.batch.domain;

import com.fasterxml.jackson.annotation.*;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "BATCH_JOB_SCHEDULE")
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
                Objects.equal(active, that.active) &&
                Objects.equal(jobDefinition, that.jobDefinition) &&
                Objects.equal(agents, that.agents);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), id, schedule, lastExecution, nextExecution, name, active, jobDefinition, agents);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("schedule", schedule)
                .add("lastExecution", lastExecution)
                .add("nextExecution", nextExecution)
                .add("name", name)
                .add("active", active)
                .toString();
    }
}
