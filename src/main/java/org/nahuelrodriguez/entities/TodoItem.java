package org.nahuelrodriguez.entities;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.Instant;
import java.util.Objects;

@Table("to_do_items")
public class TodoItem {
    @PrimaryKey
    @Column
    private Long id;

    @Column
    private String description;

    @Column
    private Instant createdDatetime;

    public TodoItem() {
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(Instant createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TodoItem todoItem = (TodoItem) o;
        return id.equals(todoItem.id) &&
                description.equals(todoItem.description) &&
                Objects.equals(createdDatetime, todoItem.createdDatetime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, createdDatetime);
    }

    @Override
    public String toString() {
        return "TodoItem{" +
                "description='" + description + '\'' +
                ", createdDatetime=" + createdDatetime +
                '}';
    }
}
