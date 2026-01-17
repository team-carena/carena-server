package org.sopt.carena.diet.application.port.in;

import org.sopt.carena.diet.application.dto.view.DietListResultView;

public interface GetDietListUseCase {
    DietListResultView getDietList(int page);
}
