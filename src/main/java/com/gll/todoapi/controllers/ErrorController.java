package com.gll.todoapi.controllers;

import com.gll.todoapi.exceptions.AlreadyUsedException;
import com.gll.todoapi.exceptions.NotFoundException;
import com.gll.todoapi.exceptions.UnauthorizedException;
import com.gll.todoapi.responses.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.time.ZoneId;

@ControllerAdvice
@Slf4j
public class ErrorController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleInternalError(Exception e) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                "Something goes wrong", e, "Internal Server Error");
    }

    @ExceptionHandler({
            NoResourceFoundException.class,
            HttpMessageNotReadableException.class,
            HttpRequestMethodNotSupportedException.class,
            MissingServletRequestParameterException.class,
            NotFoundException.class,
            BadCredentialsException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequestException(Exception e) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST,
                "Bad request: " + e.getMessage(), e,
                "Bad Requests Error: " + e.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(Exception e) {
        return buildErrorResponse(HttpStatus.FORBIDDEN,
                "Forbidden: " + e.getMessage(), e,
                "Forbidden Error: " + e.getMessage());
    }

    @ExceptionHandler(AlreadyUsedException.class)
    public ResponseEntity<ErrorResponse> handleConflictException(Exception e) {
        return buildErrorResponse(HttpStatus.CONFLICT,
                "Resource already used: " + e.getMessage(), e,
                "Conflict Error: " + e.getMessage());
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String message,
                                                             Exception e, String logMessage) {
        ErrorResponse response = new ErrorResponse(
                message,
                status.value(),
                LocalDateTime.now(ZoneId.of("Z"))
        );
        log.error("[{}] {} - {}", status, logMessage, e.getMessage(), e);
        return new ResponseEntity<>(response, status);
    }
}
