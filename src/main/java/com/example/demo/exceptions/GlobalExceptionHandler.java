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
        logger.error("Error de argumento ilegal: ", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(ex.getMessage()));
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        List<String> errors = new ArrayList<String>();
        
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String errorMessage = violation.getMessage();
            errors.add(errorMessage);
        }
        
        return new ResponseEntity<>(createErrorResponse(errors), HttpStatus.BAD_REQUEST);
    }
    
    private ErrorResponse createErrorResponse(String message) {
        return new ErrorResponse(true, message);
    }

    private ErrorResponse createErrorResponse(List<String> messages) {
        return new ErrorResponse(true, messages);
    }

    private static class ErrorResponse {
        private boolean error;
        private String[] messages;

        public ErrorResponse(boolean error, String message) {
            this.error = error;
            this.messages = new String[]{message};
        }

        public ErrorResponse(boolean error, List<String> messages) {
            this.error = error;
            this.messages = messages.toArray(new String[0]);
        }

        @SuppressWarnings("unused")
		public boolean isError() {
            return error;
        }

        @SuppressWarnings("unused")
		public String[] getMessages() {
            return messages;
        }
    }
}
