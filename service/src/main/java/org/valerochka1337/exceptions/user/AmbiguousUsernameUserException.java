package org.valerochka1337.exceptions.user;

public class AmbiguousUsernameUserException extends UserException {
  public AmbiguousUsernameUserException() {
    super("Ambiguous username given, there are plenty of users with this part of username");
  }
}
