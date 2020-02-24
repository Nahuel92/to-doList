package org.nahuelrodriguez.controllers;

import org.nahuelrodriguez.requests.dtos.NewTodoItemRequest;
import org.nahuelrodriguez.requests.dtos.UpdateTodoItemRequest;
import org.nahuelrodriguez.responses.dtos.TodoItemDTO;
import org.nahuelrodriguez.services.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/v1/todo-list/items", produces = {"application/json", "text/xml"})
public class TodoListController {
    private final TodoListService service;

    @Autowired
    public TodoListController(final TodoListService service) {
        this.service = service;
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<TodoItemDTO> addNewTodoItem(@RequestBody @Validated final NewTodoItemRequest dto) {
        return new EntityModel<>(service.addNewTodoItem(dto),
                linkTo(methodOn(TodoListController.class).addNewTodoItem(dto)).withSelfRel()
        );
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTodoItem(@PathVariable("id") final String id) {
        service.deleteTodoItem(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllTodoItems() {
        service.deleteAllTodoItems();
    }

    @GetMapping(path = "/{id}")
    public EntityModel<TodoItemDTO> getTodoItem(@PathVariable("id") final String id) {
        return new EntityModel<>(service.getTodoItem(id),
                linkTo(methodOn(TodoListController.class).getTodoItem(id)).withSelfRel(),
                linkTo(methodOn(TodoListController.class).getAllTodoItems()).withRel("todoItems")
        );
    }

    @GetMapping
    public CollectionModel<EntityModel<TodoItemDTO>> getAllTodoItems() {
        final var pageRequest = PageRequest.of(0, 15);
        final var todoItems = service.getAllTodoItems(pageRequest)
                .stream()
                .map(todoItem -> new EntityModel<>(todoItem,
                                linkTo(methodOn(TodoListController.class).getTodoItem(todoItem.getId())).withSelfRel()
                        )
                )
                .collect(Collectors.toUnmodifiableSet());

        return new CollectionModel<>(todoItems, linkTo(methodOn(TodoListController.class)
                .getAllTodoItems())
                .withSelfRel()
        );
    }

    @PatchMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTodoItem(@RequestBody @Validated final UpdateTodoItemRequest dto) {
        service.updateTodoItem(dto);
    }
}
