package org.nahuelrodriguez.controllers;

import org.nahuelrodriguez.dtos.TodoItemDTO;
import org.nahuelrodriguez.entities.TodoItem;
import org.nahuelrodriguez.services.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public void addNewTodoItem(@RequestBody @Validated TodoItemDTO dto) {
        service.addNewTodoItem(dto);
    }

    @PostMapping(path = "/items/bulk")
    public void addNewTodoItems(@RequestBody @Validated Collection<TodoItemDTO> dtos) {
        service.addNewTodoItems(dtos);
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
    public Page<TodoItem> getAllTodoItems() {
        return service.getAllTodoItems();
    }

    @GetMapping(path = "/items/{keywords}")
    public Page<TodoItem> getAllTodoItemsSearchingByKeywords(@PathVariable("keywords") String keywords) {
        return service.getAllTodoItemsSearchingByKeywords(keywords);
    }

    @PatchMapping(path = "/items")
    public void updateTodoItem(@RequestBody @Validated TodoItemDTO dto) {
        service.updateTodoItem(dto);
    }
}
