package com.hugosantos.palco_digital.common.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
  public record StandardError(Instant timestamp, Integer status, String error, String path){}

  // TODO: Create BusinessException class to handle business exceptions and return a proper response with status code 422
  // TODO: Create handlers to differentiate 400 and 422 status codes for different types of exceptions
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<StandardError> handleIllegalArgument(IllegalArgumentException e, HttpServletRequest request) {
    final StandardError error = new StandardError(
      Instant.now(), 
      HttpStatus.UNPROCESSABLE_CONTENT.value(),
      e.getMessage(), 
      request.getRequestURI()
    );
    return ResponseEntity.unprocessableContent().body(error);
  }

  @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
  public ResponseEntity<StandardError> handleOptimisticLockingFailure(ObjectOptimisticLockingFailureException e, HttpServletRequest request) {
    final StandardError error = new StandardError(
      Instant.now(), 
      HttpStatus.CONFLICT.value(), 
      "Conflict occurred while trying to update the resource. Please, update and try again.", 
      request.getRequestURI()
    );
    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }
}
