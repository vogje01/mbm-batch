package com.hlag.fis.db.db2.model;

import com.google.common.base.Objects;
import com.hlag.fis.db.converter.LegacyStringConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UsersIdOld implements Serializable {

	@Column(name = "USER_ID")
	@Convert(converter = LegacyStringConverter.class)
	private String userId;

	@Column(name = "HISTORY_FROM")
	private Long historyFrom;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getHistoryFrom() {
		return historyFrom;
	}

	public void setHistoryFrom(Long historyFrom) {
		this.historyFrom = historyFrom;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UsersIdOld that = (UsersIdOld) o;
		return Objects.equal(userId, that.userId) &&
				Objects.equal(historyFrom, that.historyFrom);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(userId, historyFrom);
	}
}
