package org.sopt.carena.diet.adapter.in.web.dto.request;

import java.util.List;

public record AdminDietRequest (
    String title,
    String reference,
    String referenceUrl,
    List<AdminDietChunkRequest> chunks
) {
}
