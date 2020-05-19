package com.hlag.fis.batch.domain.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.springframework.hateoas.RepresentationModel;

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
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class AgentDto extends RepresentationModel<AgentDto> {

    /**
     * Primary key
     */
    private String id;
    /**
     * Node name
     */
    private String nodeName;
    /**
     * Host name
     */
    private String hostName;
    /**
     * PID
     */
    private Long pid;
    /**
     * Last start time
	 */
	private Date lastStart;
	/**
	 * Last ping
     */
    private Date lastPing;
    /**
     * Active
     */
    private Boolean active;
    /**
     * Total count
     */
    private Long totalSize;
    /**
     * Schedule list
     */
    private List<JobScheduleDto> schedules = new ArrayList<>();

    /**
     * Constructor
     */
    public AgentDto() {
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

    public Long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Long totalSize) {
        this.totalSize = totalSize;
    }

    public List<JobScheduleDto> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<JobScheduleDto> schedules) {
        this.schedules = schedules;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AgentDto agentDto = (AgentDto) o;
        return Objects.equal(id, agentDto.id) &&
                Objects.equal(nodeName, agentDto.nodeName) &&
                Objects.equal(hostName, agentDto.hostName) &&
                Objects.equal(pid, agentDto.pid) &&
                Objects.equal(lastStart, agentDto.lastStart) &&
                Objects.equal(lastPing, agentDto.lastPing) &&
                Objects.equal(active, agentDto.active) &&
                Objects.equal(totalSize, agentDto.totalSize) &&
                Objects.equal(schedules, agentDto.schedules);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), id, nodeName, hostName, pid, lastStart, lastPing, active, totalSize, schedules);
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
                .add("totalSize", totalSize)
                .add("schedules", schedules)
                .toString();
    }
}
