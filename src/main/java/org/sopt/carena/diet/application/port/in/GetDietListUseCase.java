package org.sopt.carena.diet.application.port.in;

import org.sopt.carena.diet.domain.DietSummary;
import org.springframework.data.domain.Slice;

public interface GetDietListUseCase {
    Slice<DietSummary> getDietList(int page);
}
