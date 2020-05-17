package com.hlag.fis.db.db2.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.hlag.fis.db.converter.LegacyStringConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class RoleFunctionalUnitIdOld implements Serializable {

    @Column(name = "FK0TZ1820ENVIRONME")
    @Convert(converter = LegacyStringConverter.class)
    private String roleEnvironment;

    @Column(name = "FK0TZ1820IDENTIFIE")
    private Short roleIdentifier;

    @Column(name = "FK_TZ1780ENVIRONME")
    @Convert(converter = LegacyStringConverter.class)
    private String functionalUnitEnvironment;

    @Column(name = "FK_TZ1780IDENTIFIE")
    @Convert(converter = LegacyStringConverter.class)
    private String functionalUnitIdentifier;

    public String getRoleEnvironment() {
        return roleEnvironment;
    }

    public void setRoleEnvironment(String roleEnvironment) {
        this.roleEnvironment = roleEnvironment;
    }

    public Short getRoleIdentifier() {
        return roleIdentifier;
    }

    public void setRoleIdentifier(Short roleIdentifier) {
        this.roleIdentifier = roleIdentifier;
    }

    public String getFunctionalUnitEnvironment() {
        return functionalUnitEnvironment;
    }

    public void setFunctionalUnitEnvironment(String functionalUnitEnviroment) {
        this.functionalUnitEnvironment = functionalUnitEnviroment;
    }

    public String getFunctionalUnitIdentifier() {
        return functionalUnitIdentifier;
    }

    public void setFunctionalUnitIdentifier(String functionalUnitIdentifier) {
        this.functionalUnitIdentifier = functionalUnitIdentifier;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("roleEnvironment", roleEnvironment)
            .add("roleIdentifier", roleIdentifier)
            .add("functionalUnitEnvironment", functionalUnitEnvironment)
            .add("functionalUnitIdentifier", functionalUnitIdentifier)
            .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        RoleFunctionalUnitIdOld that = (RoleFunctionalUnitIdOld) o;
        return Objects.equal(roleEnvironment, that.roleEnvironment) && Objects.equal(roleIdentifier, that.roleIdentifier) && Objects.equal(
            functionalUnitEnvironment, that.functionalUnitEnvironment) && Objects.equal(functionalUnitIdentifier, that.functionalUnitIdentifier);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(roleEnvironment, roleIdentifier, functionalUnitEnvironment, functionalUnitIdentifier);
    }
}
