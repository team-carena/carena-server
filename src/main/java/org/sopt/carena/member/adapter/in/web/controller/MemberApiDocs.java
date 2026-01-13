package org.sopt.carena.member.adapter.in.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.sopt.carena.global.api.response.SuccessResponse;
import org.sopt.carena.member.adapter.in.web.dto.request.SignUpRequest;
import org.sopt.carena.member.adapter.in.web.dto.response.SignupResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;

@Tag(name = "멤버 관리",description = "회원가입, 토큰, 조회 관련 API")
public interface MemberApiDocs {
    @Operation(summary = "회원가입" ,description = "회원가입을 진행합니다.")
    ResponseEntity<SuccessResponse<SignupResponse>> signup(
            @CookieValue(name = "tempToken") final String tempToken,
            @RequestBody @Valid final SignUpRequest request,
            HttpServletResponse response);

    @Operation(summary = "토큰 재발급" ,description = "만료된 엑세스 토큰을 재발급합니다.")
    ResponseEntity<SuccessResponse<Void>> refreshToken(
            @CookieValue(name = "refreshToken") final String refreshToken,
            HttpServletResponse response);

}
