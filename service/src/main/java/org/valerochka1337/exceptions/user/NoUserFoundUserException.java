package org.valerochka1337.exceptions.user;

public class NoUserFoundUserException extends UserException {
  public NoUserFoundUserException() {
    super("No users found with such part of username");
  }
}
