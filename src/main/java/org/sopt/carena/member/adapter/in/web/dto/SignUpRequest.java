package org.sopt.carena.member.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.sopt.carena.member.domain.Gender;

public record SignUpRequest(
        @NotBlank(message = "임시 토큰은 필수입니다.")
        String tempToken,

        @NotBlank(message = "이름은 필수입니다.")
        String name,

        @NotBlank(message = "생년월일은 필수입니다.")
        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "생년월일은 YYYY-MM-DD 형식이어야 합니다.")
        String birthdate,

        @NotNull(message = "성별은 필수입니다.")
        Gender gender
) {
}