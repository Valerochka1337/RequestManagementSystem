package org.valerochka1337.exceptions.request;

public class NoSuchRequestException extends RequestException {
  public NoSuchRequestException(Long id) {
    super(String.format("No such request with id: %d", id));
  }
}
