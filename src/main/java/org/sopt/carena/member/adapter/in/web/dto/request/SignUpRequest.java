package org.sopt.carena.member.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.sopt.carena.member.domain.Gender;

import java.time.LocalDate;

public record SignUpRequest(
        @NotBlank(message = "이름은 필수입니다.")
        String name,

        @NotNull(message = "생년월일은 필수입니다")
        LocalDate birthdate,

        @NotNull(message = "성별은 필수입니다.")
        Gender gender
) {
}