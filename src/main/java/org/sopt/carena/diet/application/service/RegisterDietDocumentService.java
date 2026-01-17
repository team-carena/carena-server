package org.sopt.carena.diet.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.diet.application.dto.command.CreateDietCommand;
import org.sopt.carena.diet.application.port.in.RegisterDietDocumentUseCase;
import org.sopt.carena.diet.application.port.out.DietPersistencePort;
import org.sopt.carena.diet.application.port.out.EmbeddingClient;
import org.sopt.carena.diet.domain.DietChunk;
import org.sopt.carena.diet.domain.DietInformation;
import org.sopt.carena.diet.domain.DietSection;
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
    private final EmbeddingClient embeddingClient;  // 배치용
    private final DietContentExtractor contentExtractor;

    @Override
    @Transactional
    public void register(final CreateDietCommand command) {

       try {
           DietInformation document = toDomain(command);
           List<DietChunk> chunks = document.getChunks();

           String documentTitle = command.getTitle();

           log.info("식단 정보를 등록합니다. " + documentTitle);

           // 모든 chunk의 임베딩 텍스트 생성
           List<String> embeddingTexts = chunks.stream()
                   .map(chunk -> textGenerator.generate(chunk, documentTitle))
                   .toList();
           log.info("Embedding texts: " + embeddingTexts);

           // 배치로 임베딩
           List<float[]> embeddings = embeddingClient.embedBatch(embeddingTexts)
                   .getResults()
                   .stream()
                   .map(result -> result.getOutput())
                   .toList();
           log.info("Embedding: " + embeddings);

           // 임베딩 할당
           for (int i = 0; i < chunks.size(); i++) {
               chunks.get(i).assignEmbedding(
                       embeddingTexts.get(i),
                       embeddings.get(i)
               );
           }

           log.info("Embedding 생성: " + embeddings);
           String content = contentExtractor.extractContent(chunks);
           dietPersistencePort.save(document, chunks, content, command.getRecommendedFoods(), command.getCautionaryFoods());

           log.info("식단 정보 등록 성공: {} with ", documentTitle);
       } catch (Exception e) {
           throw new EmbeddingFailedException();
       }
    }

    private DietInformation toDomain(final CreateDietCommand command) {
        List<DietChunk> chunks = command.getChunks().stream()
                .map(this::toDomain)
                .toList();

        return DietInformation.create(
                command.getTitle(),
                command.getReference(),
                command.getReferenceUrl(),
                chunks
        );
    }

    private DietChunk toDomain(final CreateDietCommand.DietChunkCommand chunkCommand) {
        return new DietChunk(
                DietSection.from(chunkCommand.getSection()),
                chunkCommand.getContent(),
                chunkCommand.getChunkOrder()
        );
    }
}