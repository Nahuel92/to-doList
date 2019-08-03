package org.nahuelrodriguez.controllers.advices;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.nahuelrodriguez.exceptions.NotFoundException;
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
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<DTOErrors> handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        final Set<String> errorMessages = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toSet());
        return new ResponseEntity<>(new DTOErrors(errorMessages), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DataAccessResourceFailureException.class})
    public ResponseEntity<DTOErrors> handleConnectionFailureException() {
        final var error = "Database connection failed. Please try again later.";
        return new ResponseEntity<>(new DTOErrors(error), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({InvalidDataAccessApiUsageException.class})
    public ResponseEntity<DTOErrors> handleInvalidQueryException() {
        final var error = "An invalid query has been executed by the server.";
        return new ResponseEntity<>(new DTOErrors(error), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<DTOErrors> handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException exception) {
        final var error = exception.getMessage() + ". Verify the URL and params and try again.";
        return new ResponseEntity<>(new DTOErrors(error), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler({MismatchedInputException.class, JsonParseException.class})
    public ResponseEntity<DTOErrors> handleMismatchedInputException() {
        final var error = "Cannot deserialize data. Please check params and try again.";
        return new ResponseEntity<>(new DTOErrors(error), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<DTOErrors> handleHttpMessageNotReadableException(final HttpMessageNotReadableException exception) {
        if (exception.getCause() instanceof MismatchedInputException)
            return handleMismatchedInputException();

        final var error = "Required request body is missing.";
        return new ResponseEntity<>(new DTOErrors(error), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<DTOErrors> handleHttpMessageNotReadableException() {
        final var error = "Argument type mismatch. Please check data types and try again.";
        return new ResponseEntity<>(new DTOErrors(error), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<DTOErrors> handleNotFoundException() {
        final var error = "Entity not found.";
        return new ResponseEntity<>(new DTOErrors(error), HttpStatus.NOT_FOUND);
    }
}
