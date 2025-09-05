package org.taller01.catalogservice.dto;

import jakarta.validation.constraints.NotNull;

public record StockAdjustRequest(@NotNull Integer delta) {}
