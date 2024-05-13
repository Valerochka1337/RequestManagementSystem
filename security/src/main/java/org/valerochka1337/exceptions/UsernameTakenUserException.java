package org.valerochka1337.exceptions;

import javax.naming.AuthenticationException;

public class UsernameTakenUserException extends AuthenticationException {
  public UsernameTakenUserException() {
    super("This username is already taken");
  }
}
