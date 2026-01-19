package org.sopt.carena.diet.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.diet.adapter.out.persistence.entity.CautionCategoryEntity;
import org.sopt.carena.diet.adapter.out.persistence.entity.DietChunkEntity;
import org.sopt.carena.diet.adapter.out.persistence.entity.DietInformationEntity;
import org.sopt.carena.diet.adapter.out.persistence.entity.RecommendedCategoryEntity;
import org.sopt.carena.diet.adapter.out.persistence.mapper.DietPersistenceMapper;
import org.sopt.carena.diet.adapter.out.persistence.repository.DietChunkJpaRepository;
import org.sopt.carena.diet.adapter.out.persistence.repository.DietInformationJpaRepository;
import org.sopt.carena.diet.application.port.out.DietPersistencePort;
import org.sopt.carena.diet.domain.value.DietChunk;
import org.sopt.carena.diet.domain.DietInformation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class DietJpaPersistenceAdapter implements DietPersistencePort {

    private final DietInformationJpaRepository infoRepository;
    private final DietPersistenceMapper mapper;
    private final DietChunkJpaRepository dietChunkRepository;

    @Override
    @Transactional
    public Long saveInformation(final DietInformation info) {
        // DietInformation만 저장 (청크 제외)
        DietInformationEntity infoEntity = new DietInformationEntity(
                info.getTitle(),
                info.getContent(),
                info.getReference(),
                info.getReferenceUrl()
        );

        infoEntity = infoRepository.save(infoEntity);

        log.debug("DietInformation 저장 완료 - ID: {}", infoEntity.getId());
        return infoEntity.getId();
    }

    @Override
    @Transactional
    public void saveChunksAndCategories(
            final Long documentId,
            final List<DietChunk> chunks,
            final Map<String, List<String>> recommends,
            final List<String> cautionary) {

        // DietInformation 조회
        DietInformationEntity infoEntity = infoRepository.findById(documentId)
                .orElseThrow(() -> new IllegalArgumentException("Document not found: " + documentId));

        //  청크 엔티티 생성
        List<DietChunkEntity> chunkEntities = chunks.stream()
                .map(chunk -> new DietChunkEntity(
                        infoEntity,
                        chunk.getSection(),
                        chunk.getContent(),
                        chunk.getEmbeddingText(),
                        chunk.getEmbedding(),
                        chunk.getChunkOrder(),
                        chunk.getMetadata()
                ))
                .toList();

        // RecommendedCategory
        if (recommends != null && !recommends.isEmpty()) {
            RecommendedCategoryEntity recommendedEntity =
                    new RecommendedCategoryEntity(infoEntity, recommends);
            infoEntity.setRecommendedFood(recommendedEntity);
        }

        // CautionaryFoods
        if (cautionary != null && !cautionary.isEmpty()) {
            CautionCategoryEntity cautionaryEntity =
                    new CautionCategoryEntity(infoEntity, cautionary);
            infoEntity.setCautionaryFood(cautionaryEntity);
        }
        dietChunkRepository.saveAll(chunkEntities);

        log.debug("청크 및 카테고리 저장 완료 - 청크 개수: {}", chunkEntities.size());
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<DietInformation> loadById(Long dietId) {
        return infoRepository.findById(dietId)
                .map(mapper::toDomain);
    }

    /**
     * ID 목록으로 식단 정보 조회
     */
    @Override
    @Transactional(readOnly = true)
    public Map<Long, DietInformation> findAllByIds(List<Long> ids) {
        // 빈 리스트 처리
        if (ids == null || ids.isEmpty()) {
            log.warn("빈 ID 목록으로 조회 시도");
            return Collections.emptyMap();
        }

        log.debug("식단 정보 일괄 조회 시작 - IDs: {}", ids);

        // Entity 조회
        List<DietInformationEntity> entities = infoRepository
                .findAllByIdInWithDetails(ids);
        Map<Long, DietInformation> dietMap = mapper.toMapById(entities);

        if (entities.isEmpty()) {
            return Collections.emptyMap();
        }
        return dietMap;
    }
}