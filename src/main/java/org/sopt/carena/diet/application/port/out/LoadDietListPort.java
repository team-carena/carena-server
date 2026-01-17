package org.sopt.carena.diet.application.port.out;

import org.sopt.carena.diet.domain.DietInformation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface LoadDietListPort {
    Slice<DietInformation> loadDietList(Pageable pageable);
}