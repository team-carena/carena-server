package org.sopt.carena.diet.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.diet.application.port.in.RegisterDietDocumentUseCase;
import org.sopt.carena.diet.application.port.out.DietPersistencePort;
import org.sopt.carena.diet.application.port.out.EmbeddingClient;
import org.sopt.carena.diet.domain.DietChunk;
import org.sopt.carena.diet.domain.DietInformation;
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
    public void register(DietInformation document) {

        String documentTitle = document.getTitle();
        List<DietChunk> chunks = document.getChunks();

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
        var recommends = contentExtractor.extractRecommends(chunks);
        List<String> cautionary = contentExtractor.extractCautionary(chunks);
        log.info("Extracted - Content length: {}, Recommends: {}, Cautionary: {}",
                content.length(), recommends.size(), cautionary.size());

        dietPersistencePort.save(document, chunks,content, recommends, cautionary);

        log.info("식단 정보 등록 성공t: {} with ", documentTitle);
    }
}