package org.sopt.carena.diet.application.service;


import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.diet.application.port.in.EmbeddingTextGenerateUseCase;
import org.sopt.carena.diet.domain.DietChunk;
import org.sopt.carena.diet.exception.enbedding.CreateEmbeddingTextFailedException;
import org.sopt.carena.diet.exception.enbedding.EmbeddingTextNullException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class DietEmbeddingTextService implements EmbeddingTextGenerateUseCase {

    @Override
    public String generate(final DietChunk chunk,final String documentTitle) {

        try {
            StringBuilder sb = new StringBuilder();
            sb.append(sectionSentence(chunk));

            String result = normalize(sb.toString());

            if (!StringUtils.hasText(result)) {
                log.error("임베딩 텍스트 생성 결과가 비어있습니다. documentTitle: {}, section: {}",
                        documentTitle, chunk.getSection());
                throw new EmbeddingTextNullException();
            }
            return result;

        } catch (Exception e) {
            log.error("임베딩 텍스트 생성 중 예외 발생. {}",e.getMessage());
            throw new CreateEmbeddingTextFailedException();
        }
    }

    private String sectionSentence(final DietChunk chunk) {
        return switch (chunk.getSection()) {
            case RECOMMENDED_FOOD ->
                    "권장되는 음식으로는 " + chunk.getContent() + " 이 포함됩니다.";
            case CAUTION_FOOD ->
                    "섭취 시 주의가 필요한 음식으로는 " + chunk.getContent() + " 등이 있습니다.";
            case NECESSITY ->
                    chunk.getContent();
            case PRACTICE ->
                    "식사요법의 실제 내용은 다음과 같습니다. " + chunk.getContent();
            case EXTRA_CAUTHION ->
                "그외 주의사항은" + chunk.getContent() ;
        };
    }

    private String normalize(final String text) {
        return text.replaceAll("\\s+", " ").trim();
    }
}