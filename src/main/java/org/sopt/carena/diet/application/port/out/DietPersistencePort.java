package org.sopt.carena.diet.application.port.out;

import org.sopt.carena.diet.domain.value.DietChunk;
import org.sopt.carena.diet.domain.DietInformation;

import java.util.List;
import java.util.Map;

public interface DietPersistencePort {
    void save(DietInformation info,
              List<DietChunk> chunks,
              String content,
              Map<String, List<String>> recommends,
              List<String> cautionary
    );
}