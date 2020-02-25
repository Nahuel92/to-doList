package org.nahuelrodriguez.services;

import org.nahuelrodriguez.requests.dtos.NewTodoItemRequest;
import org.nahuelrodriguez.requests.dtos.UpdateTodoItemRequest;
import org.nahuelrodriguez.responses.dtos.TodoItemDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TodoListService {
    Mono<TodoItemDTO> addNewTodoItem(final NewTodoItemRequest dto);

    void deleteTodoItem(final String id);

    void deleteAllTodoItems();

    Mono<TodoItemDTO> getTodoItem(final String id);

    Flux<TodoItemDTO> getAllTodoItems();

    void updateTodoItem(final UpdateTodoItemRequest dto);
}
