package com.momentum.batch.server.database.domain;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.util.Date;

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
    @LastModifiedDate
    private Date modifiedAt;
}
