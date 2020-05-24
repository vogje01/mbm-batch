package com.hlag.fis.batch.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Class description.
 *
 * @author Jens Vogt jensvogt47@gmail.com
 * @version 0.0.2
 * @since 0.0.1
 */
@Entity
@Table(name = "BATCH_AGENT_PERFORMANCE")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class AgentPerformance implements PrimaryKeyIdentifier<String> {

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
    private Long version;
    /**
     * Node name
     */
    @Column(name = "NODE_NAME")
    private String nodeName;
    /**
     * Type
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE")
    private AgentPerformanceType type;
    /**
     * Load on whole system, in the range 0.0-1.0
     */
    @Column(name = "SYSTEM_LOAD")
    private Double systemLoad;
    /**
     * Total real memory on machine in bytes
     */
    @Column(name = "TOTAL_REAL_MEMORY")
    private Long totalRealMemory;
    /**
     * Free real memory on machine in bytes
     */
    @Column(name = "FREE_REAL_MEMORY")
    private Long freeRealMemory;
    /**
     * Used real memory on machine in bytes
     */
    @Column(name = "USED_REAL_MEMORY")
    private Long usedRealMemory;
    /**
     * Percentage of real memory free
     */
    @Column(name = "FREE_REAL_MEMORY_PCT")
    private Double freeRealMemoryPct;
    /**
     * Percentage of real memory used
     */
    @Column(name = "USED_REAL_MEMORY_PCT")
    private Double usedRealMemoryPct;
    /**
     * Total virtual memory on machine in bytes
     */
    @Column(name = "TOTAL_VIRT_MEMORY")
    private Long totalVirtMemory;
    /**
     * Free virtual memory on machine in bytes
     */
    @Column(name = "FREE_VIRT_MEMORY")
    private Long freeVirtMemory;
    /**
     * Used virtual memory on machine in bytes
     */
    @Column(name = "USED_VIRT_MEMORY")
    private Long usedVirtMemory;
    /**
     * Percentage of real memory free
     */
    @Column(name = "FREE_VIRT_MEMORY_PCT")
    private Double freeVirtMemoryPct;
    /**
     * Percentage of real memory used
     */
    @Column(name = "USED_VIRT_MEMORY_PCT")
    private Double usedVirtMemoryPct;
    /**
     * Total swap space in bytes
     */
    @Column(name = "TOTAL_SWAP")
    private Long totalSwap;
    /**
     * Free swap space in bytes
     */
    @Column(name = "FREE_SWAP")
    private Long freeSwap;
    /**
     * Free swap space in bytes
     */
    @Column(name = "USED_SWAP")
    private Long usedSwap;
    /**
     * Free swap space in percent
     */
    @Column(name = "FREE_SWAP_PCT")
    private Double freeSwapPct;
    /**
     * Free swap space in percent
     */
    @Column(name = "USED_SWAP_PCT")
    private Double usedSwapPct;
    /**
     * Last performance
     */
    @Column(name = "LAST_UPDATE")
    private Timestamp lastUpdate;

    public AgentPerformance() {
        // Intentionally empty
    }

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

    public AgentPerformanceType getType() {
        return type;
    }

    public void setType(AgentPerformanceType type) {
        this.type = type;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Double getSystemLoad() {
        return systemLoad;
    }

    public void setSystemLoad(Double averageLoad) {
        this.systemLoad = averageLoad;
    }

    public Long getTotalRealMemory() {
        return totalRealMemory;
    }

    public void setTotalRealMemory(Long totalMemory) {
        this.totalRealMemory = totalMemory;
    }

    public Long getFreeRealMemory() {
        return freeRealMemory;
    }

    public void setFreeRealMemory(Long freeMemory) {
        this.freeRealMemory = freeMemory;
    }

    public Long getUsedRealMemory() {
        return usedRealMemory;
    }

    public void setUsedRealMemory(Long committedMemory) {
        this.usedRealMemory = committedMemory;
    }

    public Double getFreeRealMemoryPct() {
        return freeRealMemoryPct;
    }

    public void setFreeRealMemoryPct(Double freeRealMemoryPct) {
        this.freeRealMemoryPct = freeRealMemoryPct;
    }

    public Double getUsedRealMemoryPct() {
        return usedRealMemoryPct;
    }

    public void setUsedRealMemoryPct(Double usedRealMemoryPct) {
        this.usedRealMemoryPct = usedRealMemoryPct;
    }

    public Long getTotalVirtMemory() {
        return totalVirtMemory;
    }

    public void setTotalVirtMemory(Long totalVirtMemory) {
        this.totalVirtMemory = totalVirtMemory;
    }

    public Long getFreeVirtMemory() {
        return freeVirtMemory;
    }

    public void setFreeVirtMemory(Long freeVirtMemory) {
        this.freeVirtMemory = freeVirtMemory;
    }

    public Long getUsedVirtMemory() {
        return usedVirtMemory;
    }

    public void setUsedVirtMemory(Long usedVirtMemory) {
        this.usedVirtMemory = usedVirtMemory;
    }

    public Double getFreeVirtMemoryPct() {
        return freeVirtMemoryPct;
    }

    public void setFreeVirtMemoryPct(Double freeVirtMemoryPct) {
        this.freeVirtMemoryPct = freeVirtMemoryPct;
    }

    public Double getUsedVirtMemoryPct() {
        return usedVirtMemoryPct;
    }

    public void setUsedVirtMemoryPct(Double usedVirtMemoryPct) {
        this.usedVirtMemoryPct = usedVirtMemoryPct;
    }

    public Long getTotalSwap() {
        return totalSwap;
    }

    public void setTotalSwap(Long totalSwap) {
        this.totalSwap = totalSwap;
    }

    public Long getFreeSwap() {
        return freeSwap;
    }

    public void setFreeSwap(Long freeSwap) {
        this.freeSwap = freeSwap;
    }

    public Long getUsedSwap() {
        return usedSwap;
    }

    public void setUsedSwap(Long usedSwap) {
        this.usedSwap = usedSwap;
    }

    public Double getFreeSwapPct() {
        return freeSwapPct;
    }

    public void setFreeSwapPct(Double freeSwapPct) {
        this.freeSwapPct = freeSwapPct;
    }

    public Double getUsedSwapPct() {
        return usedSwapPct;
    }

    public void setUsedSwapPct(Double usedSwapPct) {
        this.usedSwapPct = usedSwapPct;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastPerformance) {
        this.lastUpdate = lastPerformance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AgentPerformance that = (AgentPerformance) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(version, that.version) &&
                Objects.equal(nodeName, that.nodeName) &&
                type == that.type &&
                Objects.equal(systemLoad, that.systemLoad) &&
                Objects.equal(totalRealMemory, that.totalRealMemory) &&
                Objects.equal(freeRealMemory, that.freeRealMemory) &&
                Objects.equal(usedRealMemory, that.usedRealMemory) &&
                Objects.equal(freeRealMemoryPct, that.freeRealMemoryPct) &&
                Objects.equal(usedRealMemoryPct, that.usedRealMemoryPct) &&
                Objects.equal(totalVirtMemory, that.totalVirtMemory) &&
                Objects.equal(freeVirtMemory, that.freeVirtMemory) &&
                Objects.equal(usedVirtMemory, that.usedVirtMemory) &&
                Objects.equal(freeVirtMemoryPct, that.freeVirtMemoryPct) &&
                Objects.equal(usedVirtMemoryPct, that.usedVirtMemoryPct) &&
                Objects.equal(totalSwap, that.totalSwap) &&
                Objects.equal(freeSwap, that.freeSwap) &&
                Objects.equal(usedSwap, that.usedSwap) &&
                Objects.equal(freeSwapPct, that.freeSwapPct) &&
                Objects.equal(usedSwapPct, that.usedSwapPct) &&
                Objects.equal(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, version, nodeName, type, systemLoad, totalRealMemory, freeRealMemory, usedRealMemory, freeRealMemoryPct, usedRealMemoryPct, totalVirtMemory, freeVirtMemory, usedVirtMemory, freeVirtMemoryPct, usedVirtMemoryPct, totalSwap, freeSwap, usedSwap, freeSwapPct, usedSwapPct, lastUpdate);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("version", version)
                .add("nodeName", nodeName)
                .add("type", type)
                .add("systemLoad", systemLoad)
                .add("totalRealMemory", totalRealMemory)
                .add("freeRealMemory", freeRealMemory)
                .add("usedRealMemory", usedRealMemory)
                .add("freeRealMemoryPct", freeRealMemoryPct)
                .add("usedRealMemoryPct", usedRealMemoryPct)
                .add("totalVirtMemory", totalVirtMemory)
                .add("freeVirtMemory", freeVirtMemory)
                .add("usedVirtMemory", usedVirtMemory)
                .add("freeVirtMemoryPct", freeVirtMemoryPct)
                .add("usedVirtMemoryPct", usedVirtMemoryPct)
                .add("totalSwap", totalSwap)
                .add("freeSwap", freeSwap)
                .add("usedSwap", usedSwap)
                .add("freeSwapPct", freeSwapPct)
                .add("usedSwapPct", usedSwapPct)
                .add("lastUpdate", lastUpdate)
                .toString();
    }

}
