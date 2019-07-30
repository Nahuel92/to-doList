package org.nahuelrodriguez.services;

import org.nahuelrodriguez.requests.dtos.TodoItemRequest;
import org.nahuelrodriguez.responses.dtos.TodoItemDTO;

import java.util.List;

public interface TodoListService {
    void addNewTodoItem(final TodoItemRequest dto);

    void deleteTodoItem(final Long id);

    void deleteAllTodoItems();

    List<TodoItemDTO> getAllTodoItems();

    void updateTodoItem(final TodoItemRequest dto);
}
