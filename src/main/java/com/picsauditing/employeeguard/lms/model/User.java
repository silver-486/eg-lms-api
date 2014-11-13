package com.picsauditing.employeeguard.lms.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Locale;
import java.util.Set;

@Entity
@JsonIgnoreProperties({"accountIdSet", "id"})
//@JsonIgnoreProperties(ignoreUnknown=true)
public class User/* implements Persistable<Long> */ {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

//  private transient boolean persisted;

	//@JsonProperty("")

	private long userId;
	private String username;
	private UserType type;

	@Transient
	private Set<Integer> accountIdSet;
	private String accountIds;

	private String firstName;
	private String lastName;
	private Locale locale;
	private String email;


	public User() {
	}

	public User(long userId, String username) {
		this.userId = userId;
	}


	//  @Override
	public Long getId() {
		return id;
	}

/*
	@Override
  public boolean isNew() {
    return !persisted;
  }

  public User withPersisted(boolean persisted) {
    this.persisted = persisted;
    return this;
  }
*/

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
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

	public Set<Integer> getAccountIdSet() {
		return accountIdSet;
	}

	public void setAccountIdSet(Set<Integer> accountIdSet) {
		this.accountIdSet = accountIdSet;
	}

	public String getAccountIds() {
		return accountIds;
	}

	public void setAccountIds(String accountIds) {
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

	@Override
	public String toString() {
		return "User{" +
			"id=" + id +
			", userId='" + userId + '\'' +
			", username='" + username + '\'' +
			", type=" + type +
			", accountIdSet=" + accountIdSet +
			", accountIds='" + accountIds + '\'' +
			", firstName='" + firstName + '\'' +
			", lastName='" + lastName + '\'' +
			", locale=" + locale +
			", email='" + email + '\'' +
			'}';
	}
}