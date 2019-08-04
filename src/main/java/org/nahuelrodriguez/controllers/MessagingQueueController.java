package org.nahuelrodriguez.controllers;

import org.nahuelrodriguez.requests.dtos.TodoItemRequest;
import org.nahuelrodriguez.responses.DTOErrors;
import org.nahuelrodriguez.responses.ErrorList;
import org.nahuelrodriguez.services.MessagingQueueProducer;
import org.nahuelrodriguez.validators.ListValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/todo-list")
public class MessagingQueueController {
    private final ListValidator<TodoItemRequest> validator;
    private final MessagingQueueProducer producer;

    @Autowired
    public MessagingQueueController(final ListValidator<TodoItemRequest> validator, final MessagingQueueProducer producer) {
        this.validator = validator;
        this.producer = producer;
    }

    @PostMapping(path = "/items")
    public ResponseEntity addNewTodoItems(@RequestBody final List<TodoItemRequest> dtos) {
        if (dtos.isEmpty())
            return new ResponseEntity<>(new DTOErrors("Empty request"), HttpStatus.BAD_REQUEST);

        final var errors = validator.validate(dtos);

        if (!errors.isEmpty())
            return new ResponseEntity<>(new ErrorList(errors), HttpStatus.BAD_REQUEST);

        producer.sendMessage(dtos);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
