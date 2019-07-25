package org.nahuelrodriguez.services;

import org.nahuelrodriguez.dtos.TodoItemDTO;
import org.springframework.data.domain.Page;

import java.util.Collection;

public interface TodoListService {
    void addNewTodoItem(TodoItemDTO dto);

    void addNewTodoItems(Collection<TodoItemDTO> dtos);

    void deleteTodoItem(Long id);

    void deleteAllTodoItems();

    Page<TodoItemDTO> getAllTodoItems();

    Page<TodoItemDTO> getAllTodoItemsSearchingByKeywords(String keywords);

    void updateTodoItem(TodoItemDTO dto);
}
