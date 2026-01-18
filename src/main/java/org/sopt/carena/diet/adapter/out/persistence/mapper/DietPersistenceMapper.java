package org.sopt.carena.diet.adapter.out.persistence.mapper;

import org.sopt.carena.diet.adapter.out.persistence.entity.DietChunkEntity;
import org.sopt.carena.diet.adapter.out.persistence.entity.DietInformationEntity;
import org.sopt.carena.diet.domain.DietInformation;
import org.sopt.carena.diet.domain.value.CautionaryFoods;
import org.sopt.carena.diet.domain.value.DietChunk;
import org.sopt.carena.diet.domain.value.RecommendedFoods;
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
                .recommendedFoods(toRecommendedFoods(entity))
                .cautionaryFoods(toCautionaryFoods(entity))
                .build();
    }
    public DietChunk toDietChunk(DietChunkEntity chunkEntity) {
        return DietChunk.builder()
                .id(chunkEntity.getId())
                .section(chunkEntity.getSection())
                .content(chunkEntity.getContent())
                .chunkOrder(chunkEntity.getChunkOrder())
                .embeddingText(chunkEntity.getEmbeddingText())
                .embedding(chunkEntity.getEmbedding())
                .build();
    }

    private static  RecommendedFoods toRecommendedFoods(DietInformationEntity entity) {
        if (entity.getRecommendedFood() == null) {
            return new RecommendedFoods(Map.of());
        }
        return new RecommendedFoods(entity.getRecommendedFood().getCategories());
    }

    private static  CautionaryFoods toCautionaryFoods(DietInformationEntity entity) {
        if (entity.getCautionaryFood() == null) {
            return new CautionaryFoods(List.of());
        }
        return new CautionaryFoods(entity.getCautionaryFood().getCautionary());
    }
}