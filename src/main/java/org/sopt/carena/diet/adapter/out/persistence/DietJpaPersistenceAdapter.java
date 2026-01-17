package org.sopt.carena.diet.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.diet.adapter.out.persistence.entity.CautionCategoryEntity;
import org.sopt.carena.diet.adapter.out.persistence.entity.DietChunkEntity;
import org.sopt.carena.diet.adapter.out.persistence.entity.DietInformationEntity;
import org.sopt.carena.diet.adapter.out.persistence.entity.RecommendedCategoryEntity;
import org.sopt.carena.diet.adapter.out.persistence.repository.DietChunkJpaRepository;
import org.sopt.carena.diet.adapter.out.persistence.repository.DietInformationJpaRepository;
import org.sopt.carena.diet.application.port.out.DietPersistencePort;
import org.sopt.carena.diet.domain.DietChunk;
import org.sopt.carena.diet.domain.DietInformation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class DietJpaPersistenceAdapter implements DietPersistencePort {

    private final DietInformationJpaRepository infoRepository;

    @Override
    public void save(final DietInformation info, final List<DietChunk> chunks,
                     final String content,
                     final Map<String, List<String>> recommends,
                     final List<String> cautionary) {

        DietInformationEntity infoEntity = new DietInformationEntity(
                info.getTitle(),
                content,
                info.getReference(),
                info.getReferenceUrl()
        );

        // DietChunk
        List<DietChunkEntity> chunkEntities = chunks.stream()
                .map(chunk -> new DietChunkEntity(
                        infoEntity,
                        chunk.getSection(),
                        chunk.getContent(),
                        chunk.getEmbeddingText(),
                        chunk.getEmbedding(),
                        chunk.getChunkOrder()
                ))
                .toList();
        // 양방향 관계 설정
        infoEntity.addAllChunks(chunkEntities);

        // RecommendedCategory
        if (recommends != null && !recommends.isEmpty()) {
            RecommendedCategoryEntity recommendedEntity = new RecommendedCategoryEntity(
                    infoEntity,
                    recommends
            );
            infoEntity.setRecommendedFood(recommendedEntity);
        }

        if (cautionary != null && !cautionary.isEmpty()) {
            CautionCategoryEntity cautionaryEntity = new CautionCategoryEntity(
                    infoEntity,
                    cautionary
            );
            infoEntity.setCautionaryFood(cautionaryEntity);
        }

        infoRepository.save(infoEntity);
    }
}