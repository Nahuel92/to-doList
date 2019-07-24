package org.nahuelrodriguez.services;

import org.nahuelrodriguez.dtos.TodoItemDTO;
import org.nahuelrodriguez.entities.TodoItem;
import org.springframework.data.domain.Page;

import java.util.Collection;

public interface TodoListService {
    void addNewTodoItem(TodoItemDTO dto);

    void addNewTodoItems(Collection<TodoItemDTO> dtos);

    void deleteTodoItem(Long id);

    void deleteAllTodoItems();

    Page<TodoItem> getAllTodoItems();

    Page<TodoItem> getAllTodoItemsSearchingByKeywords(String keywords);

    void updateTodoItem(TodoItemDTO dto);
}
