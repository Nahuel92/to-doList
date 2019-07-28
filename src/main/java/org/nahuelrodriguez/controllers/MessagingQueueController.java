package org.nahuelrodriguez.controllers;

import org.nahuelrodriguez.dtos.TodoItemDTO;
import org.nahuelrodriguez.responses.DTOErrors;
import org.nahuelrodriguez.responses.ErrorList;
import org.nahuelrodriguez.services.MessagingQueueProducer;
import org.nahuelrodriguez.validators.ListValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(path = "/todo-list")
public class MessagingQueueController {
    private final ListValidator<TodoItemDTO> validator;
    private final MessagingQueueProducer producer;

    @Autowired
    public MessagingQueueController(final ListValidator<TodoItemDTO> validator, final MessagingQueueProducer producer) {
        this.validator = validator;
        this.producer = producer;
    }

    @PostMapping(path = "/items")
    public ResponseEntity addNewTodoItems(@RequestBody final List<TodoItemDTO> dtos) {
        if (dtos.isEmpty())
            return new ResponseEntity<>(new DTOErrors("Empty request"), HttpStatus.BAD_REQUEST);

        final Map<Integer, Set<String>> errors = validator.validate(dtos);

        if (!errors.isEmpty())
            return new ResponseEntity<>(new ErrorList(errors), HttpStatus.BAD_REQUEST);

        producer.sendMessage(dtos);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
