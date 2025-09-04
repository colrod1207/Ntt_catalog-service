package org.taller01.catalogservice.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductCreateUpdateDto(
        @NotBlank(message = "name is required")
        @Pattern(regexp="^[a-zA-Z0-9\\s-_.]+$", message = "name must be alphanumeric")
        String name,

        @NotNull(message = "price is required")
        @DecimalMin(value = "0.00", message = "price must be >= 0.00")
        BigDecimal price
) {}
