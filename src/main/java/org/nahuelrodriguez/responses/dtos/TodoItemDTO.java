package org.nahuelrodriguez.responses.dtos;

import java.io.Serializable;
import java.util.Objects;

public class TodoItemDTO implements Serializable {
    private Long id;
    private String description;
    private String createdDatetime;

    public TodoItemDTO() {
    }

    public Long getId() {
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

    public String getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(String createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TodoItemDTO that = (TodoItemDTO) o;
        return Objects.equals(id, that.id) &&
                description.equals(that.description) &&
                Objects.equals(createdDatetime, that.createdDatetime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, createdDatetime);
    }

    @Override
    public String toString() {
        return "TodoItemDTO{" +
                "description='" + description + '\'' +
                ", createdDatetime='" + createdDatetime + '\'' +
                '}';
    }
}
