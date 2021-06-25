package org.nahuelrodriguez.dao;

import org.nahuelrodriguez.entity.TodoItem;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;

import java.util.UUID;

public interface Repository extends ReactiveCassandraRepository<TodoItem, UUID> {
}
