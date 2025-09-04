package org.taller01.catalogservice.domain;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document("products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    private String id;

    @NotBlank(message = "name is required")
    @Pattern(regexp = "^[a-zA-Z0-9\\s-_.]+$", message = "name must be alphanumeric")
    private String name;

    @NotNull(message = "price is required")
    @DecimalMin(value = "0.00", message = "price must be >= 0.00")
    private BigDecimal price;

    @NotNull(message = "stock is required")
    @DecimalMin(value = "1", message = "stock must be > 0")
    private Integer stock;
}
