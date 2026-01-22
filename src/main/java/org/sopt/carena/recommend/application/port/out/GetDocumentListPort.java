package org.sopt.carena.recommend.application.port.out;

import java.util.List;

import org.sopt.carena.diet.domain.DietInformation;

public interface GetDocumentListPort {
	List<DietInformation> searchDocumentsId(String embeddingText, int limit);
}
