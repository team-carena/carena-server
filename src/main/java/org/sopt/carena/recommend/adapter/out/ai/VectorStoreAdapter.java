package org.sopt.carena.recommend.adapter.out.ai;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.sopt.carena.diet.adapter.out.persistence.mapper.DietPersistenceMapper;
import org.sopt.carena.diet.adapter.out.persistence.repository.DietInformationJpaRepository;
import org.sopt.carena.diet.domain.DietInformation;
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
	private final DietInformationJpaRepository dietInformationRepository;

	public List<DietInformation> searchDocumentsId(final String embeddingText, final int limit) {

		List<Document> docs = vectorStore.similaritySearch(
				SearchRequest.builder()
						.query(embeddingText)
						.topK(10)
						.build()
		);

		List<Long> documentIds = docs.stream()
				.map(d -> (Long)d.getMetadata().get("document_id"))
				.collect(Collectors.toCollection(LinkedHashSet::new))
				.stream()
				.limit(limit)
				.toList();
		System.out.println("===document id 추출===");
		documentIds.forEach(id -> System.out.print(id+" "));

		return dietInformationRepository.findAllByIdInWithDetails(documentIds).stream()
				.map(DietPersistenceMapper::toDomain)
				.toList();
	}
}
