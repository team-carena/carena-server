package org.sopt.carena.recommend.application.port.out;

import java.util.List;

import org.springframework.ai.document.Document;

public interface GetDocumentListPort {
	List<Document> searchDocuments(String embeddingText, int limit);
}
