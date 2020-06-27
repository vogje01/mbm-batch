package com.momentum.batch.server.database.domain.dto;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
public class StepStatisticDto {

    private long totalSteps;

    private long abandonedSteps;

    private long completedSteps;

    private long failedSteps;

    private long startedSteps;

    private long startingSteps;

    private long stoppedSteps;

    private long stoppingSteps;

    private long unknownSteps;

    public long getTotalSteps() {
        return totalSteps;
    }

    public void setTotalSteps(long totalSteps) {
        this.totalSteps = totalSteps;
    }

    public long getAbandonedSteps() {
        return abandonedSteps;
    }

    public void setAbandonedSteps(long abandonedSteps) {
        this.abandonedSteps = abandonedSteps;
    }

    public long getCompletedSteps() {
        return completedSteps;
    }

    public void setCompletedSteps(long completedSteps) {
        this.completedSteps = completedSteps;
    }

    public long getFailedSteps() {
        return failedSteps;
    }

    public void setFailedSteps(long failedSteps) {
        this.failedSteps = failedSteps;
    }

    public long getStartedSteps() {
        return startedSteps;
    }

    public void setStartedSteps(long startedSteps) {
        this.startedSteps = startedSteps;
    }

    public long getStartingSteps() {
        return startingSteps;
    }

    public void setStartingSteps(long startingSteps) {
        this.startingSteps = startingSteps;
    }

    public long getStoppedSteps() {
        return stoppedSteps;
    }

    public void setStoppedSteps(long stoppedSteps) {
        this.stoppedSteps = stoppedSteps;
    }

    public long getStoppingSteps() {
        return stoppingSteps;
    }

    public void setStoppingSteps(long stoppingSteps) {
        this.stoppingSteps = stoppingSteps;
    }

    public long getUnknownSteps() {
        return unknownSteps;
    }

    public void setUnknownSteps(long unknownSteps) {
        this.unknownSteps = unknownSteps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StepStatisticDto that = (StepStatisticDto) o;
        return totalSteps == that.totalSteps &&
                abandonedSteps == that.abandonedSteps &&
                completedSteps == that.completedSteps &&
                failedSteps == that.failedSteps &&
                startedSteps == that.startedSteps &&
                startingSteps == that.startingSteps &&
                stoppedSteps == that.stoppedSteps &&
                stoppingSteps == that.stoppingSteps &&
                unknownSteps == that.unknownSteps;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(totalSteps, abandonedSteps, completedSteps, failedSteps, startedSteps, startingSteps, stoppedSteps, stoppingSteps, unknownSteps);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("totalSteps", totalSteps)
                .add("abandonedSteps", abandonedSteps)
                .add("completedSteps", completedSteps)
                .add("failedSteps", failedSteps)
                .add("startedSteps", startedSteps)
                .add("startingSteps", startingSteps)
                .add("stoppedSteps", stoppedSteps)
                .add("stoppingSteps", stoppingSteps)
                .add("unknownSteps", unknownSteps)
                .toString();
    }
}
