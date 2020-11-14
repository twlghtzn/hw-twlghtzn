package com.greenfoxacademy.homeworktwlghtzn.exceptionhandling.customexceptions;

import lombok.Getter;

@Getter
public class LoginRequestIncorrectException extends RuntimeException {

  private final String message;

  public LoginRequestIncorrectException(String message) {
    super();
    this.message = message;
  }
}
