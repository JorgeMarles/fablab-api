package com.example.demo.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.google.firebase.auth.FirebaseAuthException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
    	log.error("Error inesperado: ", ex);
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
        log.error("Error de argumento ilegal: ", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(FirebaseAuthException.class)
    public ResponseEntity<Object> handleFirebaseAuthException(FirebaseAuthException ex) {
        log.error("Error de autenticación de Firebase: ", ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(createErrorResponse(ex.getMessage()));
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
