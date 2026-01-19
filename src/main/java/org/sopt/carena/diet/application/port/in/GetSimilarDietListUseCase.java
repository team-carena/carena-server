package org.sopt.carena.diet.application.port.in;

import org.sopt.carena.diet.application.dto.view.DietListResultView;

public interface GetSimilarDietListUseCase {
    DietListResultView getDietList(int page, long memberId);
}