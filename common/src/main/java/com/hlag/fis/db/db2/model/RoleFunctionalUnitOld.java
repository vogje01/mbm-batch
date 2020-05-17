package com.hlag.fis.db.db2.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.hlag.fis.db.mysql.model.PrimaryKeyIdentifier;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TZ1810")
public class RoleFunctionalUnitOld implements PrimaryKeyIdentifier {

    @EmbeddedId
    private RoleFunctionalUnitIdOld id;

    public RoleFunctionalUnitIdOld getId() {
        return id;
    }

    public void setId(RoleFunctionalUnitIdOld id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        RoleFunctionalUnitOld that = (RoleFunctionalUnitOld) o;
        return Objects.equal(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
