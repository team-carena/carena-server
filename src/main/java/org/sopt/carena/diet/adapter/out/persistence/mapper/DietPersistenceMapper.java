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

        List<DietChunk> chunks = entity.getChunks().stream()
                .map(this::toDietChunk)
                .toList();

        Map<String, List<String>> recommendedCategories = entity.getRecommendedFood() != null
                ? entity.getRecommendedFood().getCategories()
                : Map.of();

        List<String> cautionaryFoods = entity.getCautionaryFood() != null
                ? entity.getCautionaryFood().getCautionary()
                : List.of();

        return DietInformation.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .reference(entity.getReference())
                .referenceUrl(entity.getReferenceUrl())
                .chunks(chunks)
                .recommendedCategories(recommendedCategories)
                .cautionaryFoods(cautionaryFoods)
                .build();
    }
    public DietInformation toDomainWithoutChunks(DietInformationEntity entity) {
        return DietInformation.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .reference(entity.getReference())
                .referenceUrl(entity.getReferenceUrl())
                .chunks(List.of())  // 빈 리스트
                .recommendedCategories(Map.of())  // 빈 맵
                .cautionaryFoods(List.of())  // 빈 리스트
                .build();
    }
    public DietChunk toDietChunk(DietChunkEntity chunkEntity) {
        // DietChunk 도메인 객체로 변환
        return new DietChunk(
                chunkEntity.getSection(),
                chunkEntity.getContent(),
                chunkEntity.getChunkOrder(),
                chunkEntity.getEmbeddingText(),
                chunkEntity.getEmbedding()
        );
    }
}