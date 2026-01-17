package org.sopt.carena.diet.adapter.out;

import lombok.RequiredArgsConstructor;
import org.sopt.carena.diet.adapter.out.persistence.repository.DietInformationJpaRepository;
import org.sopt.carena.diet.application.port.out.LoadDietListPort;
import org.sopt.carena.diet.domain.value.DietSummary;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DietListPersistenceAdapter implements LoadDietListPort {

    private final DietInformationJpaRepository repository;

    @Override
    public Slice<DietSummary> loadDietList(final Pageable pageable) {
        return repository.findAllByOrderByIdDesc(pageable)
                .map(entity -> new DietSummary(
                        entity.getId(),
                        entity.getTitle()
                ));
    }
}