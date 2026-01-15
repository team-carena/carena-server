package org.sopt.carena.diet.application.service;


import org.sopt.carena.diet.application.port.in.EmbeddingTextGenerateUseCase;
import org.sopt.carena.diet.domain.DietChunk;
import org.springframework.stereotype.Service;

@Service
public class DietEmbeddingTextService implements EmbeddingTextGenerateUseCase {

    @Override
    public String generate(DietChunk chunk,String documentTitle) {

        StringBuilder sb = new StringBuilder();
        // 섹션별 자연어 변환
        sb.append(sectionSentence(chunk));

        return normalize(sb.toString());
    }

    private String sectionSentence(DietChunk chunk) {
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

    private String normalize(String text) {
        return text.replaceAll("\\s+", " ").trim();
    }
}