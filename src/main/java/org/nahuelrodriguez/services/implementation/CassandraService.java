package org.nahuelrodriguez.services.implementation;

import org.nahuelrodriguez.dtos.TodoItemDTO;
import org.nahuelrodriguez.entities.TodoItem;
import org.nahuelrodriguez.mappers.TodoItemDTOMapper;
import org.nahuelrodriguez.mappers.TodoItemMapper;
import org.nahuelrodriguez.services.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CassandraService implements TodoListService {
    private final TodoItemMapper toEntityMapper;
    private final TodoItemDTOMapper toDtoMapper;
    private CassandraOperations repository;

    @Autowired
    public CassandraService(CassandraOperations repository) {
        this.repository = repository;
        this.toEntityMapper = new TodoItemMapper();
        this.toDtoMapper = new TodoItemDTOMapper();
    }

    public void addNewTodoItem(TodoItemDTO dto) {
        TodoItem entity = toEntityMapper.from(dto);
        entity.setCreatedDatetime(Instant.now());

        repository.insert(entity);
    }

    public void addNewTodoItems(Collection<TodoItemDTO> dtos) {
        dtos.stream()
                .map(toEntityMapper::from)
                .forEach(entity -> {
                    entity.setCreatedDatetime(Instant.now());
                    repository.insert(entity);
                });
    }

    public void deleteTodoItem(Long id) {
        repository.deleteById(id, TodoItem.class);
    }

    public void deleteAllTodoItems() {
        repository.truncate(TodoItem.class);
    }

    public Page<TodoItemDTO> getAllTodoItems() {
        List<TodoItemDTO> dtos = repository
                .select("SELECT * FROM to_do_items", TodoItem.class)
                .stream()
                .map(toDtoMapper::from)
                .collect(Collectors.toList());
        return new PageImpl<>(dtos);
    }

    public Page<TodoItemDTO> getAllTodoItemsSearchingByKeywords(String keywords) {
        List<TodoItemDTO> dtos = repository
                .select("SELECT * FROM to_do_items WHERE 'description: *" + keywords + "*", TodoItem.class)
                .stream()
                .map(toDtoMapper::from)
                .collect(Collectors.toList());
        return new PageImpl<>(dtos);
    }

    public void updateTodoItem(TodoItemDTO dto) {
        Optional<TodoItem> entityToUpdate = Optional.ofNullable(repository.selectOneById(dto.getId(), TodoItem.class));

        entityToUpdate.ifPresent(entity -> {
            entity.setDescription(dto.getDescription());
            repository.update(entity);
        });
    }
}
