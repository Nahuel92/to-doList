package org.nahuelrodriguez.controller;

import org.nahuelrodriguez.request.dto.NewTodoItemRequest;
import org.nahuelrodriguez.request.dto.UpdateTodoItemRequest;
import org.nahuelrodriguez.response.dto.TodoItemDTO;
import org.nahuelrodriguez.service.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

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
    public EntityModel<Mono<TodoItemDTO>> addNewTodoItem(@RequestBody @Validated final NewTodoItemRequest dto) {
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
    public Mono<EntityModel<TodoItemDTO>> getTodoItem(@PathVariable("id") final String id) {
        return service.getTodoItem(id)
                .map(toEntityModel())
                .map(entityModel -> entityModel.add(linkTo(methodOn(TodoListController.class).getAllTodoItems())
                                .withRel("todoItems")
                        )
                );
    }

    // TODO: Find the way to return a Flux instead of returning a CollectionModel
    @GetMapping
    public CollectionModel<EntityModel<TodoItemDTO>> getAllTodoItems() {
        final Flux<EntityModel<TodoItemDTO>> todoItems = service.getAllTodoItems()
                .take(20)
                .map(toEntityModel());

        return new CollectionModel<>(todoItems.toIterable(),
                linkTo(methodOn(TodoListController.class)
                        .getAllTodoItems()
                ).withSelfRel()
        );
    }

    @PatchMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTodoItem(@RequestBody @Validated final UpdateTodoItemRequest dto) {
        service.updateTodoItem(dto);
    }

    private Function<TodoItemDTO, EntityModel<TodoItemDTO>> toEntityModel() {
        return dto -> new EntityModel<>(dto, linkTo(methodOn(TodoListController.class).getTodoItem(dto.getId()))
                .withSelfRel()
        );
    }
}
