package org.sopt.carena.diet.application.port.out;

import org.sopt.carena.diet.application.dto.view.DietDetailResultView;

import java.util.Optional;

public interface LoadDietDetailPort {
    Optional<DietDetailResultView> loadById(Long dietId);
}
