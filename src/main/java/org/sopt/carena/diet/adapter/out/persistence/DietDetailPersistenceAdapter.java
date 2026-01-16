package org.sopt.carena.diet.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.sopt.carena.diet.adapter.out.persistence.entity.DietInformationEntity;
import org.sopt.carena.diet.adapter.out.persistence.repository.DietInformationJpaRepository;
import org.sopt.carena.diet.application.port.out.LoadDietDetailPort;
import org.sopt.carena.diet.domain.DietDetail;
import org.springframework.stereotype.Component;
import java.util.List;
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

    private DietDetail toDomain(DietInformationEntity entity) {

        List<String> recommends = entity.getRecommends() != null
                ? entity.getRecommends()
                : List.of();

        List<String> cautionary = entity.getCautionary() != null
                ? entity.getCautionary()
                : List.of();

        return new DietDetail(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                recommends,
                cautionary,
                entity.getReference()
        );
    }
}