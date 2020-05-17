package com.hlag.fis.db.mysql.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.hlag.fis.db.attribute.common.LcValidStateA;
import com.hlag.fis.db.db2.model.TrustworthExclusionOld;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Trust worth exclusion entity.
 *
 * @author Jens.Vogt (jens.vogt@ext.hlag.com)
 * @version 0.0.3
 * @since 0.0.1
 */
@Entity
@Table(name = "TRUSTWORTH_EXCLUSION")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class TrustworthExclusion implements PrimaryKeyIdentifier<String> {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @Column(name = "TRUSTWORTH_CLASS")
    private String trustWorthClass;

    @Column(name = "REMARK")
    private String remark;

    @Enumerated(EnumType.STRING)
    @Column(name = "LC_VALID_STATE_A")
    private LcValidStateA lcValidStateA;

    @Column(name = "LAST_CHANGE")
    private Long lastChange;

    @Column(name = "CHANGED_BY")
    private String changedBy;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FUNCTIONAL_UNIT_ID", referencedColumnName = "ID")
    private FunctionalUnit functionalUnit;

    public TrustworthExclusion() {
        // JPA constructor
    }

    public void update(TrustworthExclusionOld trustWorthExclusionOld) {
        this.trustWorthClass = trustWorthExclusionOld.getId().getTrustWorthClass();
        this.remark = trustWorthExclusionOld.getRemark();
        this.lcValidStateA = trustWorthExclusionOld.getLcValidStateA();
        this.lastChange = trustWorthExclusionOld.getLastChange();
        this.changedBy = trustWorthExclusionOld.getChangedBy();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LcValidStateA getLcValidStateA() {
        return lcValidStateA;
    }

    public void setLcValidStateA(LcValidStateA lcValidStateA) {
        this.lcValidStateA = lcValidStateA;
    }

    public String getTrustWorthClass() {
        return trustWorthClass;
    }

    public void setTrustWorthClass(String trustWorhClass) {
        this.trustWorthClass = trustWorhClass;
    }

    public FunctionalUnit getFunctionalUnit() {
        return functionalUnit;
    }

    public void setFunctionalUnit(FunctionalUnit functionalUnit) {
        this.functionalUnit = functionalUnit;
    }

    public Long getLastChange() {
        return lastChange;
    }

    public void setLastChange(Long lastChange) {
        this.lastChange = lastChange;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
          .add("id", id)
          .add("trustWorhClass", trustWorthClass)
          .add("remark", remark)
          .add("lcValidStateA", lcValidStateA)
          .add("lastChange", lastChange)
          .add("changedBy", changedBy)
          .add("functionalUnit", functionalUnit)
          .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        TrustworthExclusion that = (TrustworthExclusion) o;
        return Objects.equal(id, that.id) && Objects.equal(trustWorthClass, that.trustWorthClass) && Objects.equal(remark, that.remark)
          && lcValidStateA == that.lcValidStateA && Objects.equal(lastChange, that.lastChange) && Objects.equal(changedBy, that.changedBy) && Objects.equal(functionalUnit,
          that.functionalUnit);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, trustWorthClass, remark, lcValidStateA, lastChange, changedBy, functionalUnit);
    }
}
