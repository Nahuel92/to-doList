package org.nahuelrodriguez.requests.dtos;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

public class NewTodoItemRequest implements Serializable {
    @NotBlank(message = "Description can not be null or empty")
    private String description;

    @NotBlank(message = "Status can not be null or empty")
    private String status;

    public NewTodoItemRequest() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewTodoItemRequest that = (NewTodoItemRequest) o;
        return Objects.equals(description, that.description) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, status);
    }

    @Override
    public String toString() {
        return "NewTodoItemRequest{" +
                "description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
