package org.nahuelrodriguez.controllers;

import org.nahuelrodriguez.representationModelAssemblers.TodoItemDTORepresentationModelAssembler;
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

import java.util.UUID;

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
    public TodoItemDTO addNewTodoItem(@RequestBody @Validated final NewTodoItemRequest dto) {
        return service.addNewTodoItem(dto);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTodoItem(@PathVariable("id") UUID id) {
        service.deleteTodoItem(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllTodoItems() {
        service.deleteAllTodoItems();
    }

    @GetMapping
    public CollectionModel<EntityModel<TodoItemDTO>> getAllTodoItems() {
        final var pageRequest = PageRequest.of(0, 15);
        final var todoItems = service.getAllTodoItems(pageRequest);

        final var todoItemDTOAssembler = new TodoItemDTORepresentationModelAssembler();
        final var recentResources = CollectionModel.wrap(todoItemDTOAssembler.toCollectionModel(todoItems));

        return recentResources.add(linkTo(methodOn(TodoListController.class)
                        .getAllTodoItems()
                ).withRel("allTodoItems")
        );
    }

    @PatchMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTodoItem(@RequestBody @Validated final UpdateTodoItemRequest dto) {
        service.updateTodoItem(dto);
    }
}
