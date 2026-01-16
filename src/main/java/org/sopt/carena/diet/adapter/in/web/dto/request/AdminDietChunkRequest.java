package org.sopt.carena.diet.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record AdminDietChunkRequest(
        @NotBlank String section,
        @NotBlank String content,
        @PositiveOrZero int chunkOrder
) {
}