package com.hlag.fis.batch.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.hlag.fis.db.mysql.model.PrimaryKeyIdentifier;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true, value = {"handler", "hibernateLazyInitializer"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Agent implements PrimaryKeyIdentifier<String>, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Primary key
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;
    /**
	 * Version
	 */
	@Version
	@Column(name = "VERSION")
	private Long version;
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
	}

	public void update(Agent agent) {
		this.nodeName = agent.nodeName;
		this.active = agent.active;
	}

	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
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
                Objects.equal(version, agent.version) &&
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
        return Objects.hashCode(id, version, nodeName, hostName, pid, lastStart, lastPing, active, schedules);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("version", version)
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
