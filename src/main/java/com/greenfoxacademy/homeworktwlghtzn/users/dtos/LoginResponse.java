package com.greenfoxacademy.homeworktwlghtzn.users.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponse {

  private String token;
  private String tokenType;
  private int account;

  public LoginResponse(String token, int account) {
    this.token = token;
    this.account = account;
    tokenType = "HW-token";
  }
}
