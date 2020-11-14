package com.greenfoxacademy.homeworktwlghtzn.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponse {

  private String token;
  private String tokenType;
  private int GBDAmount;

  public LoginResponse(String token, int GBDAmount) {
    this.token = token;
    this.GBDAmount = GBDAmount;
    tokenType = "HW-token";
  }
}
