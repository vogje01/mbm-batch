package com.hlag.fis.db.db2.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.hlag.fis.db.attribute.common.LcValidStateA;
import com.hlag.fis.db.converter.LegacyStringConverter;
import com.hlag.fis.db.mysql.model.PrimaryKeyIdentifier;

import javax.persistence.*;

@Entity
@Table(name = "TZ0190")
public class TrustworthExclusionOld implements PrimaryKeyIdentifier {

	@EmbeddedId
	private TrustworthExclusionIdOld id;

	@Column(name = "REMARK")
	@Convert(converter = LegacyStringConverter.class)
	private String remark;

	@Column(name = "LC_VALID_STATE_A")
	@Convert(converter = LcValidStateA.Converter.class)
	private LcValidStateA lcValidStateA;

	@Column(name = "LAST_CHANGE")
	private Long lastChange;

	@Column(name = "CHANGED_BY")
	@Convert(converter = LegacyStringConverter.class)
	private String changedBy;

	public TrustworthExclusionOld() {
		// JPA constructor
	}

	public TrustworthExclusionIdOld getId() {
		return id;
	}

	public void setId(TrustworthExclusionIdOld id) {
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
			.add("remark", remark)
			.add("lcValidStateA", lcValidStateA)
			.add("lastChange", lastChange)
			.add("changedBy", changedBy)
			.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		TrustworthExclusionOld that = (TrustworthExclusionOld) o;
		return Objects.equal(id, that.id) && Objects.equal(remark, that.remark) && lcValidStateA == that.lcValidStateA && Objects.equal(lastChange, that.lastChange)
			&& Objects.equal(changedBy, that.changedBy);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id, remark, lcValidStateA, lastChange, changedBy);
	}
}
