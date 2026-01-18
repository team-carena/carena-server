package org.sopt.carena.healthtip.adapter.in.web.controller;

import org.sopt.carena.global.api.response.SuccessResponse;
import org.sopt.carena.healthtip.adapter.in.request.CreateHealthTipRequest;
import org.sopt.carena.healthtip.application.dto.view.ReadHealthTipDetailView;
import org.sopt.carena.healthtip.application.dto.view.ReadHealthTipListView;
import org.sopt.carena.healthtip.application.dto.view.ReadHealthTipTickerView;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@Tag(name = "건강팁 큐레이션", description = "건강팁 큐레이션 관련 API")
public interface HealthTipApiDocs {
	@Operation(summary = "건강팁 목록 조회", description = "건강팁의 목록을 조회합니다.")
	ResponseEntity<SuccessResponse<ReadHealthTipListView>> readHealthTipList(@Min(1) int page, String hashtagName);

	@Operation(summary = "Ticker에서의 건강팁 목록 조회",description = "Ticker에 표시할 건강팁의 목록을 조회합니다.")
	ResponseEntity<SuccessResponse<ReadHealthTipTickerView>> readHealthTipTicker(long memberId);

	@Operation(summary = "건강팁 상세 조회", description = "특정 ID에 해당하는 건강팁의 세부 내용을 조회합니다.")
	ResponseEntity<SuccessResponse<ReadHealthTipDetailView>> readHealthTipDetail(long healthTipId);

	@Operation(summary = "[ADMIN] 건강팁 생성", description = "건강팁 큐레이션을 생성하는 어드민 기능입니다.")
	ResponseEntity<SuccessResponse<Void>> createHealthTip(@Valid CreateHealthTipRequest request);

	@Operation(summary = "[ADMIN] 건강팁 삭제", description = "건강팁 큐레이션을 삭제하는 어드민 기능입니다.")
	ResponseEntity<SuccessResponse<Void>> deleteHealthTip(long healthTipId);
}
