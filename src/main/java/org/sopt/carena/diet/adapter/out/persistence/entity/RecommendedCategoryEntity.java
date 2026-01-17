package org.sopt.carena.diet.adapter.out.persistence.entity;

import io.hypersistence.utils.hibernate.id.Tsid;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "recommended_category")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommendedCategoryEntity {
    @Id
    @Tsid
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diet_information_id", nullable = false)
    private DietInformationEntity dietInformation;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb", nullable = false)
    private Map<String, List<String>> categories;

    public RecommendedCategoryEntity(
            DietInformationEntity dietInformation,
            Map<String, List<String>> categories
    ) {
        this.dietInformation = dietInformation;
        this.categories = categories != null ? new HashMap<>(categories) : new HashMap<>();
    }
}
