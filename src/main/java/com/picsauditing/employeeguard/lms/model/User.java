package com.picsauditing.employeeguard.lms.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Locale;
import java.util.Set;

@Entity
public class User implements Serializable {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private long id;

  private static final long serialVersionUID = 1L;
  private String userId;
  private String picsUserID;
  private String username;
  private UserType type;

  @Transient
  private Set<Integer> accountIdSet;
  private String accountIds;

  private String firstName;
  private String lastName;
  private Locale locale;
  private String email;

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  public User() {
  }

  public User(long id, String userId) {
    this.id = id;
    this.userId = userId;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

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
      ", picsUserID='" + picsUserID + '\'' +
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