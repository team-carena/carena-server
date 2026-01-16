package org.sopt.carena.diet.application.port.out;

import org.sopt.carena.diet.domain.DietChunk;
import org.sopt.carena.diet.domain.DietInformation;

import java.util.List;

public interface DietPersistencePort {
    void save(DietInformation info,
              List<DietChunk> chunks,
              String content,
              List<String> recommends,
              List<String> cautionary
    );
}