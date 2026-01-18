package org.sopt.carena.diet.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.diet.application.dto.command.CreateDietCommand;
import org.sopt.carena.diet.application.port.in.RegisterDietDocumentUseCase;
import org.sopt.carena.diet.application.port.out.DietPersistencePort;
import org.sopt.carena.diet.application.port.out.EmbeddingPort;
import org.sopt.carena.diet.domain.value.EmbeddingVector;
import org.sopt.carena.diet.domain.value.DietChunk;
import org.sopt.carena.diet.domain.DietInformation;
import org.sopt.carena.diet.domain.value.DietSection;
import org.sopt.carena.diet.exception.embedding.EmbeddingFailedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterDietDocumentService
        implements RegisterDietDocumentUseCase {

    private final DietEmbeddingTextService textGenerator;
    private final DietPersistencePort dietPersistencePort;
    private final DietContentExtractor contentExtractor;
    private final EmbeddingPort embeddingPort;

    @Override
    @Transactional
    public void register(final CreateDietCommand command) {

       try {
           DietInformation document = toDomain(command);
           String content = document.getContent();
           List<DietChunk> chunks = document.getChunks();

           String documentTitle = command.title();

           log.info("식단 정보를 등록합니다. " + documentTitle);

           // 모든 chunk의 임베딩 텍스트 생성
           List<String> embeddingTexts = chunks.stream()
                   .map(chunk -> textGenerator.generate(chunk, documentTitle))
                   .toList();

           log.debug("생성된 임베딩 텍스트:");
           embeddingTexts.forEach(text ->
                   log.debug("  - {}", text.substring(0, Math.min(100, text.length())) + "...")
           );


           log.info("Step 2: 임베딩 생성 중 (Texts: {}개)", embeddingTexts.size());
           // 배치로 임베딩
           List<EmbeddingVector> embeddings = embeddingPort.embedBatch(embeddingTexts);
           log.info("생성된 임베딩 정보:");
           log.info("  - 개수: {}", embeddings.size());
           log.info("  - 차원: {}", embeddings.isEmpty() ? 0 : embeddings.get(0).vector().length);

           // 임베딩 할당
           for (int i = 0; i < chunks.size(); i++) {
               chunks.get(i).assignEmbedding(embeddingTexts.get(i), embeddings.get(i).vector());
               log.debug("Chunk {} 임베딩 할당 완료 (섹션: {})",
                       i + 1, chunks.get(i).getSection());
           }

           log.info("Embedding 생성: " + embeddings);
           //String content = contentExtractor.extractContent(chunks);
           dietPersistencePort.save(document, chunks, content, command.recommendedCategories(), command.cautionaryFoods());

           log.info("식단 정보 등록 성공: {} with ", documentTitle);
       } catch (Exception e) {
           throw new EmbeddingFailedException();
       }
    }

    private DietInformation toDomain(final CreateDietCommand command) {
        List<DietChunk> chunks = command.chunks().stream()
                .map(this::toDomain)
                .toList();

        return DietInformation.create(
                command.title(),
                command.content(),
                command.reference(),
                command.referenceUrl(),
                chunks,
                command.recommendedCategories(),
                command.cautionaryFoods()
        );
    }

    private DietChunk toDomain(final CreateDietCommand.DietChunkCommand chunkCommand) {
        return new DietChunk(
                DietSection.from(chunkCommand.section()),
                chunkCommand.content(),
                chunkCommand.chunkOrder()
        );
    }
}