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
}
