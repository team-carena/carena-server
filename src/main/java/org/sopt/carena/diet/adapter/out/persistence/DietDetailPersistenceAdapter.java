package org.sopt.carena.diet.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.sopt.carena.diet.adapter.out.persistence.entity.DietInformationEntity;
import org.sopt.carena.diet.adapter.out.persistence.repository.DietInformationJpaRepository;
import org.sopt.carena.diet.application.port.out.LoadDietDetailPort;
import org.sopt.carena.diet.domain.value.DietDetail;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DietDetailPersistenceAdapter implements LoadDietDetailPort {

    private final DietInformationJpaRepository repository;

    @Override
    public Optional<DietDetail> loadById(Long dietId) {
        return repository.findById(dietId)
                .map(this::toDomain);
    }

    private DietDetail toDomain(final DietInformationEntity entity) {

        Map<String, List<String>> recommendedCategories = entity.getRecommendedFood() != null
                ? entity.getRecommendedFood().getCategories()
                : Map.of();

        List<String> cautionaryFoods = entity.getCautionaryFood() != null
                ? entity.getCautionaryFood().getCautionary()
                : List.of();

        return new DietDetail(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                recommendedCategories,
                cautionaryFoods,
                entity.getReference()
        );
    }
}