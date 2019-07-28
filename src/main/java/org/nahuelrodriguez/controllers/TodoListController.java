package org.nahuelrodriguez.controllers;

import org.nahuelrodriguez.dtos.TodoItemDTO;
import org.nahuelrodriguez.responses.DTOErrors;
import org.nahuelrodriguez.responses.ErrorList;
import org.nahuelrodriguez.services.TodoListService;
import org.nahuelrodriguez.validators.ListValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(path = "/todo-list")
public class TodoListController {
    private final TodoListService service;
    private final ListValidator<TodoItemDTO> validator;

    @Autowired
    public TodoListController(final TodoListService service, final ListValidator<TodoItemDTO> validator) {
        this.service = service;
        this.validator = validator;
    }

    @PostMapping(path = "/item")
    public ResponseEntity addNewTodoItem(@RequestBody @Validated final TodoItemDTO dto) {
        service.addNewTodoItem(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(path = "/items")
    public ResponseEntity addNewTodoItems(@RequestBody final List<TodoItemDTO> dtos) {
        if (dtos.isEmpty())
            return new ResponseEntity<>(new DTOErrors("Empty request"), HttpStatus.BAD_REQUEST);

        final Map<Integer, Set<String>> errors = validator.validate(dtos);

        if (!errors.isEmpty())
            return new ResponseEntity<>(new ErrorList(errors), HttpStatus.BAD_REQUEST);

        service.addNewTodoItems(dtos);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/item/{id}")
    public ResponseEntity deleteTodoItem(@PathVariable("id") final Long id) {
        service.deleteTodoItem(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/items")
    public ResponseEntity deleteAllTodoItems() {
        service.deleteAllTodoItems();
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/items")
    public List<TodoItemDTO> getAllTodoItems() {
        return service.getAllTodoItems();
    }

    @PatchMapping(path = "/item")
    public ResponseEntity updateTodoItem(@RequestBody @Validated final TodoItemDTO dto) {
        service.updateTodoItem(dto);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
