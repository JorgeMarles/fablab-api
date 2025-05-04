package com.example.demo.exceptions;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
    	logger.error("Error inesperado: ", ex);
        return new ResponseEntity<>(createErrorResponse("Ocurrió un error inesperado: " + ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(ValidationException ex, WebRequest request) {
    	logger.error("Error de validación: ", ex);
        return new ResponseEntity<>(createErrorResponse("Error de validación: " + ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(createErrorResponse("Recurso no encontrado: " + ex.getMessage()), HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(ResourceReferencedByOthersException.class)
    public ResponseEntity<Object> handleResourceReferencedByOthersException(ResourceReferencedByOthersException ex, WebRequest request) {
        return new ResponseEntity<>(createErrorResponse("Violación de integridad: " + ex.getMessage()), HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(ex.getMessage()));
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        List<String> errors = new ArrayList<String>();
        
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String errorMessage = violation.getMessage();
            errors.add(errorMessage);
        }
        
        return new ResponseEntity<>(createErrorResponse(errors.get(0)), HttpStatus.BAD_REQUEST);
    }
    
    private ErrorResponse createErrorResponse(String message) {
        return new ErrorResponse(true, message);
    }

   
    private static class ErrorResponse {
        private boolean error;
        private String message;

        public ErrorResponse(boolean error, String message) {
            this.error = error;
            this.message = message;
        }

        @SuppressWarnings("unused")
		public boolean isError() {
            return error;
        }

        @SuppressWarnings("unused")
		public String getMessage() {
            return message;
        }
    }
}
