package com.sec.eventify.controller;

import com.sec.eventify.dto.ErrorResponse;
import com.sec.eventify.exception.EventNotFoundExecption;
import com.sec.eventify.exception.UnauthorizedActionException;
import com.sec.eventify.exception.UsernameAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestControllerAdvice
public class AdviceController {

    private ErrorResponse build(HttpStatus status, String message, HttpServletRequest request) {
        return new ErrorResponse(LocalDateTime.now(), status.value(),
                status.getReasonPhrase(), message, request.getRequestURI());
    }

    @ExceptionHandler(EventNotFoundExecption.class)
    public ErrorResponse handle(EventNotFoundExecption ex, HttpServletRequest req) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), req);
    }

    @ExceptionHandler(UnauthorizedActionException.class)
    public ErrorResponse handle(UnauthorizedActionException ex, HttpServletRequest req) {
        return build(HttpStatus.FORBIDDEN, ex.getMessage(), req);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ErrorResponse handle(UsernameAlreadyExistsException ex, HttpServletRequest req) {
        return build(HttpStatus.CONFLICT, ex.getMessage(), req);
    }

    @ExceptionHandler(RuntimeException.class)
    public ErrorResponse handle(RuntimeException ex, HttpServletRequest req) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage(), req);
    }
}