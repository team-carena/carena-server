package org.sopt.carena.diet.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Map;

public record CreateAdminDietRequest (
        @NotBlank String title,
        @NotBlank String content,
        @NotBlank String reference,
        @NotBlank String referenceUrl,
        List<AdminDietChunkRequest> chunks,
        Map<String, List<String>> recommendedFoods,
        List<String> cautionaryFoods
) {
}
