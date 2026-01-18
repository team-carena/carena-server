package org.sopt.carena.diet.domain.value;

import java.util.List;

public record CautionaryFoods(List<String> foods) {

    public CautionaryFoods {
        foods = foods != null
                ? List.copyOf(foods)
                : List.of();
    }

    public boolean isEmpty() {
        return foods.isEmpty();
    }
}

