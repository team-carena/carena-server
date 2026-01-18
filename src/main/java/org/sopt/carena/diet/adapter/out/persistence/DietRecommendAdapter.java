/*
package org.sopt.carena.diet.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.sopt.carena.diet.adapter.out.persistence.entity.DietRecommendEntity;
import org.sopt.carena.diet.adapter.out.persistence.mapper.DietRecommendMapper;
import org.sopt.carena.diet.adapter.out.persistence.repository.DietRecommendRepository;
import org.sopt.carena.diet.application.port.out.LoadDietRecommendPort;
import org.sopt.carena.diet.application.port.out.SaveDietRecommendPort;
import org.sopt.carena.diet.domain.DietRecommend;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DietRecommendAdapter implements SaveDietRecommendPort, LoadDietRecommendPort {

    private final DietRecommendRepository repository;
    private final DietRecommendMapper mapper;

    @Override
    public DietRecommend save(DietRecommend dietRecommend) {
        DietRecommendEntity entity = mapper.toEntity(dietRecommend);
        DietRecommendEntity saved = repository.save(entity);
        return mapper.toDomain(saved);  // 도메인 반환!
    }

    @Override
    public Optional<DietRecommend> findLatestByMemberId(Long memberId) {
        return repository.findTopByMemberIdOrderByCreatedAtDesc(memberId)
                .map(mapper::toDomain);  // 도메인 반환!
    }
}*/
