package org.valerochka1337.exceptions.request;

public class NotNULLIdCreationRequestException extends RequestException {
  public NotNULLIdCreationRequestException() {
    super("Request's Id must be null, when creation a request");
  }
}
