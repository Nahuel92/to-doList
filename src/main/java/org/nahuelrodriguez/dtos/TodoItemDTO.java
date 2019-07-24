package org.nahuelrodriguez.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class TodoItemDTO {
    @NotNull(message = "Id can not be null")
    private Long id;

    @NotBlank(message = "Description can not be null or empty")
    private String description;

    public TodoItemDTO(Long id, String description) {
        this.id = id;
        this.description = description;
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
        TodoItemDTO that = (TodoItemDTO) o;
        return id.equals(that.id) &&
                description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }

    @Override
    public String toString() {
        return "TodoItemDTO{" +
                "description='" + description + '\'' +
                '}';
    }
}
