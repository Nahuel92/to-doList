package org.nahuelrodriguez.services;

import org.nahuelrodriguez.entities.TodoItem;
import org.nahuelrodriguez.requests.dtos.NewTodoItemRequest;
import org.nahuelrodriguez.requests.dtos.UpdateTodoItemRequest;
import org.nahuelrodriguez.responses.dtos.TodoItemDTO;

import java.util.Collection;

public interface TodoListService {
    TodoItem addNewTodoItem(final NewTodoItemRequest dto);

    void deleteTodoItem(final String id);

    void deleteAllTodoItems();

    Collection<TodoItemDTO> getAllTodoItems();

    void updateTodoItem(final UpdateTodoItemRequest dto);
}
