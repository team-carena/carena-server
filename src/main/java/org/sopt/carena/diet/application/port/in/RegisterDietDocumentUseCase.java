package org.sopt.carena.diet.application.port.in;

import org.sopt.carena.diet.domain.DietChunk;
import org.sopt.carena.diet.domain.DietInformation;

public interface RegisterDietDocumentUseCase {
    //String generate(DietChunk chunk, String documentTitle);
    void register(DietInformation document);
}
