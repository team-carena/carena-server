package org.sopt.carena.diet.application.service;

import org.sopt.carena.diet.domain.DietChunk;
import org.sopt.carena.diet.domain.DietSection;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DietContentExtractor {

    /**
     * 청크에서 본문(content) 추출
     */
    public String extractContent(List<DietChunk> chunks) {
        return chunks.stream()
                .filter(chunk -> chunk.getSection() == DietSection.NECESSITY)
                .map(DietChunk::getContent)
                .collect(Collectors.joining("\n\n"));
    }

    /**
     * 청크에서 추천 식단 추출
     */
    public List<String> extractRecommends(List<DietChunk> chunks) {

        return chunks.stream()
                .filter(chunk -> chunk.getSection() == DietSection.RECOMMENDED_FOOD)
                .map(DietChunk::getContent)
                .flatMap(content -> parseItems(content).stream())
                .toList();

    }

    /**
     * 청크에서 주의 식품 추출
     */
    public List<String> extractCautionary(List<DietChunk> chunks) {
        return chunks.stream()
                .filter(chunk -> chunk.getSection() == DietSection.CAUTION_FOOD)
                .map(DietChunk::getContent)
                .flatMap(content -> parseItems(content).stream())
                .toList();
    }

    /**
     * 쉼표로 구분된 항목들을 파싱
     */
    private List<String> parseItems(String content) {
        if (content == null || content.isBlank()) {
            return List.of();
        }

        List<String> items = new ArrayList<>();
        StringBuilder currentItem = new StringBuilder();
        int parenthesesDepth = 0;
        for (char c : content.toCharArray()) {
            if (c == '(' || c == '（') {
                parenthesesDepth++;
                currentItem.append(c);
            } else if (c == ')' || c == '）') {
                parenthesesDepth--;
                currentItem.append(c);
            } else if ((c == ',' || c == '、') && parenthesesDepth == 0) {
                // 괄호 밖의 쉼표만 구분자로 인식
                String item = currentItem.toString().trim();
                if (!item.isEmpty()) {
                    items.add(item);
                }
                currentItem = new StringBuilder();
            } else {
                currentItem.append(c);
            }
        }

        // 마지막 항목 추가
        String lastItem = currentItem.toString().trim();
        if (!lastItem.isEmpty()) {
            items.add(lastItem);
        }

        return items;
    }
}