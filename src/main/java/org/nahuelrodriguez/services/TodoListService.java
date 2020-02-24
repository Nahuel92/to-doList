package org.nahuelrodriguez.services;

import org.nahuelrodriguez.entities.TodoItem;
import org.nahuelrodriguez.requests.dtos.NewTodoItemRequest;
import org.nahuelrodriguez.requests.dtos.UpdateTodoItemRequest;
import org.nahuelrodriguez.responses.dtos.TodoItemDTO;
import org.springframework.data.domain.PageRequest;

import java.util.Collection;
import java.util.UUID;

public interface TodoListService {
    TodoItemDTO addNewTodoItem(final NewTodoItemRequest dto);

    void deleteTodoItem(final UUID id);

    void deleteAllTodoItems();

    Collection<TodoItem> getAllTodoItems(final PageRequest pageRequest);

    void updateTodoItem(final UpdateTodoItemRequest dto);
}
