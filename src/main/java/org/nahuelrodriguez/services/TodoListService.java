package org.nahuelrodriguez.services;

import org.nahuelrodriguez.requests.dtos.NewTodoItemRequest;
import org.nahuelrodriguez.requests.dtos.UpdateTodoItemRequest;
import org.nahuelrodriguez.responses.dtos.TodoItemDTO;

import java.util.Collection;
import java.util.UUID;

public interface TodoListService {
    TodoItemDTO addNewTodoItem(final NewTodoItemRequest dto);

    void deleteTodoItem(final UUID id);

    void deleteAllTodoItems();

    Collection<TodoItemDTO> getAllTodoItems();

    void updateTodoItem(final UpdateTodoItemRequest dto);
}
