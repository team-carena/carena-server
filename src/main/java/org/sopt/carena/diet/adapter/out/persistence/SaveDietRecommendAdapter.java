package org.sopt.carena.diet.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.sopt.carena.diet.adapter.out.persistence.entity.DietRecommendEntity;
import org.sopt.carena.diet.adapter.out.persistence.mapper.DietRecommendMapper;
import org.sopt.carena.diet.adapter.out.persistence.repository.DietRecommendRepository;
import org.sopt.carena.diet.application.port.out.SaveDietRecommendPort;
import org.sopt.carena.diet.domain.DietRecommend;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SaveDietRecommendAdapter implements SaveDietRecommendPort {

    private final DietRecommendRepository repository;
    private final DietRecommendMapper mapper;

    @Override
    public DietRecommend save(DietRecommend dietRecommend) {
        DietRecommendEntity entity = mapper.toEntity(dietRecommend);
        DietRecommendEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public List<DietRecommend> saveAll(List<DietRecommend> dietRecommends) {
        List<DietRecommendEntity> entities = dietRecommends.stream()
                .map(mapper::toEntity)
                .toList();

        List<DietRecommendEntity> saved = repository.saveAll(entities);

        return saved.stream()
                .map(mapper::toDomain)
                .toList();
    }
}