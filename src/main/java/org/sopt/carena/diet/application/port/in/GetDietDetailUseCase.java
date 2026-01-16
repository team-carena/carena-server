package org.sopt.carena.diet.application.port.in;

import org.sopt.carena.diet.domain.DietDetail;

public interface GetDietDetailUseCase {
    DietDetail getDietDetail(Long dietId);
}