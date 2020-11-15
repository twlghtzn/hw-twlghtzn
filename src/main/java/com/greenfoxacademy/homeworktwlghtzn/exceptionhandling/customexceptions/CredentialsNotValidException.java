package com.greenfoxacademy.homeworktwlghtzn.exceptionhandling.customexceptions;

public class CredentialsNotValidException extends RuntimeException {

  private String message;

  public CredentialsNotValidException(String message) {
    super();
    this.message = message;
  }
}
