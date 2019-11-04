package org.nahuelrodriguez.controllers;

import org.nahuelrodriguez.requests.dtos.NewTodoItemRequest;
import org.nahuelrodriguez.requests.dtos.UpdateTodoItemRequest;
import org.nahuelrodriguez.responses.dtos.TodoItemDTO;
import org.nahuelrodriguez.services.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/todo-list")
public class TodoListController {
    private final TodoListService service;

    @Autowired
    public TodoListController(final TodoListService service) {
        this.service = service;
    }

    @PostMapping(path = "/item")
    public ResponseEntity addNewTodoItem(@RequestBody @Validated final NewTodoItemRequest dto) {
        return new ResponseEntity<>(service.addNewTodoItem(dto), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/item/{id}")
    public ResponseEntity deleteTodoItem(@PathVariable("id") String id) {
        service.deleteTodoItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/items")
    public ResponseEntity deleteAllTodoItems() {
        service.deleteAllTodoItems();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/items")
    public ResponseEntity<Collection<TodoItemDTO>> getAllTodoItems() {
        return new ResponseEntity<>(service.getAllTodoItems(), HttpStatus.OK);
    }

    @PatchMapping(path = "/item")
    public ResponseEntity updateTodoItem(@RequestBody @Validated final UpdateTodoItemRequest dto) {
        service.updateTodoItem(dto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
