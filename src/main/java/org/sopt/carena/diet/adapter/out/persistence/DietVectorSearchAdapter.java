package org.sopt.carena.diet.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.diet.application.port.out.LoadDietChunksByVectorPort;
import org.sopt.carena.diet.domain.value.DietChunkSimilarity;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class DietVectorSearchAdapter implements LoadDietChunksByVectorPort {

    private final VectorStore vectorStore;

    @Override
    public List<DietChunkSimilarity> findSimilarChunks(
            final String embeddingText,
            final int topK
    ) {
        List<Document> documents = vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(embeddingText)
                        .topK(topK)
                        .build()
        );
        documents.forEach(doc -> {
            log.debug("doc metadata = {}", doc.getMetadata());
        });

        // Document → DietChunkSimilarity 변환
        List<DietChunkSimilarity> results = documents.stream()
                .map(this::toChunkSimilarity)
                .filter(chunk -> chunk != null)
                .collect(Collectors.toList());

        log.debug("VectorStore 검색 완료 - 결과: {} 개", results.size());

        return results;
    }

    /**
     * Spring AI Document → Domain Value Object 변환
     */
    private DietChunkSimilarity toChunkSimilarity(final Document doc) {
        try {
            Long dietId = extractLong(doc.getMetadata().get("document_id"));

            if (dietId == null) {
                log.warn("document_id가 없는 Document 발견");
                return null;
            }

            // 코사인으로 Distance → Similarity 변환
            Double distance = extractDouble(doc.getMetadata().get("distance"));
            if (distance == null) {
                log.warn("distance가 없는 Document 발견");
                return null;
            }
            double similarity = 1.0 - distance;
            similarity = Math.max(0.0, Math.min(1.0, similarity));

            String section = (String) doc.getMetadata().get("section");

            return new DietChunkSimilarity(dietId, similarity, section);
        } catch (Exception e) {
            log.warn("Document 변환 실패", e);
            return null;
        }
    }

    private Long extractLong(final Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).longValue();
        }
        if (obj instanceof String) {
            try {
                return Long.parseLong((String) obj);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    private Double extractDouble(final Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).doubleValue();
        }
        return null;
    }
}