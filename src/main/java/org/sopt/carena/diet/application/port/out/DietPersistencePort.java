package org.sopt.carena.diet.application.port.out;

import org.sopt.carena.diet.application.dto.view.DietDetailResultView;
import org.sopt.carena.diet.domain.value.DietChunk;
import org.sopt.carena.diet.domain.DietInformation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DietPersistencePort {
    void save(DietInformation info,
              List<DietChunk> chunks,
              String content,
              Map<String, List<String>> recommends,
              List<String> cautionary
    );
    Optional<DietDetailResultView> loadById(Long dietId);
    Slice<DietInformation> loadDietList(Pageable pageable);
}