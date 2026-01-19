package org.sopt.carena.diet.adapter.out.persistence.entity;

import io.hypersistence.utils.hibernate.id.Tsid;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "caution_category")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CautionCategoryEntity {
    @Id
    @Tsid
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diet_information_id", nullable = false,foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private DietInformationEntity dietInformation;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private List<String> cautionary;

    public CautionCategoryEntity(
            DietInformationEntity dietInformation,
            List<String>  cautionary
    ) {
        this.dietInformation = dietInformation;
        this.cautionary =  cautionary != null ? new ArrayList<>(cautionary) : new ArrayList<>();
    }
}
