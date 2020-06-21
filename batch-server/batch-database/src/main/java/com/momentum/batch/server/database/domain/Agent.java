package com.momentum.batch.server.database.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * MBM agent entity.
 *
 * @author Jens Vogt jensvogt47@gmail.com
 * @version 0.0.6-SNAPSHOT
 * @since 0.0.1
 */
@Entity
@Table(name = "BATCH_AGENT")
@EntityListeners(AuditingEntityListener.class)
public class Agent extends Auditing implements PrimaryKeyIdentifier<String> {

    /**
     * Primary key
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;
    /**
     * Node name
     */
    @Column(name = "NODE_NAME")
    private String nodeName;
    /**
     * Host name
     */
    @Column(name = "HOST_NAME")
    private String hostName;
    /**
     * PID
     */
    @Column(name = "PID")
    private Long pid;
    /**
     * Agent status
     */
    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private AgentStatus status;
    /**
     * Last start time
     */
    @Column(name = "LAST_START")
    private Date lastStart;
    /**
     * Last ping
     */
    @Column(name = "LAST_PING")
    private Date lastPing;
    /**
     * Current system load
     */
    @Column(name = "SYSTEM_LOAD")
    private Double systemLoad;
    /**
     * Average system load day
     */
    @Column(name = "AVG_SYSTEM_LOAD_DAY")
    private Double avgSystemLoadDay;
    /**
     * Average system load week
     */
    @Column(name = "AVG_SYSTEM_LOAD_WEEK")
    private Double avgSystemLoadWeek;
    /**
     * Active
     */
    @Column(name = "ACTIVE")
    private Boolean active;
    /**
     * Agent groups many to many relationship
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "BATCH_AGENT_AGENT_GROUP",
            joinColumns = @JoinColumn(name = "AGENT_ID"),
            inverseJoinColumns = @JoinColumn(name = "AGENT_GROUP_ID"))
    private final List<AgentGroup> agentGroups = new ArrayList<>();
    /**
     * Schedules
     */
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "agents")
    private List<JobSchedule> schedules = new ArrayList<>();

    public Agent() {
        // Default constructor
    }

    public void update(Agent agent) {
        this.hostName = agent.hostName;
        this.nodeName = agent.nodeName;
        this.status = agent.status;
        this.systemLoad = agent.systemLoad;
        this.active = agent.active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public AgentStatus getStatus() {
        return status;
    }

    public void setStatus(AgentStatus status) {
        this.status = status;
    }

    public Date getLastStart() {
        return lastStart;
    }

    public void setLastStart(Date lastStart) {
        this.lastStart = lastStart;
    }

    public Date getLastPing() {
        return lastPing;
    }

    public void setLastPing(Date lastPing) {
        this.lastPing = lastPing;
    }

    public Double getSystemLoad() {
        return systemLoad;
    }

    public void setSystemLoad(Double systemLoad) {
        this.systemLoad = systemLoad;
    }

    public Double getAvgSystemLoadDay() {
        return avgSystemLoadDay;
    }

    public void setAvgSystemLoadDay(Double avgSystemLoadDay) {
        this.avgSystemLoadDay = avgSystemLoadDay;
    }

    public Double getAvgSystemLoadWeek() {
        return avgSystemLoadWeek;
    }

    public void setAvgSystemLoadWeek(Double avgSystemLoadWeek) {
        this.avgSystemLoadWeek = avgSystemLoadWeek;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<AgentGroup> getAgentGroups() {
        return agentGroups;
    }

    public void setAgentGroups(List<AgentGroup> agentGroups) {
        this.agentGroups.clear();
        if (agentGroups != null) {
            agentGroups.forEach(this::addAgentGroup);
        }
    }

    public void addAgentGroup(AgentGroup agentGroup) {
        if (!agentGroups.contains(agentGroup)) {
            agentGroups.add(agentGroup);
        }
    }

    public void removeAgentGroup(AgentGroup agentGroup) {
        if (agentGroups.contains(agentGroup)) {
            agentGroups.remove(agentGroup);
        }
    }

    public List<JobSchedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<JobSchedule> schedules) {
        this.schedules = schedules;
    }

    public void addSchedule(JobSchedule schedule) {
        if (!schedules.contains(schedule)) {
            schedules.add(schedule);
        }
    }

    public void removeSchedule(JobSchedule schedule) {
        if (schedules.contains(schedule)) {
            schedules.remove(schedule);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Agent agent = (Agent) o;
        return Objects.equal(id, agent.id) &&
                Objects.equal(nodeName, agent.nodeName) &&
                Objects.equal(hostName, agent.hostName) &&
                Objects.equal(pid, agent.pid) &&
                Objects.equal(status, agent.status) &&
                Objects.equal(lastStart, agent.lastStart) &&
                Objects.equal(lastPing, agent.lastPing) &&
                Objects.equal(systemLoad, agent.systemLoad) &&
                Objects.equal(avgSystemLoadDay, agent.avgSystemLoadDay) &&
                Objects.equal(avgSystemLoadWeek, agent.avgSystemLoadWeek) &&
                Objects.equal(active, agent.active);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), id, nodeName, hostName, pid, status, lastStart, lastPing, systemLoad, avgSystemLoadDay, avgSystemLoadWeek, active);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("nodeName", nodeName)
                .add("hostName", hostName)
                .add("pid", pid)
                .add("status", status)
                .add("lastStart", lastStart)
                .add("lastPing", lastPing)
                .add("systemLoad", systemLoad)
                .add("avgSystemLoadDay", avgSystemLoadDay)
                .add("avgSystemLoadWeek", avgSystemLoadWeek)
                .add("active", active)
                .toString();
    }

}
