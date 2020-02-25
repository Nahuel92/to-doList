package org.nahuelrodriguez.daos;

import org.nahuelrodriguez.entities.TodoItem;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import java.util.UUID;

public interface Repository extends ReactiveCassandraRepository<TodoItem, UUID> {
}
