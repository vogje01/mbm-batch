package com.momentum.batch.server.manager.service.util;

import java.io.Serializable;

/**
 * Class description.
 *
 * @author Jens Vogt jensvogt47@gmail.com
 * @version 0.0.2
 * @since 0.0.1
 */
public class JwtRequest implements Serializable {

	private static final long serialVersionUID = 5926468583005150707L;
	private String userId;
	private String password;
	private String orgUnit;

	public JwtRequest() {
		// Need default constructor for JSON Parsing
	}

	public JwtRequest(String userId, String password, String orgUnit) {
		this.setUserId(userId);
		this.setPassword(password);
		this.setOrgUnit(orgUnit);
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String username) {
		this.userId = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOrgUnit() {
		return orgUnit;
	}

	public void setOrgUnit(String orgUnit) {
		this.orgUnit = orgUnit;
	}
}