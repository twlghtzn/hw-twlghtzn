package com.greenfoxacademy.homeworktwlghtzn.exceptionhandling.customexceptions;

import lombok.Getter;

@Getter
public class CredentialsNotValidException extends RuntimeException {

  private final String message;

  public CredentialsNotValidException(String message) {
    super();
    this.message = message;
  }
}
