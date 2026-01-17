package org.sopt.carena.diet.adapter.in.web.mapper;

import org.sopt.carena.diet.adapter.in.web.dto.request.AdminDietRequest;
import org.sopt.carena.diet.application.dto.command.CreateDietCommand;
import org.springframework.stereotype.Component;

@Component
public class DietCommandMapper {

    //Request DTO → Command 변환
    public CreateDietCommand toCommand(final AdminDietRequest request) {
        return CreateDietCommand.builder()
                .title(request.title())
                .reference(request.reference())
                .referenceUrl(request.referenceUrl())
                .chunks(request.chunks().stream()
                        .map(chunk -> CreateDietCommand.DietChunkCommand.builder()
                                .section(chunk.section())
                                .content(chunk.content())
                                .chunkOrder(chunk.chunkOrder())
                                .build())
                        .toList())
                .recommendedFoods(request.recommendedFoods())
                .cautionaryFoods(request.cautionaryFoods())
                .build();
    }
}