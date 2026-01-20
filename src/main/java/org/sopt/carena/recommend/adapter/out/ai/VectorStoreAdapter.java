package org.sopt.carena.recommend.adapter.out.ai;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.sopt.carena.recommend.application.port.out.GetDocumentListPort;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VectorStoreAdapter implements GetDocumentListPort {
	private final VectorStore vectorStore;

	public List<Document> searchDocuments(final String embeddingText, final int limit) {

		List<Document> docs = vectorStore.similaritySearch(
				SearchRequest.builder()
						.query(embeddingText)
						.topK(10)
						.build()
		);
		System.out.println("최초 유사도 검색 결과");

		docs.forEach(document -> System.out.println(document.getText()));

		System.out.println("==================");
		return docs.stream()
				.collect(Collectors.toMap(
						d -> d.getMetadata().get("document_id"),
						Function.identity(),
						(existing, duplicate) -> existing,
						LinkedHashMap::new
				))
				.values()
				.stream()
				.limit(limit)
				.toList();
	}
}
