package org.nahuelrodriguez.requests.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

public class TodoItemRequest implements Serializable {
    @NotNull(message = "Id can not be null")
    private Long id;

    @NotBlank(message = "Description can not be null or empty")
    private String description;

    public TodoItemRequest() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TodoItemRequest that = (TodoItemRequest) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }

    @Override
    public String toString() {
        return "TodoItemRequest{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
