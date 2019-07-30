package org.nahuelrodriguez.services.implementation;

import org.nahuelrodriguez.daos.Repository;
import org.nahuelrodriguez.entities.TodoItem;
import org.nahuelrodriguez.exceptions.NotFoundException;
import org.nahuelrodriguez.mappers.TodoItemDTOMapper;
import org.nahuelrodriguez.mappers.TodoItemMapper;
import org.nahuelrodriguez.requests.dtos.TodoItemRequest;
import org.nahuelrodriguez.responses.dtos.TodoItemDTO;
import org.nahuelrodriguez.services.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CassandraDBService implements TodoListService {
    private final TodoItemMapper toEntityMapper;
    private final TodoItemDTOMapper toDtoMapper;
    private final Repository repository;

    @Autowired
    public CassandraDBService(final Repository repository) {
        this.toEntityMapper = new TodoItemMapper();
        this.toDtoMapper = new TodoItemDTOMapper();
        this.repository = repository;
    }

    public void addNewTodoItem(final TodoItemRequest dto) {
        final TodoItem entity = toEntityMapper.from(dto);
        entity.setCreatedDatetime(Instant.now());
        repository.save(entity);
    }

    @CacheEvict(value = "todoItems", key = "#p0")
    public void deleteTodoItem(final Long id) {
        final Optional<TodoItem> entity = repository.findById(id);
        repository.deleteById(id);
        entity.orElseThrow(NotFoundException::new);
    }

    @CacheEvict(value = "todoItems", allEntries = true)
    public void deleteAllTodoItems() {
        repository.deleteAll();
    }

    @Cacheable(value = "todoItems", unless = "#result.size() == 0")
    public List<TodoItemDTO> getAllTodoItems() {
        final List<TodoItemDTO> dtos = new ArrayList<>();
        repository.findAll().forEach(entity -> dtos.add(toDtoMapper.from(entity)));
        return dtos;
    }

    @CacheEvict(value = "todoItems", allEntries = true)
    public void updateTodoItem(final TodoItemRequest dto) {
        final Optional<TodoItem> entity = repository.findById(dto.getId());
        entity.ifPresent(e -> {
            e.setDescription(dto.getDescription());
            repository.save(e);
        });
        entity.orElseThrow(NotFoundException::new);
    }
}
