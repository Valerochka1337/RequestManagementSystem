package org.valerochka1337.exceptions;

import org.springframework.security.core.AuthenticationException;

public class NonExistingLoginUserException extends AuthenticationException {
  public NonExistingLoginUserException() {
    super("No user with such login");
  }
}
