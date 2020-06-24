package com.momentum.batch.server.database.domain.dto;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
public class JobStatisticDto {

    private long totalJobs;

    private long abandonedJobs;

    private long completedJobs;

    private long failedJobs;

    private long startedJobs;

    private long startingJobs;

    private long stoppedJobs;

    private long stoppingJobs;

    private long unknownJobs;

    public long getTotalJobs() {
        return totalJobs;
    }

    public void setTotalJobs(long totalJobs) {
        this.totalJobs = totalJobs;
    }

    public long getAbandonedJobs() {
        return abandonedJobs;
    }

    public void setAbandonedJobs(long abandonedJobs) {
        this.abandonedJobs = abandonedJobs;
    }

    public long getCompletedJobs() {
        return completedJobs;
    }

    public void setCompletedJobs(long completedJobs) {
        this.completedJobs = completedJobs;
    }

    public long getFailedJobs() {
        return failedJobs;
    }

    public void setFailedJobs(long failedJobs) {
        this.failedJobs = failedJobs;
    }

    public long getStartedJobs() {
        return startedJobs;
    }

    public void setStartedJobs(long startedJobs) {
        this.startedJobs = startedJobs;
    }

    public long getStartingJobs() {
        return startingJobs;
    }

    public void setStartingJobs(long startingJobs) {
        this.startingJobs = startingJobs;
    }

    public long getStoppedJobs() {
        return stoppedJobs;
    }

    public void setStoppedJobs(long stoppedJobs) {
        this.stoppedJobs = stoppedJobs;
    }

    public long getStoppingJobs() {
        return stoppingJobs;
    }

    public void setStoppingJobs(long stoppingJobs) {
        this.stoppingJobs = stoppingJobs;
    }

    public long getUnknownJobs() {
        return unknownJobs;
    }

    public void setUnknownJobs(long unknownJobs) {
        this.unknownJobs = unknownJobs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobStatisticDto that = (JobStatisticDto) o;
        return totalJobs == that.totalJobs &&
                completedJobs == that.completedJobs &&
                failedJobs == that.failedJobs;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(totalJobs, completedJobs, failedJobs);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("totalJobs", totalJobs)
                .add("completedJobs", completedJobs)
                .add("failedJobs", failedJobs)
                .toString();
    }
}
