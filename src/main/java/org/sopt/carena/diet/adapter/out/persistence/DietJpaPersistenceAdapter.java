package org.sopt.carena.diet.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.sopt.carena.diet.adapter.out.persistence.entity.DietChunkEntity;
import org.sopt.carena.diet.adapter.out.persistence.entity.DietInformationEntity;
import org.sopt.carena.diet.adapter.out.persistence.repository.DietChunkJpaRepository;
import org.sopt.carena.diet.adapter.out.persistence.repository.DietInformationJpaRepository;
import org.sopt.carena.diet.application.port.out.DietPersistencePort;
import org.sopt.carena.diet.domain.DietChunk;
import org.sopt.carena.diet.domain.DietInformation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DietJpaPersistenceAdapter implements DietPersistencePort {

    private final DietInformationJpaRepository infoRepository;
    private final DietChunkJpaRepository chunkRepository;

    @Override
    public void save(DietInformation info, List<DietChunk> chunks,
                     String content,
                     List<String> recommends,
                     List<String> cautionary) {

        DietInformationEntity infoEntity = new DietInformationEntity(
                info.getTitle(),
                content,
                recommends,
                cautionary,
                info.getReference(),
                info.getReferenceUrl()
        );

        infoRepository.save(infoEntity);

        List<DietChunkEntity> entities = chunks.stream()
                .map(chunk -> new DietChunkEntity(
                        infoEntity,
                        chunk.getSection(),
                        chunk.getContent(),
                        chunk.getEmbeddingText(),
                        chunk.getEmbedding(),
                        chunk.getChunkOrder()
                ))
                .toList();

        chunkRepository.saveAll(entities);
    }
}