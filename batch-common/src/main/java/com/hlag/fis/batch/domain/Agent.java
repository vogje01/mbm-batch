package com.hlag.fis.batch.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class description.
 *
 * @author Jens Vogt jensvogt47@gmail.com
 * @version 0.0.2
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
     * Active
     */
    @Column(name = "ACTIVE")
    private Boolean active;
    /**
     * Schedules
     */
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "agents")
    private List<JobSchedule> schedules = new ArrayList<>();

    public Agent() {
        // Default constructor
    }

    public void update(Agent agent) {
        this.nodeName = agent.nodeName;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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
        Agent agent = (Agent) o;
        return Objects.equal(id, agent.id) &&
                Objects.equal(nodeName, agent.nodeName) &&
                Objects.equal(hostName, agent.hostName) &&
                Objects.equal(pid, agent.pid) &&
                Objects.equal(lastStart, agent.lastStart) &&
                Objects.equal(lastPing, agent.lastPing) &&
                Objects.equal(active, agent.active) &&
                Objects.equal(schedules, agent.schedules);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, nodeName, hostName, pid, lastStart, lastPing, active, schedules);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("nodeName", nodeName)
                .add("hostName", hostName)
                .add("pid", pid)
                .add("lastStart", lastStart)
                .add("lastPing", lastPing)
                .add("active", active)
                .add("schedules", schedules)
                .toString();
    }

}
