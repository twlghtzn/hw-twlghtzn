package com.greenfoxacademy.homeworktwlghtzn.exceptionhandling.customexceptions;

import lombok.Getter;

@Getter
public class RequestIncorrectException extends RuntimeException {

  private final String message;

  public RequestIncorrectException(String message) {
    super();
    this.message = message;
  }
}
