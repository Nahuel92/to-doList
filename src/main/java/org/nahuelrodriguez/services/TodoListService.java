package org.nahuelrodriguez.services;

import org.nahuelrodriguez.requests.dtos.NewTodoItemRequest;
import org.nahuelrodriguez.requests.dtos.UpdateTodoItemRequest;
import org.nahuelrodriguez.responses.dtos.TodoItemDTO;
import org.springframework.data.domain.PageRequest;

import java.util.Collection;

public interface TodoListService {
    TodoItemDTO addNewTodoItem(final NewTodoItemRequest dto);

    void deleteTodoItem(final String id);

    void deleteAllTodoItems();

    TodoItemDTO getTodoItem(final String id);

    Collection<TodoItemDTO> getAllTodoItems(final PageRequest pageRequest);

    void updateTodoItem(final UpdateTodoItemRequest dto);
}
