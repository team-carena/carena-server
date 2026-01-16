package org.sopt.carena.diet.application.port.out;

import org.sopt.carena.diet.domain.DietDetail;
import java.util.Optional;

public interface LoadDietDetailPort {
    Optional<DietDetail> loadById(Long dietId);
}
