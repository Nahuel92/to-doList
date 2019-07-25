package org.nahuelrodriguez.controllers;

import com.google.common.annotations.Beta;
import org.nahuelrodriguez.dtos.TodoItemDTO;
import org.nahuelrodriguez.services.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/todoList")
public class TodoListController {
    private final TodoListService service;

    @Autowired
    public TodoListController(TodoListService service) {
        this.service = service;
    }

    @PostMapping(path = "/items")
    public ResponseEntity addNewTodoItem(@RequestBody @Validated TodoItemDTO dto) {
        service.addNewTodoItem(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(path = "/items/bulk")
    public ResponseEntity addNewTodoItems(@RequestBody @Validated Collection<TodoItemDTO> dtos) {
        service.addNewTodoItems(dtos);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/items/{id}")
    public void deleteTodoItem(@PathVariable("id") Long id) {
        service.deleteTodoItem(id);
    }

    @DeleteMapping(path = "/items")
    public void deleteAllTodoItems() {
        service.deleteAllTodoItems();
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
    public void updateTodoItem(@RequestBody @Validated TodoItemDTO dto) {
        service.updateTodoItem(dto);
    }
}
