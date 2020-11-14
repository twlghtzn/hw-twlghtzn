package com.greenfoxacademy.homeworktwlghtzn.exceptionhandling;

import com.fasterxml.jackson.core.JsonParseException;
import com.greenfoxacademy.homeworktwlghtzn.exceptionhandling.customexceptions.LoginRequestIncorrectException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

  @ExceptionHandler(LoginRequestIncorrectException.class)
  protected ResponseEntity<Object> handleLoginRequestFieldMissingException(
      LoginRequestIncorrectException ex) {
    String message = ex.getMessage();
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("message", message);

    return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
      HttpMediaTypeNotSupportedException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    Map<String, Object> body = new LinkedHashMap<>();
    String message = "Change data type to x-www-form-urlencoded";
    body.put("message", message);

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      MissingServletRequestParameterException ex, HttpHeaders headers,
      HttpStatus status, WebRequest request) {
    String error = ex.getParameterName() + " parameter is missing";
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("message", error);

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MissingRequestHeaderException.class)
  protected ResponseEntity<Object> handleMissingRequestHeaderException() {
    String error = "Request header is missing";
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("message", error);

    return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status,
      WebRequest request) {
    String message = ex.getLocalizedMessage();
    String error = "";
    int indexOf = message.indexOf(":");
    if (indexOf != -1) {
      error = message.substring(0, indexOf);
    } else {
      error = message;
    }
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("message", error);

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(JsonParseException.class)
  public ResponseEntity<Object> handleJsonParseException() {
    Map<String, Object> body = new LinkedHashMap<>();
    String message = "Request not readable";
    body.put("message", message);

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }
}
