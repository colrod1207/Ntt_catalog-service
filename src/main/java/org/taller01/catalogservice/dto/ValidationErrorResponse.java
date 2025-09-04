package org.taller01.catalogservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.Map;

@Schema(description = "Error de validación con detalles por campo")
public record ValidationErrorResponse(
        int status,
        String error,
        String message,
        String path,
        Instant timestamp,
        @Schema(description = "Mapa campo -> mensaje de error")
        Map<String, String> fields
) {}
