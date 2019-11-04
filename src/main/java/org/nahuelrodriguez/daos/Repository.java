package org.nahuelrodriguez.daos;

import org.nahuelrodriguez.entities.TodoItem;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface Repository extends CrudRepository<TodoItem, UUID> {
}
