package com.project.exception;

public class CustomValidationException extends RuntimeException {

  public CustomValidationException(String reason) {
    super("Validation error, " + reason);
  }
}
