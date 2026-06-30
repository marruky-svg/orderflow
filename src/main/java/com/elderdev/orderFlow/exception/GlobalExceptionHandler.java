package com.elderdev.orderFlow.exception;

import com.elderdev.orderFlow.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException ex) {
        var error = new ErrorResponse(404, ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(404).body(error);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handlerAlreadyExists(AlreadyExistsException ex) {
        var error = new ErrorResponse(409, ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(409).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handlerIllegalArgument(IllegalArgumentException ex) {
        var error = new ErrorResponse(400, ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(400).body(error);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorResponse>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<ErrorResponse> errors = new ArrayList<>();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                        error.getDefaultMessage(),
                        LocalDateTime.now()
                )).toList());
    }
}
