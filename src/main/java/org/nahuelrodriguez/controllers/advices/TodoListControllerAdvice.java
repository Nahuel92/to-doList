package org.nahuelrodriguez.controllers.advices;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TodoListControllerAdvice {
    @ExceptionHandler({DataAccessResourceFailureException.class})
    public ResponseEntity<Object> handleConnectionFailureException() {
        final String errorMessage = "Database connection failed. Please try again later.";
        return new ResponseEntity<>(errorMessage, null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({InvalidDataAccessApiUsageException.class})
    public ResponseEntity<Object> handleInvalidQueryException() {
        final String errorMessage = "An invalid query has been executed by the server.";
        return new ResponseEntity<>(errorMessage, null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
