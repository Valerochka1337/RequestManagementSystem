package org.valerochka1337.exceptions;

import org.springframework.security.core.AuthenticationException;

public class NoSuchRoleException extends AuthenticationException {
  public NoSuchRoleException(String roleName) {
    super("No such role: " + roleName);
  }
}
