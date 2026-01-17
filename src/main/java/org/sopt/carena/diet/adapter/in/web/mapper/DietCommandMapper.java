package org.sopt.carena.diet.adapter.in.web.mapper;

import org.sopt.carena.diet.adapter.in.web.dto.request.CreateAdminDietRequest;
import org.sopt.carena.diet.application.dto.command.CreateDietCommand;
import org.springframework.stereotype.Component;

@Component
public class DietCommandMapper {

    //Request DTO → Command 변환
    public CreateDietCommand toCommand(final CreateAdminDietRequest request) {
        return new CreateDietCommand(
                request.title(),
                request.reference(),
                request.referenceUrl(),
                request.chunks().stream()
                        .map(chunk -> new CreateDietCommand.DietChunkCommand(
                                chunk.section(),
                                chunk.content(),
                                chunk.chunkOrder()
                        ))
                        .toList(),
                request.recommendedFoods(),
                request.cautionaryFoods()
        );
    }
}