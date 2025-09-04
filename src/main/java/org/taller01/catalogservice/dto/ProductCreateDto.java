package org.taller01.catalogservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

@Schema(description = "DTO for creating a product")
public record ProductCreateDto(

        @Schema(description = "Product name", example = "Coffee")
        @NotBlank(message = "name is required")
        @Pattern(regexp="^[a-zA-Z0-9\\s-_.]+$", message = "name must be alphanumeric")
        String name,

        @Schema(description = "Product price", example = "39.99")
        @NotNull(message = "price is required")
        @DecimalMin(value = "0.00", message = "price must be >= 0.00")
        BigDecimal price,

        @Schema(description = "Product stock", example = "1000")
        @NotNull(message = "stock is required")
        @DecimalMin(value = "1", message = "stock must be > 0")
        Integer stock
) implements ProductInput {}
