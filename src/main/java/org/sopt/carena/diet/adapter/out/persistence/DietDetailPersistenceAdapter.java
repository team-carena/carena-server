package org.sopt.carena.diet.adapter.out.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.diet.adapter.out.persistence.entity.DietInformationEntity;
import org.sopt.carena.diet.adapter.out.persistence.mapper.DietPersistenceMapper;
import org.sopt.carena.diet.adapter.out.persistence.repository.CautionCategoryRepository;
import org.sopt.carena.diet.adapter.out.persistence.repository.DietInformationJpaRepository;
import org.sopt.carena.diet.adapter.out.persistence.repository.RecommendedCategoryRepository;
import org.sopt.carena.diet.application.dto.view.DietDetailResultView;
import org.sopt.carena.diet.application.port.out.LoadDietDetailPort;
import org.sopt.carena.diet.domain.DietInformation;
import org.sopt.carena.diet.domain.value.DietChunk;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class DietDetailPersistenceAdapter implements LoadDietDetailPort {

    private final DietInformationJpaRepository repository;
    private final DietPersistenceMapper mapper;
    private final RecommendedCategoryRepository recommendedRepository;
    private final CautionCategoryRepository cautionRepository;
    private final ObjectMapper objectMapper;

/*
    @Override
    @Transactional(readOnly = true)
    public Optional<DietInformation> loadById(Long dietId) {
        System.out.println("==========================");
        System.out.println(repository.findById(dietId).get().getId());
        System.out.println(repository.findById(dietId).get().getCautionaryFood().getCautionary());
        System.out.println(repository.findById(dietId).get().getRecommendedFood().getCategories());
        System.out.println("==========================");

        return repository.findById(dietId)
                .map(mapper::toDomain);
    }

 */



    @Override
    @Transactional(readOnly = true)
    public Optional<DietDetailResultView> loadById(Long dietId) {
        Optional<DietDetailResultView> result = repository.findById(dietId)
                .map(this::toDomain);
        return result;
    }


    private DietDetailResultView toDomain(final DietInformationEntity entity) {

        Map<String, List<String>> recommendedCategories = entity.getRecommendedFood() != null
                ? entity.getRecommendedFood().getCategories()
                : Map.of();

        List<String> cautionaryFoods = entity.getCautionaryFood() != null
                ? entity.getCautionaryFood().getCautionary()
                : List.of();

        return new DietDetailResultView(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                recommendedCategories,
                cautionaryFoods,
                entity.getReference()
        );
    }
}