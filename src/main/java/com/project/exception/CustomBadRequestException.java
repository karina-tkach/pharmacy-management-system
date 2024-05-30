package com.project.exception;

public class CustomBadRequestException extends RuntimeException {

  public CustomBadRequestException() {
    super("Error sending request");
  }
}
