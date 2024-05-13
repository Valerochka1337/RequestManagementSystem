package org.valerochka1337.exceptions.handlers;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.valerochka1337.exceptions.ApiException;
import org.valerochka1337.exceptions.request.RequestException;
import org.valerochka1337.exceptions.user.UserException;

@RestControllerAdvice
public class ApiExceptionHandler {
  @ExceptionHandler(value = {AuthenticationException.class})
  public ResponseEntity<Object> handleAuthException(AuthenticationException ex) {
    HttpStatus status = HttpStatus.UNAUTHORIZED;
    ApiException apiException =
        new ApiException(
            ex.getMessage(),
            status.value(),
            status.getReasonPhrase(),
            ZonedDateTime.now(ZoneId.of("Z")));

    return new ResponseEntity<>(apiException, status);
  }

  @ExceptionHandler(value = {AccessDeniedException.class})
  public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
    HttpStatus status = HttpStatus.FORBIDDEN;
    ApiException apiException =
        new ApiException(
            ex.getMessage(),
            status.value(),
            status.getReasonPhrase(),
            ZonedDateTime.now(ZoneId.of("Z")));

    return new ResponseEntity<>(apiException, status);
  }

  @ExceptionHandler(value = {RequestException.class, UserException.class})
  public ResponseEntity<Object> handleCatException(RequestException ex) {
    HttpStatus badRequest = HttpStatus.BAD_REQUEST;
    ApiException apiException =
        new ApiException(
            ex.getMessage(),
            badRequest.value(),
            badRequest.getReasonPhrase(),
            ZonedDateTime.now(ZoneId.of("Z")));

    return new ResponseEntity<>(apiException, badRequest);
  }
}
