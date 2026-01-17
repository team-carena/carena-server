package org.sopt.carena.diet.adapter.in.web.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.diet.adapter.in.web.code.DietSuccessCode;
import org.sopt.carena.diet.adapter.in.web.dto.request.CreateAdminDietRequest;
import org.sopt.carena.diet.adapter.in.web.dto.response.DietDetailResponse;
import org.sopt.carena.diet.adapter.in.web.dto.response.DietListResponse;
import org.sopt.carena.diet.adapter.in.web.mapper.DietCommandMapper;
import org.sopt.carena.diet.application.dto.command.CreateDietCommand;
import org.sopt.carena.diet.application.dto.view.DietListResultView;
import org.sopt.carena.diet.application.port.in.GetDietListUseCase;
import org.sopt.carena.diet.application.port.in.RegisterDietDocumentUseCase;
import org.sopt.carena.diet.application.port.in.GetDietDetailUseCase;
import org.sopt.carena.diet.domain.value.DietDetail;
import org.sopt.carena.global.api.response.ApiResponse;
import org.sopt.carena.global.api.response.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/diet")
@RequiredArgsConstructor
@Slf4j
public class DietController implements DietApiDocs {
    private final RegisterDietDocumentUseCase registerDietDocumentUseCase;
    private final GetDietDetailUseCase getDietDetailUseCase;
    private final GetDietListUseCase getDietListUseCase;
    private final DietCommandMapper dietcommandMapper;

    @PostMapping
    public ResponseEntity<SuccessResponse<Void>> createDiet(
            @RequestBody @Valid final CreateAdminDietRequest request
    ) {
        CreateDietCommand command = dietcommandMapper.toCommand(request);
        registerDietDocumentUseCase.register(command);

        return ResponseEntity.status(DietSuccessCode.DIET_CREATED.getStatus())
                .body(ApiResponse.success(DietSuccessCode.DIET_CREATED));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<DietDetailResponse>> dietDetail(
            @PathVariable Long id
    ) {
        DietDetail dietDetail = getDietDetailUseCase.getDietDetail(id);
        DietDetailResponse response = DietDetailResponse.from(dietDetail);

        return ResponseEntity.status(DietSuccessCode.DIET_DETAIL.getStatus())
                .body(ApiResponse.success(DietSuccessCode.DIET_DETAIL,response));
    }

    @GetMapping
    public ResponseEntity<SuccessResponse<DietListResponse>> getDietList(
            @RequestParam(defaultValue = "1") @Min(1) int page
    ) {
        log.info("시작");
        DietListResultView result = getDietListUseCase.getDietList(page);
        DietListResponse response = DietListResponse.from(result);

        return ResponseEntity.status(DietSuccessCode.DIET_LIST.getStatus())
                .body(ApiResponse.success(DietSuccessCode.DIET_LIST,response));
    }
}
