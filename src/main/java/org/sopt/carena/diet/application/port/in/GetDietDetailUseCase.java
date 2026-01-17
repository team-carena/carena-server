package org.sopt.carena.diet.application.port.in;

import org.sopt.carena.diet.application.dto.view.DietDetailResultView;

public interface GetDietDetailUseCase {
    DietDetailResultView getDietDetail(Long dietId);
}