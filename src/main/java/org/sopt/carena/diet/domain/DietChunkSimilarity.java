package org.sopt.carena.diet.domain;

/**
 * 식단 청크의 유사도 정보
 */
public record DietChunkSimilarity(
        Long dietInformationId,
        double similarity,
        String section
) {

    public DietChunkSimilarity {
        validateDietInformationId(dietInformationId);
        validateSimilarity(similarity);
    }

    private static void validateDietInformationId(Long dietInformationId) {
        if (dietInformationId == null || dietInformationId <= 0) {
            throw new IllegalArgumentException(
                    "DietInformationId는 양수여야함: " + dietInformationId);
        }
    }

    private static void validateSimilarity(double similarity) {
        if (similarity < 0.0 || similarity > 1.0) {
            throw new IllegalArgumentException(
                    "유사도는 0.0 ~ 1.0: " + similarity);
        }
    }
}
