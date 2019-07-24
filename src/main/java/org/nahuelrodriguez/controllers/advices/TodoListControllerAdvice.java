package org.nahuelrodriguez.controllers.advices;

import com.datastax.driver.core.exceptions.NoHostAvailableException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TodoListControllerAdvice {
    @ExceptionHandler({DataAccessException.class})
    public ResponseEntity handleException(DataAccessException exception) {
        final String errorMessage = "Database access error. Error message:" + exception.getMessage();
        return new ResponseEntity<>(errorMessage, null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity handleException(NoHostAvailableException exception) {
        final String errorMessage = "Database server unreacheable. Error message:" + exception.getMessage();
        return new ResponseEntity<>(errorMessage, null, HttpStatus.BAD_REQUEST);
    }
}
