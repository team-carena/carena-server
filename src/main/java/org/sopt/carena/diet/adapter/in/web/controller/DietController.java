package org.sopt.carena.diet.adapter.in.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.carena.diet.adapter.in.web.code.DietSuccessCode;
import org.sopt.carena.diet.adapter.in.web.dto.request.AdminDietRequest;
import org.sopt.carena.diet.adapter.out.persistence.mapper.AdminDietRequestMapper;
import org.sopt.carena.diet.application.port.in.RegisterDietDocumentUseCase;
import org.sopt.carena.diet.domain.DietInformation;
import org.sopt.carena.global.api.response.ApiResponse;
import org.sopt.carena.global.api.response.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/diet")
@RequiredArgsConstructor
public class DietController implements DietApiDocs {
    private final RegisterDietDocumentUseCase registerDietDocumentUseCase;

    @PostMapping
    public ResponseEntity<SuccessResponse<Void>> createDiet(
            @AuthenticationPrincipal Long memberId,
            @RequestBody @Valid final AdminDietRequest request
    ) {
        DietInformation information = AdminDietRequestMapper.toInformation(request);

        registerDietDocumentUseCase.register(information);

        return ResponseEntity.status(DietSuccessCode.DIET_CREATED.getStatus())
                .body(ApiResponse.success(DietSuccessCode.DIET_CREATED));
    }
}
