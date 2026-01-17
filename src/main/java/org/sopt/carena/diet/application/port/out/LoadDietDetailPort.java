package org.sopt.carena.diet.application.port.out;

import org.sopt.carena.diet.domain.value.DietDetail;
import java.util.Optional;

public interface LoadDietDetailPort {
    Optional<DietDetail> loadById(Long dietId);
}
