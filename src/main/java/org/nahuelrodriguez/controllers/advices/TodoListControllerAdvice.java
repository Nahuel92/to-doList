package org.nahuelrodriguez.controllers.advices;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.nahuelrodriguez.responses.DTOErrors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class TodoListControllerAdvice {
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<DTOErrors> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        final List<String> errorMessages = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return new ResponseEntity<>(new DTOErrors<>(errorMessages), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DataAccessResourceFailureException.class})
    public ResponseEntity<DTOErrors<String>> handleConnectionFailureException() {
        final String error = "Database connection failed. Please try again later.";
        return new ResponseEntity<>(generateErrorList(error), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({InvalidDataAccessApiUsageException.class})
    public ResponseEntity<DTOErrors<String>> handleInvalidQueryException() {
        final String error = "An invalid query has been executed by the server.";
        return new ResponseEntity<>(generateErrorList(error), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<DTOErrors<String>> handleMethodArgumentTypeMismatchException() {
        final String error = "Required request body is missing.";
        return new ResponseEntity<>(generateErrorList(error), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<DTOErrors<String>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        final String error = exception.getMessage() + ". Verify the URL and params and try again.";
        return new ResponseEntity<>(generateErrorList(error), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler({MismatchedInputException.class, JsonParseException.class})
    public ResponseEntity<DTOErrors<String>> handleMismatchedInputException() {
        final String error = "Cannot deserialize data. Please check params and try again.";
        return new ResponseEntity<>(generateErrorList(error), HttpStatus.BAD_REQUEST);
    }

    private DTOErrors<String> generateErrorList(final String error) {
        return new DTOErrors<>(List.of(error));
    }
}
