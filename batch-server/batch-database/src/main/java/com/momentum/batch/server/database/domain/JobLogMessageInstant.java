package com.momentum.batch.server.database.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class JobLogMessageInstant {

    private long epochSecond;

    private int nanoOfSecond;

    public JobLogMessageInstant() {
        // JSON constructor
    }

    public long getEpochSecond() {
        return epochSecond;
    }

    public void setEpochSecond(long epochSecond) {
        this.epochSecond = epochSecond;
    }

    public int getNanoOfSecond() {
        return nanoOfSecond;
    }

    public void setNanoOfSecond(int nanoOfSecond) {
        this.nanoOfSecond = nanoOfSecond;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobLogMessageInstant that = (JobLogMessageInstant) o;
        return Objects.equal(epochSecond, that.epochSecond) &&
                Objects.equal(nanoOfSecond, that.nanoOfSecond);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(epochSecond, nanoOfSecond);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("epochSecond", epochSecond)
                .add("nanoOfSecond", nanoOfSecond)
                .toString();
    }
}
