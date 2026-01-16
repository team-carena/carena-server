package org.sopt.carena.diet.application.port.in;

import org.sopt.carena.diet.domain.DietInformation;

public interface RegisterDietDocumentUseCase {
    void register(DietInformation document);
}
