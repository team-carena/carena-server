package org.sopt.carena.institution.adapter.in.web.controller;

import org.sopt.carena.global.api.response.ApiResponse;
import org.sopt.carena.global.api.response.SuccessResponse;
import org.sopt.carena.institution.adapter.in.web.code.SuccessCode;
import org.sopt.carena.institution.application.dto.view.InstitutionListView;
import org.sopt.carena.institution.application.dto.view.SidoCodeView;
import org.sopt.carena.institution.application.dto.view.SigunguCodeView;
import org.sopt.carena.institution.application.port.in.GetAddressCodeUseCase;
import org.sopt.carena.institution.application.port.in.GetInstitutionInfoUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/institution")
public class InstitutionController implements InstitutionApiDocs {
	private final GetAddressCodeUseCase getAddressCodeUseCase;
	private final GetInstitutionInfoUseCase getInstitutionInfoUseCase;

	@GetMapping(path = "/sido-code")
	public ResponseEntity<SuccessResponse<SidoCodeView>> getSidoCode() {
		return ResponseEntity.status(SuccessCode.ADDRESS_CODE_FOUND.getStatus())
				.body(ApiResponse.success(SuccessCode.ADDRESS_CODE_FOUND, getAddressCodeUseCase.getSidoCodes()));
	}

	@GetMapping(path = "/sigungu-code")
	public ResponseEntity<SuccessResponse<SigunguCodeView>> getSigunguCode(
			@RequestParam(name = "sidoCode") final int sidoCode
	) {
		return ResponseEntity.status(SuccessCode.ADDRESS_CODE_FOUND.getStatus())
				.body(ApiResponse.success(SuccessCode.ADDRESS_CODE_FOUND,
						getAddressCodeUseCase.getSigunguCodes(sidoCode)));
	}

	@GetMapping
	public ResponseEntity<SuccessResponse<InstitutionListView>> getInstitutionInfo(
			@RequestParam(name = "page", defaultValue = "1") @Min(1) final int page,
			@RequestParam(name = "sidoCode", required = false) final Integer sidoCode,
			@RequestParam(name = "sigunguCode", required = false) final Integer sigunguCode,
			@RequestParam(name = "type", defaultValue = "0") @Min(0) @Max(6) final int type,
			@RequestParam(name = "name", required = false) final String name
	) {
		return ResponseEntity.status(SuccessCode.INSTITUTION_FOUND.getStatus())
				.body(ApiResponse.success(SuccessCode.INSTITUTION_FOUND,
						getInstitutionInfoUseCase.searchInstitution(page, sidoCode, sigunguCode, type, name)));
	}
}
