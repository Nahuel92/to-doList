package org.nahuelrodriguez.service;

import org.nahuelrodriguez.request.dto.NewTodoItemRequest;
import org.nahuelrodriguez.request.dto.UpdateTodoItemRequest;
import org.nahuelrodriguez.response.dto.TodoItemDTO;
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
