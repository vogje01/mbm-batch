package com.hlag.fis.batch.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.springframework.batch.core.ExitStatus;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ExitStatusInfo {

	@Column(name = "EXIT_CODE")
	private String exitCode;
	@Column(name = "EXIT_MESSAGE")
	private String exitDescription;

	public ExitStatusInfo() {
        // JSON constructor
	}

	public ExitStatusInfo(ExitStatus original) {
		this.exitCode = original.getExitCode();
		this.exitDescription = original.getExitDescription();
	}

	public String getExitCode() {
		return exitCode;
	}

	public void setExitCode(String exitCode) {
		this.exitCode = exitCode;
	}

	public String getExitDescription() {
		return exitDescription;
	}

	public void setExitDescription(String exitDescription) {
		this.exitDescription = exitDescription;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ExitStatusInfo that = (ExitStatusInfo) o;
		return Objects.equal(exitCode, that.exitCode) && Objects.equal(exitDescription, that.exitDescription);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(exitCode, exitDescription);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("exitCode", exitCode).add("exitDescription", exitDescription).toString();
	}
}
