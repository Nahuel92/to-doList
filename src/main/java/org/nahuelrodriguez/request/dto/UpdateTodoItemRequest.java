package org.nahuelrodriguez.request.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class UpdateTodoItemRequest {
    @NotNull
    private String id;

    @NotBlank(message = "Description can not be null or empty")
    private String description;

    @NotBlank(message = "Status can not be null or empty")
    private String status;

    public UpdateTodoItemRequest() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateTodoItemRequest that = (UpdateTodoItemRequest) o;
        return Objects.equals(id, that.id) && Objects.equals(description, that.description) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, status);
    }

    @Override
    public String toString() {
        return "UpdateTodoItemRequest{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
