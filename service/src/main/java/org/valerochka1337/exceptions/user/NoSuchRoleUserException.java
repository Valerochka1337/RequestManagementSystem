package org.valerochka1337.exceptions.user;

public class NoSuchRoleUserException extends UserException {
  public NoSuchRoleUserException(String roleName) {
    super(String.format("No such role with name: %s", roleName));
  }
}
