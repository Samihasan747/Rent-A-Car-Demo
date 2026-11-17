package com.RentACarDemo.exceptions;

import com.RentACarDemo.exceptions.dto.ExceptionResponseDto;
import com.RentACarDemo.exceptions.dto.ValidationExceptionDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionDto> handleException(MethodArgumentNotValidException  ex, HttpServletRequest servReq) {

        Map<String, String> fieldErrors =new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(error ->
                        fieldErrors.put(error.getField() , error.getDefaultMessage()));

        ValidationExceptionDto validationExceptionDto = new ValidationExceptionDto(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                servReq.getRequestURI(),
                "Invalid Input",
                fieldErrors
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(validationExceptionDto);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionResponseDto> handleDuplicateEmail(DataIntegrityViolationException ex, HttpServletRequest servReq) {

        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                "Email already exists!",
                servReq.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exceptionResponseDto);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponseDto> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest servReq) {

        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                servReq.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exceptionResponseDto);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponseDto> handleRuntimeException(RuntimeException ex, HttpServletRequest servReq) {
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                ex.getMessage(),
                servReq.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exceptionResponseDto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDto> handleRuntimeException(Exception ex, HttpServletRequest servReq) {
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                ex.getMessage(),
                servReq.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exceptionResponseDto);
    }

}
