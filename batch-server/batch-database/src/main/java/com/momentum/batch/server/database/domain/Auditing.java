package com.momentum.batch.server.database.domain;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;


/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditing<U> {
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
    private U createdBy;
    /**
     * Created date
     */
    @Column(name = "CREATED_AT")
    @Temporal(TIMESTAMP)
    @CreatedDate
    private Date createdAt;
    /**
     * Last modification user
     */
    @Column(name = "MODIFIED_BY")
    @LastModifiedBy
    private U modifiedBy;
    /**
     * Last modification date
     */
    @Column(name = "MODIFIED_AT")
    @Temporal(TIMESTAMP)
    @LastModifiedDate
    private Date modifiedAt;

    public U getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(U createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public U getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(U modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
