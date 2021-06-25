package org.nahuelrodriguez.request.dto;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

public class NewTodoItemRequest implements Serializable {
    public NewTodoItemRequest() {
    }

    @NotBlank(message = "Description can not be null or empty")
    private String description;

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
        NewTodoItemRequest that = (NewTodoItemRequest) o;
        return Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }

    @Override
    public String toString() {
        return "NewTodoItemRequest{" +
                "description='" + description + '\'' +
                '}';
    }
}
