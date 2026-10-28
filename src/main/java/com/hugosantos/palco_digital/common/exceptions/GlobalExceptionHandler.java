package com.hugosantos.palco_digital.common.exceptions;

import java.net.URI;
import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

  // Bean Validation exception handler
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ProblemDetail handleValidationErrors(MethodArgumentNotValidException e, HttpServletRequest request) {
    final String errorMessage = e.getBindingResult().getFieldErrors().stream()
      .map(error -> error.getField() + ": " + error.getDefaultMessage())
      .collect(Collectors.joining(", "));

    final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, errorMessage);
    problemDetail.setTitle("Validation Failed");
    problemDetail.setType(URI.create(request.getRequestURI()));
    problemDetail.setProperty("timestamp", Instant.now());
    return problemDetail;
  }

  // Business logic exception handlers (ex: email already exists, channel not found, etc.)
  @ExceptionHandler(IllegalArgumentException.class)
  public ProblemDetail handleIllegalArgument(IllegalArgumentException e, HttpServletRequest request) {
    final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    problemDetail.setTitle("Bad Request");
    problemDetail.setType(URI.create(request.getRequestURI()));
    problemDetail.setProperty("timestamp", Instant.now());
    return problemDetail;
  }

  // State machine exception handlers (ex: trying to start a presentation that is already started, etc.)
  @ExceptionHandler(IllegalStateException.class)
  public ProblemDetail handleIllegalState(IllegalStateException e, HttpServletRequest request) {
    final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_CONTENT, e.getMessage());
    problemDetail.setTitle("Bad Request");
    problemDetail.setType(URI.create(request.getRequestURI()));
    problemDetail.setProperty("timestamp", Instant.now());
    return problemDetail;
  }

  // Optimistic locking exception handler
  @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
  public ProblemDetail handleOptimisticLockingFailure(ObjectOptimisticLockingFailureException e, HttpServletRequest request) {
    final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, "Conflict occurred while trying to update the resource. Please, update and try again.");
    problemDetail.setTitle("Conflict");
    problemDetail.setType(URI.create(request.getRequestURI()));
    problemDetail.setProperty("timestamp", Instant.now());
    return problemDetail;
  }
}
