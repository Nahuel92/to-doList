package org.nahuelrodriguez.controllers.advices;

import org.springframework.dao.DataAccessException;
import org.springframework.data.cassandra.CassandraConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TodoListControllerAdvice {
    @ExceptionHandler({DataAccessException.class})
    public ResponseEntity<Object> handleException(DataAccessException exception) {
        final String errorMessage = "Database access error. Error message:" + exception.getMessage();
        return new ResponseEntity<>(errorMessage, null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({CassandraConnectionFailureException.class})
    public ResponseEntity<Object> handleException() {
        final String errorMessage = "Database connection failed. Please try again later.";
        return new ResponseEntity<>(errorMessage, null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
