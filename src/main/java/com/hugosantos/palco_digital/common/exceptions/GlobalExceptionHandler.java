package com.hugosantos.palco_digital.common.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
  public record StandardError(Instant timestamp, Integer status, String error, String path){}

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<StandardError> handleIllegalArgument(IllegalArgumentException e, HttpServletRequest request) {
    final StandardError error = new StandardError(
      Instant.now(), 
      HttpStatus.BAD_REQUEST.value(), 
      e.getMessage(), 
      request.getRequestURI()
    );
    return ResponseEntity.badRequest().body(error);
  }
}
