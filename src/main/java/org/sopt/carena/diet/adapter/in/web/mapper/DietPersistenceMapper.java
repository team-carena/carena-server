package org.sopt.carena.diet.adapter.in.web.mapper;

import org.sopt.carena.diet.adapter.out.persistence.entity.DietInformationEntity;
import org.sopt.carena.diet.domain.value.DietSummary;
import org.springframework.stereotype.Component;

@Component
public class DietPersistenceMapper {

    public DietSummary toDietSummary(DietInformationEntity entity) {
        return new DietSummary(
                entity.getId(),
                entity.getTitle()
        );
    }
}
