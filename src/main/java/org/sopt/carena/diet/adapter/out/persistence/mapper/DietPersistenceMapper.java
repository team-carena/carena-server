package org.sopt.carena.diet.adapter.out.persistence.mapper;

import org.sopt.carena.diet.adapter.out.persistence.entity.DietInformationEntity;
import org.sopt.carena.diet.domain.value.DietSummary;
import org.springframework.stereotype.Component;

@Component
public class DietPersistenceMapper {

    public DietSummary toDomain(DietInformationEntity entity) {
        return new DietSummary(
                entity.getId(),
                entity.getTitle()
        );
    }
}
