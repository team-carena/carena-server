package org.sopt.carena.diet.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record AdminDietChunkRequest(
        @NotBlank String section,
        @NotBlank String content,
        @Positive int chunkOrder
) {
}