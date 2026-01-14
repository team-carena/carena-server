package org.sopt.carena.member.adapter.in.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.sopt.carena.global.api.response.SuccessResponse;
import org.sopt.carena.member.adapter.in.web.dto.request.SignUpRequest;
import org.springframework.http.ResponseEntity;

@Tag(name = "멤버 관리",description = "회원가입, 토큰, 조회 관련 API")
public interface MemberApiDocs {
    @Operation(summary = "회원가입" ,description = "회원가입을 진행합니다.")
    ResponseEntity<SuccessResponse<Void>> signup(String tempToken, SignUpRequest request, HttpServletResponse response);

    @Operation(summary = "토큰 재발급" ,description = "만료된 엑세스 토큰을 재발급합니다.")
    ResponseEntity<SuccessResponse<Void>> refreshToken(String refreshToken, HttpServletResponse response);

    @Operation(summary = "토큰 발급", description = "로그인 성공 후 해당 api를 이용하여 엑세스 및 리프레시 토큰을 발급받습니다.")
    ResponseEntity<SuccessResponse<Void>> afterLogin(String oneTimeToken, HttpServletResponse response);
}
