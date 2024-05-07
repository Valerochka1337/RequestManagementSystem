package org.valerochka1337.exceptions.request;

public class InvalidStatusRequestException extends RequestException {
  public InvalidStatusRequestException() {
    super("Invalid status for making this operation");
  }
}
