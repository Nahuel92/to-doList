package org.nahuelrodriguez.services;

import org.nahuelrodriguez.dtos.TodoItemDTO;

import java.util.Collection;
import java.util.List;

public interface TodoListService {
    void addNewTodoItem(final TodoItemDTO dto);

    void addNewTodoItems(final Collection<TodoItemDTO> dtos);

    void deleteTodoItem(final Long id);

    void deleteAllTodoItems();

    List<TodoItemDTO> getAllTodoItems();

    void updateTodoItem(final TodoItemDTO dto);
}
