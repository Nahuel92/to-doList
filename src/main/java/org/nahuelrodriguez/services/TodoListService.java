package org.nahuelrodriguez.services;

import org.nahuelrodriguez.dtos.TodoItemDTO;

import java.util.List;

public interface TodoListService {
    void addNewTodoItem(final TodoItemDTO dto);

    void deleteTodoItem(final Long id);

    void deleteAllTodoItems();

    List<TodoItemDTO> getAllTodoItems();

    void updateTodoItem(final TodoItemDTO dto);
}
