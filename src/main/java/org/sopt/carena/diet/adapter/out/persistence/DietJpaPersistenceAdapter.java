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
import org.sopt.carena.diet.adapter.out.persistence.repository.DietVectorSearchResult;
import org.sopt.carena.diet.application.port.out.DietPersistencePort;
import org.sopt.carena.diet.domain.DietChunk;
import org.sopt.carena.diet.domain.DietInformation;
import org.sopt.carena.healthreport.exception.healthreport.HealthReportEmbeddingNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.*;
import java.util.function.Function;
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
                info.getReferenceUrl(),
                info.getCreatedAt()
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
    public Optional<DietInformation> loadById(final Long dietId) {
        return infoRepository.findById(dietId)
                .map(mapper::toDomain);
    }

    @Override
    public Slice<DietInformation> loadDietList(final Pageable pageable) {
        return infoRepository.findAllByOrderByIdDesc(pageable)
                .map(mapper::toDomain);
    }
    @Override
    public Slice<DietInformation> loadDietsByVectorSimilarity(final float[] embeddingVector, final int page, final int pageSize) {
        log.debug("벡터 유사도 기반 식단 조회 - page: {}, pageSize: {}", page, pageSize);

        String vectorString = convertToVectorString(embeddingVector);
        int offset = (page - 1) * pageSize;

        // 벡터 검색
        List<DietVectorSearchResult> rawResults = dietChunkRepository.findSimilarDietsByVector(vectorString, pageSize+1, offset);

        if (rawResults.isEmpty()) {
            return new SliceImpl<>(List.of(), PageRequest.of(page - 1, pageSize), false);
        }
        boolean hasNext = rawResults.size() > pageSize;

        List<DietVectorSearchResult> actualResults = hasNext
                ? rawResults.subList(0, pageSize)
                : rawResults;

        List<Long> dietIds = actualResults.stream()
                .map(DietVectorSearchResult::getDietInformationId)
                .toList();


        Map<Long, DietInformationEntity> dietEntityMap =
                infoRepository.findAllById(dietIds).stream()
                        .collect(Collectors.toMap(
                                DietInformationEntity::getId,
                                Function.identity()
                        ));

        //  Entity → 도메인 변환
        List<DietInformation> diets = dietIds.stream()
                .map(dietEntityMap::get)
                .filter(entity -> entity != null)
                .map(mapper::toDomain)
                .toList();

        log.debug("벡터 유사도 식단 조회 완료 - 결과: {}개, hasNext: {}", diets.size(), hasNext);

        return new SliceImpl<>(diets, PageRequest.of(page - 1, pageSize), hasNext);
    }

    private String convertToVectorString(float[] embedding) {
        if (embedding == null || embedding.length == 0) {
            throw new HealthReportEmbeddingNotFoundException();
        }
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < embedding.length; i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(embedding[i]);
        }
        sb.append("]");
        return sb.toString();
    }
}