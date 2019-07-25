package org.nahuelrodriguez.services.implementation;

import org.nahuelrodriguez.dtos.TodoItemDTO;
import org.nahuelrodriguez.entities.TodoItem;
import org.nahuelrodriguez.mappers.TodoItemMapper;
import org.nahuelrodriguez.services.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class CassandraService implements TodoListService {
    private final TodoItemMapper mapper;
    private CassandraOperations repository;

    @Autowired
    public CassandraService(CassandraOperations repository) {
        this.repository = repository;
        this.mapper = new TodoItemMapper();
    }

    public void addNewTodoItem(TodoItemDTO dto) {
        repository.insert(mapper.from(dto));
    }

    public void addNewTodoItems(Collection<TodoItemDTO> dtos) {
        dtos.stream()
                .map(mapper::from)
                .forEach(entity -> repository.insert(entity));
    }

    public void deleteTodoItem(Long id) {
        repository.deleteById(id, TodoItem.class);
    }

    public void deleteAllTodoItems() {
        repository.truncate(TodoItem.class);
    }

    public Page<TodoItem> getAllTodoItems() {
        return new PageImpl<>(repository.select("SELECT * FROM to_do_items", TodoItem.class));
    }

    public Page<TodoItem> getAllTodoItemsSearchingByKeywords(String keywords) {
        return new PageImpl<>(repository.select("SELECT * FROM to_do_items WHERE 'description: *" + keywords + "*", TodoItem.class));
    }

    public void updateTodoItem(TodoItemDTO dto) {
        Optional<TodoItem> entityToUpdate = Optional.ofNullable(repository.selectOneById(dto.getId(), TodoItem.class));

        entityToUpdate.ifPresent(entity -> {
            entity.setDescription(dto.getDescription());
            repository.update(entity);
        });
    }
}
