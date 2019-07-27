package org.nahuelrodriguez.daos;

import org.nahuelrodriguez.entities.TodoItem;
import org.springframework.data.repository.CrudRepository;

public interface Repository extends CrudRepository<TodoItem, Long> {
}
