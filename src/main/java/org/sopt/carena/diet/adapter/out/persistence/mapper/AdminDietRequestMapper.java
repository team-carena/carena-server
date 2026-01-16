package org.sopt.carena.diet.adapter.out.persistence.mapper;

import org.sopt.carena.diet.adapter.in.web.dto.request.AdminDietChunkRequest;
import org.sopt.carena.diet.adapter.in.web.dto.request.AdminDietRequest;
import org.sopt.carena.diet.domain.DietChunk;
import org.sopt.carena.diet.domain.DietInformation;
import org.sopt.carena.diet.domain.DietSection;

import java.util.List;

public class AdminDietRequestMapper {

    public static DietInformation toInformation(final AdminDietRequest request) {
        return new DietInformation(
                request.title(),
                request.reference(),
                request.referenceUrl(),
                toChunks(request.chunks())
        );
    }

    public static List<DietChunk> toChunks(final List<AdminDietChunkRequest> requests) {
        return requests.stream()
                .map(AdminDietRequestMapper::toChunk)
                .toList();
    }

    private static DietChunk toChunk(final AdminDietChunkRequest request) {
        return new DietChunk(
                DietSection.valueOf(request.section()),
                request.content(),
                request.chunkOrder()
        );
    }
}