package com.hlag.fis.batch.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.springframework.batch.item.ExecutionContext;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExecutionContextInfo {

    private Set<Map.Entry<String, Object>> context = new HashSet<>();

    /**
     * Default constructor
     */
    public ExecutionContextInfo() {
        // JSON constructor
    }

    public ExecutionContextInfo(ExecutionContext executionContext) {
        this.context = executionContext.entrySet();
    }

    public Set<Map.Entry<String, Object>> getContext() {
        return context;
    }

    public void setContext(Set<Map.Entry<String, Object>> context) {
        this.context = context;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExecutionContextInfo that = (ExecutionContextInfo) o;
        return Objects.equal(context, that.context);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(context);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("context", context)
                .toString();
    }
}
