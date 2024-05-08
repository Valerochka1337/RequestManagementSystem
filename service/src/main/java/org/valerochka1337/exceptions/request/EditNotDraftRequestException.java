package org.valerochka1337.exceptions.request;

public class EditNotDraftRequestException extends RequestException {
  public EditNotDraftRequestException() {
    super("Cannot edit requests in status different from DRAFT");
  }
}
