package org.sopt.carena.diet.application.service.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.diet.domain.DietChunk;
import org.sopt.carena.diet.exception.embedding.CreateEmbeddingTextFailedException;
import org.sopt.carena.diet.exception.embedding.EmbeddingTextNullException;
import org.springframework.util.StringUtils;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DietEmbeddingTextUtil  {

    public static String generate(final DietChunk chunk,final String documentTitle) {

        try {
            String normalizedTitle = removeDietSuffix(documentTitle);
            StringBuilder sb = new StringBuilder();
            sb.append(sectionSentence(chunk,normalizedTitle));
            String result = normalize(sb.toString());

            if (!StringUtils.hasText(result)) {
                log.error("임베딩 텍스트 생성 결과가 비어있습니다. documentTitle: {}, section: {}",
                        documentTitle, chunk.getSection());
                throw new EmbeddingTextNullException();
            }
            return result;

        } catch (EmbeddingTextNullException e) {
            throw e;
        } catch (Exception e) {
            log.error("임베딩 텍스트 생성 중 예외 발생. {}",e.getMessage());
            throw new CreateEmbeddingTextFailedException();
        }
    }

    private static String sectionSentence(final DietChunk chunk, final String normalizedTitle) {
        return switch (chunk.getSection()) {
            case NECESSITY ->
                    normalizedTitle + "은" + chunk.getContent();
            case PRACTICE ->
                    normalizedTitle + "의 식사요법 실제 내용은 다음과 같습니다. " + chunk.getContent();
            case EXTRA_CAUTION ->
                    normalizedTitle + "의 그외 주의사항은 " + chunk.getContent() ;
        };
    }

    private static String normalize(final String text) {
        return text.replaceAll("\\s+", " ").trim();
    }

    private static String removeDietSuffix(final String title) {
        if (title == null || title.isEmpty()) {
            return title;
        }
        // '식' 제거
        if (title.endsWith("식")) {
            return title.substring(0, title.length() - 1);
        }
        return title;
    }
}