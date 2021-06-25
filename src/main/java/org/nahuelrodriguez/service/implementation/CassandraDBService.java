package org.nahuelrodriguez.service.implementation;

import org.nahuelrodriguez.dao.Repository;
import org.nahuelrodriguez.exception.NotFoundException;
import org.nahuelrodriguez.mapper.TodoItemDTOMapper;
import org.nahuelrodriguez.mapper.TodoItemMapper;
import org.nahuelrodriguez.request.dto.NewTodoItemRequest;
import org.nahuelrodriguez.request.dto.UpdateTodoItemRequest;
import org.nahuelrodriguez.response.dto.TodoItemDTO;
import org.nahuelrodriguez.service.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

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

    @CachePut(value = "tasks", key = "#result.subscribe().id")
    public Mono<TodoItemDTO> addNewTodoItem(final NewTodoItemRequest dto) {
        final var entity = toEntityMapper.from(dto);
        entity.setId(UUID.randomUUID());
        entity.setStatus("Created");
        entity.setCreatedDatetime(Instant.now());
        return toDtoMapper.from(repository.save(entity));
    }

    @CacheEvict(value = "tasks", key = "#id")
    public void deleteTodoItem(final String id) {
        repository.deleteById(UUID.fromString(id));
    }

    @CacheEvict(value = "tasks", allEntries = true)
    public void deleteAllTodoItems() {
        repository.deleteAll();
    }

    public Mono<TodoItemDTO> getTodoItem(final String id) {
        return repository.findById(UUID.fromString(id))
                .map(toDtoMapper::from);
    }

    public Flux<TodoItemDTO> getAllTodoItems() {
        return repository.findAll()
                .map(toDtoMapper::from);
    }

    @CachePut(value = "tasks", key = "#dto.id")
    public void updateTodoItem(final UpdateTodoItemRequest dto) {
        final var idFromString = UUID.fromString(dto.getId());
        final var entity = repository.findById(idFromString);

        entity.subscribe(e -> {
            e.setDescription(dto.getDescription());
            e.setStatus(dto.getStatus());
            repository.save(e);
        }, error -> {
            throw new NotFoundException();
        });
    }
}
