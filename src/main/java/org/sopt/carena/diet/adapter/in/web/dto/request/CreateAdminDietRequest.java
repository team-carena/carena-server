package org.sopt.carena.diet.adapter.in.web.dto.request;

import java.util.List;
import java.util.Map;

public record CreateAdminDietRequest (
        String title,
        String reference,
        String referenceUrl,
        List<AdminDietChunkRequest> chunks,
        Map<String, List<String>> recommendedFoods,
        List<String> cautionaryFoods
) {
}
