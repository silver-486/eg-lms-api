package com.picsauditing.employeeguard.lms.model;

import java.util.Locale;
import java.util.Set;

public class User {

	private String userId;
	private String picsUserID;
	private String username;
	private UserType type;
	private Set<Integer> accountIds;
	private String firstName;
	private String lastName;
	private Locale locale;
	private String email;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPicsUserID() {
		return picsUserID;
	}

	public void setPicsUserID(String picsUserID) {
		this.picsUserID = picsUserID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	public Set<Integer> getAccountIds() {
		return accountIds;
	}

	public void setAccountIds(Set<Integer> accountIds) {
		this.accountIds = accountIds;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
