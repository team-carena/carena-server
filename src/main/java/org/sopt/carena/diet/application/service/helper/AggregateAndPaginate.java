package org.sopt.carena.diet.application.service.helper;

import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.diet.domain.value.DietChunkSimilarity;
import org.sopt.carena.diet.domain.value.PagedDietSimilarity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


/*
* 청크 그룹화, 정렬, 페이징 등의 도메인 로직 처리 근데 이거 여기 맞음?
 */
@Slf4j
@Service
public class AggregateAndPaginate {

    /**
     * 유사도 정렬 + 중복 제거 + 페이징을 한 번에 처리
     *
     * 1. 유사도 내림차순 정렬
     * 2. dietInformationId 중복 제거 (첫 번째 = 최고 유사도만 유지)
     * 3. 페이징 적용
     */
    public PagedDietSimilarity processAndPaginate(
            final List<DietChunkSimilarity> chunks,
            final int offset,
            final int pageSize
    ) {
        if (chunks == null || chunks.isEmpty()) {
            log.debug("처리할 청크가 없습니다");
            return new PagedDietSimilarity(Collections.emptyList(), false);
        }

        //  유사도 내림차순 정렬 + 중복 제거 (한 번에)
        List<DietChunkSimilarity> deduplicatedAndSorted = chunks.stream()
                .sorted(Comparator.comparingDouble(DietChunkSimilarity::similarity)
                        .reversed())  // 유사도 높은 순
                .collect(Collectors.toMap(
                        DietChunkSimilarity::dietInformationId,  //  dietInformationId
                        Function.identity(),                      //chunk 자체
                        (first, second) -> first,     // 중복 시 첫 번째(최고 유사도) 유지
                        LinkedHashMap::new                        // 순서 유지
                ))
                .values()
                .stream()
                .toList();

        log.debug("정렬 및 중복 제거 완료 - 입력: {} 청크, 출력: {} DietInformation",
                chunks.size(), deduplicatedAndSorted.size());

        if (!deduplicatedAndSorted.isEmpty()) {
            log.debug("유사도 범위 - 최고: {:.3f}, 최저: {:.3f}",
                    deduplicatedAndSorted.get(0).similarity(),
                    deduplicatedAndSorted.get(deduplicatedAndSorted.size() - 1).similarity());
        }

        //  페이징 적용
        return applyPagination(deduplicatedAndSorted, offset, pageSize);
    }

    private PagedDietSimilarity applyPagination(
            final List<DietChunkSimilarity> sorted,
            final int offset,
            final int pageSize
    ) {
        if (offset >= sorted.size()) {
            log.debug("페이지 범위 초과 - offset: {}, total: {}", offset, sorted.size());
            return new PagedDietSimilarity(Collections.emptyList(), false);
        }

        int end = Math.min(offset + pageSize + 1, sorted.size());
        List<DietChunkSimilarity> sliced = sorted.subList(offset, end);

        boolean hasNext = sliced.size() > pageSize;
        List<DietChunkSimilarity> content = hasNext
                ? sliced.subList(0, pageSize)
                : sliced;

        log.debug("페이지네이션 완료 - offset: {}, size: {}, hasNext: {}",
                offset, content.size(), hasNext);

        return new PagedDietSimilarity(content, hasNext);
    }
}