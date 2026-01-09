package org.sopt.carena.healthtip.adapter.in.web.controller;

import org.sopt.carena.global.api.response.ApiResponse;
import org.sopt.carena.global.api.response.SuccessResponse;
import org.sopt.carena.healthtip.adapter.in.request.CreateHealthTipRequest;
import org.sopt.carena.healthtip.adapter.in.web.code.SuccessCode;
import org.sopt.carena.healthtip.application.dto.command.CreateHealthTipCommand;
import org.sopt.carena.healthtip.application.dto.view.ReadHealthTipDetailView;
import org.sopt.carena.healthtip.application.dto.view.ReadHealthTipListView;
import org.sopt.carena.healthtip.application.port.in.CreateHealthTipUseCase;
import org.sopt.carena.healthtip.application.port.in.DeleteHealthTipUseCase;
import org.sopt.carena.healthtip.application.port.in.ReadHealthTipDetailUseCase;
import org.sopt.carena.healthtip.application.port.in.ReadHealthTipListUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/health-tip")
public class HealthTipController implements HealthTipApiDocs {
	private final CreateHealthTipUseCase createHealthTipUseCase;
	private final ReadHealthTipListUseCase readHealthTipListUseCase;
	private final ReadHealthTipDetailUseCase readHealthTipDetailUseCase;
	private final DeleteHealthTipUseCase deleteHealthTipUseCase;

	@GetMapping
	public ResponseEntity<SuccessResponse<ReadHealthTipListView>> readHealthTipList(
			@RequestParam(name = "page", defaultValue = "1") @Min(1) final int page
	) {
		return ResponseEntity.status(SuccessCode.HEALTH_TIP_FOUND.getStatus())
				.body(ApiResponse.success(SuccessCode.HEALTH_TIP_FOUND,
						readHealthTipListUseCase.readHealthTipList(page)));
	}

	@GetMapping(path = "/{healthTipId}")
	public ResponseEntity<SuccessResponse<ReadHealthTipDetailView>> readHealthTipDetail(
			@PathVariable(name = "healthTipId") final long healthTipId
	) {
		return ResponseEntity.status(SuccessCode.HEALTH_TIP_FOUND.getStatus())
				.body(ApiResponse.success(SuccessCode.HEALTH_TIP_FOUND, readHealthTipDetailUseCase.readHealthTipDetail(healthTipId)));
	}

	@PostMapping
	public ResponseEntity<SuccessResponse<Void>> createHealthTip(
			@Valid @RequestBody final CreateHealthTipRequest request
	) {
		createHealthTipUseCase.createHealthTip(CreateHealthTipCommand.from(request));

		return ResponseEntity.status(SuccessCode.HEALTH_TIP_CREATED.getStatus())
				.body(ApiResponse.success(SuccessCode.HEALTH_TIP_CREATED));
	}

	@DeleteMapping(path = "/{healthTipId}")
	public ResponseEntity<SuccessResponse<Void>> deleteHealthTip(
			@PathVariable(name = "healthTipId") final long healthTipId
	){
		deleteHealthTipUseCase.deleteHealthTip(healthTipId);

		return ResponseEntity.status(SuccessCode.HEALTH_TIP_DELETED.getStatus())
				.body(ApiResponse.success(SuccessCode.HEALTH_TIP_DELETED));
	}
}
