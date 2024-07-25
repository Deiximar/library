package com.library;

public class Gender {
  private int idGender;
  private String gender;

  public Gender(int idGender, String gender) {
    this.idGender = idGender;
    this.gender = gender;
  }

  public int getIdGender() {
    return this.idGender;
  }

  public String getGender() {
    return this.gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }
}
