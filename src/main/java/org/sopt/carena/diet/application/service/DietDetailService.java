package org.sopt.carena.diet.application.service;

import lombok.RequiredArgsConstructor;
import org.sopt.carena.diet.application.dto.view.DietDetailResultView;
import org.sopt.carena.diet.application.port.in.GetDietDetailUseCase;
import org.sopt.carena.diet.application.port.out.LoadDietDetailPort;
import org.sopt.carena.diet.exception.diet.DietNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DietDetailService implements GetDietDetailUseCase {

    private final LoadDietDetailPort loadDietDetailPort;

    @Override
    public DietDetailResultView getDietDetail(final Long dietId) {
        DietDetailResultView dietDetail = loadDietDetailPort.loadById(dietId).orElseThrow(DietNotFoundException::new);
        return new DietDetailResultView(
                dietDetail.id(),
                dietDetail.title(),
                dietDetail.content(),
                dietDetail.recommendedCategories(),
                dietDetail.cautionaryFoods(),
                dietDetail.reference()
        );
    }
}