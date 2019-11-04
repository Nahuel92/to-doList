package org.nahuelrodriguez.services.implementation;

import org.nahuelrodriguez.daos.Repository;
import org.nahuelrodriguez.entities.TodoItem;
import org.nahuelrodriguez.exceptions.NotFoundException;
import org.nahuelrodriguez.mappers.TodoItemDTOMapper;
import org.nahuelrodriguez.mappers.TodoItemMapper;
import org.nahuelrodriguez.requests.dtos.NewTodoItemRequest;
import org.nahuelrodriguez.requests.dtos.UpdateTodoItemRequest;
import org.nahuelrodriguez.responses.dtos.TodoItemDTO;
import org.nahuelrodriguez.services.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    @CachePut(value = "tasks", key = "#result.id")
    public TodoItem addNewTodoItem(final NewTodoItemRequest dto) {
        final var entity = toEntityMapper.from(dto);
        entity.setId(UUID.randomUUID());
        entity.setCreatedDatetime(Instant.now());
        return repository.save(entity);
    }

    @CacheEvict(value = "tasks", key = "#result.id")
    public void deleteTodoItem(final String id) {
        if (id == null || !id.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$"))
            throw new IllegalArgumentException("The id can not be null");

        final var idFromString = UUID.fromString(id);
        final var entity = repository.findById(idFromString);
        repository.deleteById(idFromString);
        entity.orElseThrow(NotFoundException::new);
    }

    @CacheEvict(value = "tasks", allEntries = true)
    public void deleteAllTodoItems() {
        repository.deleteAll();
    }

    public Collection<TodoItemDTO> getAllTodoItems() {
        return StreamSupport.stream(repository.findAll().spliterator(), true)
                .map(toDtoMapper::from)
                .collect(Collectors.toUnmodifiableSet());
    }

    @CachePut(value = "tasks", key = "#dto.id")
    public void updateTodoItem(final UpdateTodoItemRequest dto) {
        final var idFromString = UUID.fromString(dto.getId());
        final var entity = repository.findById(idFromString);
        entity.ifPresent(e -> {
            e.setDescription(dto.getDescription());
            e.setStatus(dto.getStatus());
            repository.save(e);
        });
        entity.orElseThrow(NotFoundException::new);
    }
}
