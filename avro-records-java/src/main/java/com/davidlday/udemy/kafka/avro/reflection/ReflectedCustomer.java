package com.davidlday.udemy.kafka.avro.reflection;

import org.apache.avro.reflect.Nullable;

public class ReflectedCustomer {

  private String firstName;
  private String lastName;
  @Nullable private String nickname;

  public ReflectedCustomer(){}

  public ReflectedCustomer(String firstName, String lastName, String nickname) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.nickname = nickname;
  }

  public String fullName() {
    String fullName = this.firstName + " " + this.lastName;
    if (this.nickname != null) {
      fullName += " (" + this.nickname + ")";
    }
    return fullName;
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

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

}
