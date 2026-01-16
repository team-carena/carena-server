package org.sopt.carena.diet.application.service;

import lombok.RequiredArgsConstructor;
import org.sopt.carena.diet.application.port.in.GetDietListUseCase;
import org.sopt.carena.diet.application.port.out.LoadDietListPort;
import org.sopt.carena.diet.domain.DietSummary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DietListService implements GetDietListUseCase {

    private static final int PAGE_SIZE = 10;

    private final LoadDietListPort loadDietListPort;

    @Override
    public Slice<DietSummary> getDietList(int page) {
        Pageable pageable = PageRequest.of(page - 1, PAGE_SIZE);
        return loadDietListPort.loadDietList(pageable);
    }
}