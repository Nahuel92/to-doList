package org.nahuelrodriguez.controllers;

import com.google.common.annotations.Beta;
import org.nahuelrodriguez.dtos.TodoItemDTO;
import org.nahuelrodriguez.responses.ErrorList;
import org.nahuelrodriguez.services.TodoListService;
import org.nahuelrodriguez.validators.ListValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(path = "/todoList")
public class TodoListController {
    private final TodoListService service;
    private final ListValidator<TodoItemDTO> validator;

    @Autowired
    public TodoListController(TodoListService service, ListValidator<TodoItemDTO> validator) {
        this.service = service;
        this.validator = validator;
    }

    @PostMapping(path = "/items")
    public ResponseEntity addNewTodoItem(@RequestBody @Validated TodoItemDTO dto) {
        service.addNewTodoItem(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(path = "/items/bulk")
    public ResponseEntity addNewTodoItems(@RequestBody List<TodoItemDTO> dtos) {
        Map<Integer, Set<String>> errors = validator.validate(dtos);

        if (!errors.isEmpty())
            return new ResponseEntity<>(new ErrorList(errors), HttpStatus.BAD_REQUEST);

        service.addNewTodoItems(dtos);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/items/{id}")
    public ResponseEntity deleteTodoItem(@PathVariable("id") Long id) {
        service.deleteTodoItem(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/items")
    public ResponseEntity deleteAllTodoItems() {
        service.deleteAllTodoItems();
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/items")
    public Page<TodoItemDTO> getAllTodoItems() {
        return service.getAllTodoItems();
    }

    @Beta
    @GetMapping(path = "/items/{keywords}")
    public Page<TodoItemDTO> getAllTodoItemsSearchingByKeywords(@PathVariable("keywords") String keywords) {
        return service.getAllTodoItemsSearchingByKeywords(keywords);
    }

    @PatchMapping(path = "/items")
    public ResponseEntity updateTodoItem(@RequestBody @Validated TodoItemDTO dto) {
        service.updateTodoItem(dto);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
