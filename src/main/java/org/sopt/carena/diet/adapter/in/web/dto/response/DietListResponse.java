package org.sopt.carena.diet.adapter.in.web.dto.response;

import org.sopt.carena.diet.domain.DietSummary;
import org.springframework.data.domain.Slice;

import java.util.List;

public record DietListResponse(
        List<DietItemResponse> result,
        boolean hasNext
) {
    public static DietListResponse from(Slice<DietSummary> dietSlice) {
        List<DietItemResponse> items = dietSlice.getContent().stream()
                .map(DietItemResponse::from)
                .toList();

        return new DietListResponse(items, dietSlice.hasNext());
    }
}