package com.hlag.fis.db.db2.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.hlag.fis.db.attribute.userrole.UserRoleClassifier;
import com.hlag.fis.db.converter.LegacyBooleanConverter;
import com.hlag.fis.db.converter.LegacyDateConverter;
import com.hlag.fis.db.converter.LegacyStringConverter;
import com.hlag.fis.db.mysql.model.PrimaryKeyIdentifier;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "TZ1820")
public class UserRoleOld implements PrimaryKeyIdentifier {

    @EmbeddedId
    private UserRoleIdOld id;

    @Column(name = "NAME")
    @Convert(converter = LegacyStringConverter.class)
    private String name;

    @Column(name = "SHORT_DESCRIPTION")
    @Convert(converter = LegacyStringConverter.class)
    private String shortDescription;

    @Column(name = "CLASSIFIER")
    @Convert(converter = UserRoleClassifier.Converter.class)
    private UserRoleClassifier classifier;

    @Column(name = "DE_THD_IDENTIFIER")
    private Integer deThdIdentifier;

    @Column(name = "RELEASED_ON")
    @Convert(converter = LegacyDateConverter.class)
    private LocalDate releasedOn;

    @Column(name = "HIGH_SECURITY_FLAG")
    @Convert(converter = LegacyBooleanConverter.class)
    private Boolean highSecurityFlag;

    @Column(name = "LAST_CHANGE")
    private Long lastChange;

    @Column(name = "CHANGE_LOC")
    @Convert(converter = LegacyStringConverter.class)
    private String changeLocation;

    @Column(name = "CHANGED_BY")
    @Convert(converter = LegacyStringConverter.class)
    private String changedBy;

    public UserRoleIdOld getId() {
        return id;
    }

    public void setId(UserRoleIdOld id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public UserRoleClassifier getClassifier() {
        return classifier;
    }

    public void setClassifier(UserRoleClassifier classifier) {
        this.classifier = classifier;
    }

    public Integer getDeThdIdentifier() {
        return deThdIdentifier;
    }

    public void setDeThdIdentifier(Integer deThdIdentifier) {
        this.deThdIdentifier = deThdIdentifier;
    }

    public LocalDate getReleasedOn() {
        return releasedOn;
    }

    public void setReleasedOn(LocalDate releasedOn) {
        this.releasedOn = releasedOn;
    }

    public Boolean getHighSecurityFlag() {
        return highSecurityFlag;
    }

    public void setHighSecurityFlag(Boolean highSecurityFlag) {
        this.highSecurityFlag = highSecurityFlag;
    }

    public Long getLastChange() {
        return lastChange;
    }

    public void setLastChange(Long lastChange) {
        this.lastChange = lastChange;
    }

    public String getChangeLocation() {
        return changeLocation;
    }

    public void setChangeLocation(String changeLocation) {
        this.changeLocation = changeLocation;
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
            .add("name", name)
            .add("shortDescription", shortDescription)
            .add("classifier", classifier)
            .add("deThdIdentifier", deThdIdentifier)
            .add("releasedOn", releasedOn)
            .add("highSecurityFlag", highSecurityFlag)
            .add("lastChange", lastChange)
            .add("changeLocation", changeLocation)
            .add("changedBy", changedBy)
            .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserRoleOld that = (UserRoleOld) o;
        return Objects.equal(id, that.id) && Objects.equal(name, that.name) && Objects.equal(shortDescription, that.shortDescription) && classifier == that.classifier
            && Objects.equal(deThdIdentifier, that.deThdIdentifier) && Objects.equal(releasedOn, that.releasedOn) && Objects.equal(
            highSecurityFlag, that.highSecurityFlag) && Objects.equal(lastChange, that.lastChange) && Objects.equal(changeLocation, that.changeLocation) && Objects.equal(
            changedBy, that.changedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, shortDescription, classifier, deThdIdentifier, releasedOn, highSecurityFlag, lastChange, changeLocation, changedBy);
    }
}
