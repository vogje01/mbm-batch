package com.hlag.fis.db.mysql.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.hlag.fis.db.attribute.userrole.UserRoleClassifier;
import com.hlag.fis.db.db2.model.UserRoleOld;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USER_ROLE")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class UserRole implements PrimaryKeyIdentifier<String> {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @Column(name = "ENVIRONMENT")
    private String environment;

    @Column(name = "IDENTIFIER")
    private Short identifier;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SHORT_DESCRIPTION")
    private String shortDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "CLASSIFIER")
    private UserRoleClassifier classifier;

    @Column(name = "DE_THD_IDENTIFIER")
    private Integer deThdIdentifier;

    @Column(name = "RELEASED_ON")
    private LocalDate releasedOn;

    @Column(name = "HIGH_SECURITY_FLAG")
    private Boolean highSecurityFlag;

    @Column(name = "LAST_CHANGE")
    private Long lastChange;

    @Column(name = "CHANGE_LOCATION")
    private String changeLocation;

    @Column(name = "CHANGED_BY")
    private String changedBy;

    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinTable(name = "ROLE_FUNCTIONAL_UNIT", joinColumns = @JoinColumn(name = "ROLE_ID"), inverseJoinColumns = @JoinColumn(name = "FUNCTIONAL_UNIT_ID"))
    private List<FunctionalUnit> functionalUnits = new ArrayList<>();

    public UserRole() {
        // JPA constructor
    }

    public void update(UserRoleOld userRoleOld) {
        this.environment = userRoleOld.getId().getEnvironment();
        this.identifier = userRoleOld.getId().getIdentifier();
        this.name = userRoleOld.getName();
        this.shortDescription = userRoleOld.getShortDescription();
        this.classifier = userRoleOld.getClassifier();
        this.deThdIdentifier = userRoleOld.getDeThdIdentifier();
        this.releasedOn = userRoleOld.getReleasedOn();
        this.highSecurityFlag = userRoleOld.getHighSecurityFlag();
        this.lastChange = userRoleOld.getLastChange();
        this.changedBy = userRoleOld.getChangedBy();
        this.changeLocation = userRoleOld.getChangeLocation();
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public Short getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Short identifier) {
        this.identifier = identifier;
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

    public Integer getDeThdIdentifier() {
        return deThdIdentifier;
    }

    public void setDeThdIdentifier(Integer deThdIdentifier) {
        this.deThdIdentifier = deThdIdentifier;
    }

    public List<FunctionalUnit> getFunctionalUnits() {
        return functionalUnits;
    }

    public void setFunctionalUnits(List<FunctionalUnit> functionalUnits) {
        functionalUnits.forEach(this::addFunctionalUnit);
    }

    public void addFunctionalUnit(FunctionalUnit functionalUnit) {
        if (!functionalUnits.contains(functionalUnit)) {
            functionalUnits.add(functionalUnit);
            //functionalUnit.addUserRole(this);
        }
    }

    public void removeFunctionalUnit(FunctionalUnit functionalUnit) {
        if (functionalUnits.contains(functionalUnit)) {
            functionalUnits.remove(functionalUnit);
            functionalUnit.removeUserRole(this);
        }
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

    public String getChangeLocation() {
        return changeLocation;
    }

    public void setChangeLocation(String changeLocation) {
        this.changeLocation = changeLocation;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
          .add("id", id)
          .add("environment", environment)
          .add("identifier", identifier)
          .add("name", name)
          .add("shortDescription", shortDescription)
          .add("classifier", classifier)
          .add("deThdIdentifier", deThdIdentifier)
          .add("releasedOn", releasedOn)
          .add("highSecurityFlag", highSecurityFlag)
          .add("lastChange", lastChange)
          .add("changeLocation", changeLocation)
          .add("changedBy", changedBy)
          .add("functionalUnits", functionalUnits)
          .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserRole userRole = (UserRole) o;
        return Objects.equal(id, userRole.id) && Objects.equal(environment, userRole.environment) && Objects.equal(identifier, userRole.identifier) && Objects.equal(
          name, userRole.name) && Objects.equal(shortDescription, userRole.shortDescription) && classifier == userRole.classifier && Objects.equal(
          deThdIdentifier, userRole.deThdIdentifier) && Objects.equal(releasedOn, userRole.releasedOn) && Objects.equal(highSecurityFlag, userRole.highSecurityFlag)
          && Objects.equal(lastChange, userRole.lastChange) && Objects.equal(changeLocation, userRole.changeLocation) && Objects.equal(changedBy, userRole.changedBy)
          && Objects.equal(functionalUnits, userRole.functionalUnits);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, environment, identifier, name, shortDescription, classifier, deThdIdentifier, releasedOn, highSecurityFlag, lastChange,
          changeLocation, changedBy, functionalUnits);
    }
}
