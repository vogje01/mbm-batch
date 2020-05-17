package com.hlag.fis.db.mysql.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.hlag.fis.db.attribute.functionalunit.FunctionalUnitClItemType;
import com.hlag.fis.db.db2.model.FunctionalUnitOld;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Functional unit entity.
 *
 * @author Jens.Vogt (jens.vogt@ext.hlag.com)
 * @version 0.0.3
 * @since 0.0.1
 */
@Entity
@Table(name = "FUNCTIONAL_UNIT")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class FunctionalUnit implements PrimaryKeyIdentifier {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @Column(name = "ENVIRONMENT")
    private String environment;

    @Column(name = "IDENTIFIER")
    private String identifier;

    @Column(name = "LAST_CHANGE")
    private Long lastChange;

    @Column(name = "CHANGED_BY")
    private String changedBy;

    @Column(name = "CHANGE_LOCATION")
    private String changeLocation;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "NON_PROD_USAGE")
    private String nonProdUsage;

    @Column(name = "NOT_AVAIL_FROM")
    private LocalDateTime notAvailFrom;

    @Column(name = "NOT_AVAIL_TO")
    private LocalDateTime notAvailTo;

    @Column(name = "VISIBLE_TITLE")
    private String visibleTitle;

    @Enumerated(EnumType.STRING)
    @Column(name = "CL_ITEM_TYPE")
    private FunctionalUnitClItemType functionalUnitClItemType;

    @Column(name = "DE_THD_IDENTIFIER")
    private Integer deThdIdentifier;

    @Column(name = "DI_TRUSTWORTH_EXCL")
    private Boolean diTrustworthExcl;

    @Column(name = "DI_ORG_RESTRICT")
    private Boolean diOrgRestrict;

    @ManyToMany(mappedBy = "functionalUnits")
    @Cascade(CascadeType.SAVE_UPDATE)
    private List<UserRole> userRoles = new ArrayList<>();

    public FunctionalUnit() {
        // JPA constructor
    }

    public void update(FunctionalUnitOld functionalUnitOld) {
        this.environment = functionalUnitOld.getId().getEnvironment();
        this.identifier = functionalUnitOld.getId().getIdentifier();
        this.name = functionalUnitOld.getName();
        this.description = functionalUnitOld.getDescription();
        this.nonProdUsage = functionalUnitOld.getNonProdUsage();
        this.notAvailFrom = functionalUnitOld.getNotAvailFrom() != null ? functionalUnitOld.getNotAvailFrom().toLocalDateTime() : null;
        this.notAvailTo = functionalUnitOld.getNotAvailTo() != null ? functionalUnitOld.getNotAvailTo().toLocalDateTime() : null;
        this.visibleTitle = functionalUnitOld.getVisibleTitle();
        this.functionalUnitClItemType = functionalUnitOld.getFunctionalUnitClItemType();
        this.deThdIdentifier = functionalUnitOld.getDeThdIdentifier();
        this.diTrustworthExcl = functionalUnitOld.getDiTrustworthExcl();
        this.diOrgRestrict = functionalUnitOld.getDiOrgRestrict();
        this.lastChange = functionalUnitOld.getLastChange();
        this.changedBy = functionalUnitOld.getChangedBy();
        this.changeLocation = functionalUnitOld.getChangeLocation() != null ? functionalUnitOld.getChangeLocation() : "H";
    }

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

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNonProdUsage() {
        return nonProdUsage;
    }

    public void setNonProdUsage(String nonProdUsage) {
        this.nonProdUsage = nonProdUsage;
    }

    public LocalDateTime getNotAvailFrom() {
        return notAvailFrom;
    }

    public void setNotAvailFrom(LocalDateTime notAvailFrom) {
        this.notAvailFrom = notAvailFrom;
    }

    public LocalDateTime getNotAvailTo() {
        return notAvailTo;
    }

    public void setNotAvailTo(LocalDateTime notAvailTo) {
        this.notAvailTo = notAvailTo;
    }

    public String getVisibleTitle() {
        return visibleTitle;
    }

    public void setVisibleTitle(String visibleTitle) {
        this.visibleTitle = visibleTitle;
    }

    public FunctionalUnitClItemType getFunctionalUnitClItemType() {
        return functionalUnitClItemType;
    }

    public void setFunctionalUnitClItemType(FunctionalUnitClItemType functionalUnitClItemType) {
        this.functionalUnitClItemType = functionalUnitClItemType;
    }

    public Integer getDeThdIdentifier() {
        return deThdIdentifier;
    }

    public void setDeThdIdentifier(Integer deThdIdentifier) {
        this.deThdIdentifier = deThdIdentifier;
    }

    public Boolean getDiTrustworthExcl() {
        return diTrustworthExcl;
    }

    public void setDiTrustworthExcl(Boolean diTrustworthExcl) {
        this.diTrustworthExcl = diTrustworthExcl;
    }

    public Boolean getDiOrgRestrict() {
        return diOrgRestrict;
    }

    public void setDiOrgRestrict(Boolean diOrgRestrict) {
        this.diOrgRestrict = diOrgRestrict;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> roles) {
        roles.forEach(this::addUserRole);
    }

    public void addUserRole(UserRole userRole) {
        if (!userRoles.contains(userRole)) {
            userRoles.add(userRole);
        }
    }

    public void removeUserRole(UserRole userRole) {
        if (userRoles.contains(userRole)) {
            userRoles.remove(userRole);
        }
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
          .add("id", id)
          .add("environment", environment)
          .add("identifier", identifier)
          .add("lastChange", lastChange)
          .add("changedBy", changedBy)
          .add("changeLocation", changeLocation)
          .add("name", name)
          .add("description", description)
          .add("nonProdUsage", nonProdUsage)
          .add("notAvailFrom", notAvailFrom)
          .add("notAvailTo", notAvailTo)
          .add("visibleTitle", visibleTitle)
          .add("clItemType", functionalUnitClItemType)
          .add("deThdIdentifier", deThdIdentifier)
          .add("diTrustworthExcl", diTrustworthExcl)
          .add("diOrgRestrict", diOrgRestrict)
          .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        FunctionalUnit that = (FunctionalUnit) o;
        return Objects.equal(id, that.id) && Objects.equal(environment, that.environment) && Objects.equal(identifier, that.identifier) && Objects.equal(lastChange,
          that.lastChange) && Objects.equal(changedBy, that.changedBy) && Objects.equal(changeLocation, that.changeLocation) && Objects.equal(
          name,
          that.name) && Objects.equal(description, that.description) && Objects.equal(nonProdUsage, that.nonProdUsage) && Objects.equal(notAvailFrom, that.notAvailFrom)
          && Objects.equal(notAvailTo, that.notAvailTo) && Objects.equal(visibleTitle, that.visibleTitle) && functionalUnitClItemType == that.functionalUnitClItemType
          && Objects.equal(deThdIdentifier, that.deThdIdentifier) && Objects.equal(diTrustworthExcl, that.diTrustworthExcl) && Objects.equal(diOrgRestrict,
          that.diOrgRestrict) && Objects.equal(userRoles, that.userRoles);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id,
          environment,
          identifier,
          lastChange,
          changedBy,
          changeLocation,
          name,
          description,
          nonProdUsage,
          notAvailFrom,
          notAvailTo,
          visibleTitle,
          functionalUnitClItemType,
          deThdIdentifier,
          diTrustworthExcl,
          diOrgRestrict,
          userRoles);
    }
}
