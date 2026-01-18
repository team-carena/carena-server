package org.sopt.carena.diet.application.service;

import lombok.RequiredArgsConstructor;
import org.sopt.carena.diet.application.dto.view.DietDetailResultView;
import org.sopt.carena.diet.application.port.in.GetDietDetailUseCase;
import org.sopt.carena.diet.application.port.out.DietPersistencePort;
import org.sopt.carena.diet.domain.DietInformation;
import org.sopt.carena.diet.exception.diet.DietNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DietDetailService implements GetDietDetailUseCase {

    private final DietPersistencePort dietPersistencePort;

    @Override
    public DietDetailResultView getDietDetail(final Long dietId) {
        DietInformation dietInformation = dietPersistencePort.loadById(dietId).orElseThrow(DietNotFoundException::new);
        return new DietDetailResultView(
                dietInformation.getId(),
                dietInformation.getTitle(),
                dietInformation.getContent(),
                dietInformation.getRecommendedFoods().categories(),
                dietInformation.getCautionaryFoods().foods(),
                dietInformation.getReference()
        );
    }
}