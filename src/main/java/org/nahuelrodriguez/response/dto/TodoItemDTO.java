package org.nahuelrodriguez.response.dto;

import java.io.Serializable;
import java.util.Objects;

public class TodoItemDTO implements Serializable {
    private String id;
    private String description;
    private String status;
    private String createdDatetime;

    public TodoItemDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        return Objects.equals(id, that.id) && Objects.equals(description, that.description) && Objects.equals(status, that.status) && Objects.equals(createdDatetime, that.createdDatetime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, status, createdDatetime);
    }

    @Override
    public String toString() {
        return "TodoItemDTO{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", createdDatetime='" + createdDatetime + '\'' +
                '}';
    }
}
