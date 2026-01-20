package org.sopt.carena.diet.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.diet.application.dto.command.CreateDietCommand;
import org.sopt.carena.diet.application.port.in.RegisterDietDocumentUseCase;
import org.sopt.carena.diet.application.port.out.DietPersistencePort;
import org.sopt.carena.diet.application.port.out.EmbeddingPort;
import org.sopt.carena.diet.domain.value.*;
import org.sopt.carena.diet.domain.DietInformation;
import org.sopt.carena.diet.exception.embedding.EmbeddingFailedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterDietDocumentService
        implements RegisterDietDocumentUseCase {

    private final DietEmbeddingTextService dietEmbeddingTextService;
    private final DietPersistencePort dietPersistencePort;
    private final EmbeddingPort embeddingPort;
    @Override
    @Transactional
    public void register(final CreateDietCommand command) {
        try {
            // 1. 도메인 생성
            DietInformation document = toDomain(command);
            List<DietChunk> chunks = document.getChunks();
            String documentTitle = document.getTitle();

            log.info("식단 정보를 등록합니다: {}", documentTitle);

            // 2. 임베딩 텍스트 생성
            List<String> embeddingTexts = chunks.stream()
                    .map(chunk -> dietEmbeddingTextService.generate(chunk, documentTitle))
                    .toList();

            log.debug("생성된 임베딩 텍스트 개수: {}", embeddingTexts.size());

            // 3. 임베딩 벡터 생성
            log.info("임베딩 생성 중 (Texts: {}개)", embeddingTexts.size());
            List<EmbeddingVector> embeddings = embeddingPort.embedBatch(embeddingTexts);
            log.info("생성된 임베딩 정보:");
            log.info("  - 개수: {}", embeddings.size());
            log.info("  - 차원: {}", embeddings.isEmpty() ? 0 : embeddings.get(0).vector().length);

            // 4. 임베딩 할당
            for (int i = 0; i < chunks.size(); i++) {
                chunks.get(i).assignEmbedding(
                        embeddingTexts.get(i),
                        embeddings.get(i).vector()
                );
                log.debug("Chunk {} 임베딩 할당 완료 (섹션: {})",
                        i + 1, chunks.get(i).getSection());
            }

            // 5. Information 먼저 저장 후 ID 획득
            Long documentId = dietPersistencePort.saveInformation(document);
            log.info("DietInformation 저장 완료 - ID: {}", documentId);

            // 6. Application에서 메타데이터 생성 및 할당
            Map<String, Object> metadata = createChunkMetadata(documentId, documentTitle);
            chunks.forEach(chunk -> chunk.assignMetadata(metadata));
            log.debug("청크 메타데이터 생성 완료");

            // 7. 청크 + 카테고리 한 번에 저장
            dietPersistencePort.saveChunksAndCategories(
                    documentId,
                    chunks,
                    command.recommendedCategories(),
                    command.cautionaryFoods()
            );
            log.info("식단 정보 등록 성공: {}", documentTitle);
        } catch (Exception e) {
            log.error("식단 정보 등록 실패", e);
            throw new EmbeddingFailedException();
        }
    }

    /**
     * 청크 메타데이터 생성
     */
    private Map<String, Object> createChunkMetadata(Long documentId, String title) {
        return Map.of(
                "document_id", documentId,
                "title", title
        );
    }

    private DietInformation toDomain(final CreateDietCommand command) {
        List<DietChunk> chunks = command.chunks().stream()
                .map(this::toDomain)
                .toList();
        RecommendedFoods recommendedFoods =
                new RecommendedFoods(command.recommendedCategories());
        CautionaryFoods cautionaryFoods =
                new CautionaryFoods(command.cautionaryFoods());

        return DietInformation.builder()
                .title(command.title())
                .content(command.content())
                .reference(command.reference())
                .referenceUrl(command.referenceUrl())
                .chunks(chunks)
                .recommendedFoods(recommendedFoods)
                .cautionaryFoods(cautionaryFoods)
                .createdAt(LocalDateTime.now())
                .build();
    }

    private DietChunk toDomain(final CreateDietCommand.DietChunkCommand chunkCommand) {
        return DietChunk.builder()
                .section(DietSection.from(chunkCommand.section()))
                .content(chunkCommand.content())
                .chunkOrder(chunkCommand.chunkOrder())
                .build();

    }
}