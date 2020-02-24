package org.nahuelrodriguez.daos;

import org.nahuelrodriguez.entities.TodoItem;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.UUID;

public interface Repository extends CassandraRepository<TodoItem, UUID> {
}
