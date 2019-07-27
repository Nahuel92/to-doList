package org.nahuelrodriguez.services;

import org.nahuelrodriguez.dtos.TodoItemDTO;
import org.springframework.data.domain.Page;

import java.util.Collection;

public interface TodoListService {
    void addNewTodoItem(final TodoItemDTO dto);

    void addNewTodoItems(final Collection<TodoItemDTO> dtos);

    void deleteTodoItem(final Long id);

    void deleteAllTodoItems();

    Page<TodoItemDTO> getAllTodoItems();

    void updateTodoItem(final TodoItemDTO dto);
}
