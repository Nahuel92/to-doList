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
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public TodoItemDTO addNewTodoItem(final NewTodoItemRequest dto) {
        final var entity = toEntityMapper.from(dto);
        entity.setId(UUID.randomUUID());
        entity.setStatus("Created");
        entity.setCreatedDatetime(Instant.now());
        return toDtoMapper.from(repository.save(entity));
    }

    @CacheEvict(value = "tasks", key = "#result.id")
    public void deleteTodoItem(final UUID id) {
        final var entity = repository.findById(id);
        repository.deleteById(id);
        entity.orElseThrow(NotFoundException::new);
    }

    @CacheEvict(value = "tasks", allEntries = true)
    public void deleteAllTodoItems() {
        repository.deleteAll();
    }

    public Collection<TodoItem> getAllTodoItems(final PageRequest pageRequest) {
        return repository.findAll(pageRequest)
                .getContent()
                .stream()
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
