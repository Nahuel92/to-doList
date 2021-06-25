package org.nahuelrodriguez.controller;

import org.nahuelrodriguez.request.dto.NewTodoItemRequest;
import org.nahuelrodriguez.response.DTOErrors;
import org.nahuelrodriguez.response.ErrorList;
import org.nahuelrodriguez.service.MessagingQueueProducer;
import org.nahuelrodriguez.validator.ListValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/v1/todo-list/batch")
public class MessagingQueueController {
    private final ListValidator<NewTodoItemRequest> validator;
    private final MessagingQueueProducer producer;

    @Autowired
    public MessagingQueueController(final ListValidator<NewTodoItemRequest> validator, final MessagingQueueProducer producer) {
        this.validator = validator;
        this.producer = producer;
    }

    @PostMapping(path = "/items")
    public ResponseEntity addNewTodoItems(@RequestBody final List<NewTodoItemRequest> dtos) {
        if (dtos.isEmpty())
            return new ResponseEntity<>(new DTOErrors("Empty request"), HttpStatus.BAD_REQUEST);

        final var errors = validator.validate(dtos);

        if (!errors.isEmpty())
            return new ResponseEntity<>(new ErrorList(errors), HttpStatus.BAD_REQUEST);

        producer.sendMessage(dtos);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
