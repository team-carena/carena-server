package org.sopt.carena.diet.adapter.out.persistence.mapper;

import org.sopt.carena.diet.adapter.out.persistence.entity.DietChunkEntity;
import org.sopt.carena.diet.adapter.out.persistence.entity.DietInformationEntity;
import org.sopt.carena.diet.domain.DietInformation;
import org.sopt.carena.diet.domain.value.DietChunk;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;



@Component
public class DietPersistenceMapper {

    public DietInformation toDomain(DietInformationEntity entity) {

        return DietInformation.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .reference(entity.getReference())
                .referenceUrl(entity.getReferenceUrl())
                .chunks(entity.getChunks().stream()
                        .map(this::toDietChunk)
                        .toList())
                .recommendedCategories(
                        entity.getRecommendedFood() != null
                                ? entity.getRecommendedFood().getCategories()
                                : Map.of()
                )
                .cautionaryFoods(
                        entity.getCautionaryFood() != null
                                ? entity.getCautionaryFood().getCautionary()
                                : List.of()
                )
                .build();
    }

    public DietChunk toDietChunk(DietChunkEntity chunkEntity) {
        return new DietChunk(
                chunkEntity.getSection(),
                chunkEntity.getContent(),
                chunkEntity.getChunkOrder(),
                chunkEntity.getEmbeddingText(),
                chunkEntity.getEmbedding()
        );
    }
}