package com.momentum.batch.server.database.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.util.Date;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-SNAPSHOT
 * @since 0.0.1
 */
@MappedSuperclass
public class Auditing {
    /**
     * Version
     */
    @Version
    @Column(name = "VERSION")
    private Long version;
    /**
     * Creation user
     */
    @Column(name = "CREATED_BY")
    @CreatedBy
    private String createdBy = "admin";
    /**
     * Created date
     */
    @Column(name = "CREATED_AT")
    @CreatedDate
    private Date createdAt = new Date();
    /**
     * Last modification user
     */
    @Column(name = "MODIFIED_BY")
    @LastModifiedBy
    private String modifiedBy = "admin";
    /**
     * Last modification date
     */
    @Column(name = "MODIFIED_AT")
    @LastModifiedDate
    private Date modifiedAt = new Date();

    public Long getVersion() {
        return version;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("version", version)
                .add("createdBy", createdBy)
                .add("createdAt", createdAt)
                .add("modifiedBy", modifiedBy)
                .add("modifiedAt", modifiedAt)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Auditing that = (Auditing) o;
        return Objects.equal(version, that.version) &&
                Objects.equal(createdBy, that.createdBy) &&
                Objects.equal(createdAt, that.createdAt) &&
                Objects.equal(modifiedBy, that.modifiedBy) &&
                Objects.equal(modifiedAt, that.modifiedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(version, createdBy, createdAt, modifiedBy, modifiedAt);
    }
}
