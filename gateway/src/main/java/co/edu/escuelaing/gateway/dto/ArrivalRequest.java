package co.edu.escuelaing.gateway.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ArrivalRequest(
        @NotBlank(message = "Name is required")
        @Size(max = 100, message = "Name must be at most 100 characters")
        String name) {

    public ArrivalRequest {
        if (name != null) {
            name = name.strip();
        }
    }
}
