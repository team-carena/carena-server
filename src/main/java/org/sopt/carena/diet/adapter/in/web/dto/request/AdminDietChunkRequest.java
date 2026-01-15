package org.sopt.carena.diet.adapter.in.web.dto.request;

public record AdminDietChunkRequest(
        String section,
        String content,
        int chunkOrder
) {
}