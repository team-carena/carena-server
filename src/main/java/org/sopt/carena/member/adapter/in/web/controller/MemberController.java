package org.sopt.carena.member.adapter.in.web.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.global.api.response.ApiResponse;
import org.sopt.carena.global.api.response.SuccessResponse;
import org.sopt.carena.member.adapter.in.web.controller.util.CookieUtil;
import org.sopt.carena.member.adapter.in.web.controller.util.HeaderUtil;
import org.sopt.carena.member.adapter.in.web.dto.MemberInfoResponse;
import org.sopt.carena.member.adapter.in.web.dto.SignUpRequest;
import org.sopt.carena.member.adapter.in.web.dto.SignupResponse;
import org.sopt.carena.member.adapter.in.web.code.MemberSuccessCode;
import org.sopt.carena.member.appliacation.dto.command.SignUpCommand;
import org.sopt.carena.member.appliacation.dto.view.MemberInfoView;
import org.sopt.carena.member.appliacation.dto.view.SignupView;
import org.sopt.carena.member.appliacation.dto.view.TokenRefreshView;
import org.sopt.carena.member.appliacation.port.in.GetMemberInfoUseCase;
import org.sopt.carena.member.appliacation.port.in.RefreshTokenUseCase;
import org.sopt.carena.member.appliacation.port.in.SignupUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController {

    private final SignupUseCase signupUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;
    private final GetMemberInfoUseCase getMemberInfoUseCase;

    @PostMapping("/signup")
    public ResponseEntity<SuccessResponse<SignupResponse>> signup(
            @CookieValue(name = "tempToken") String tempToken,
            @RequestBody @Valid SignUpRequest request,
            HttpServletResponse response
    ) {
        SignUpCommand command = SignUpCommand.of(tempToken, request);
        SignupView result = signupUseCase.signup(command);
        HeaderUtil.setAuthorizationHeader(response, result.accessToken());
        CookieUtil.addRefreshTokenCookie(response, result.refreshToken());
        CookieUtil.deleteTempTokenCookie(response);
        SignupResponse signupResponse = SignupResponse.from(result);
        return ResponseEntity.status(MemberSuccessCode.SIGNUP_SUCCESS.getStatus())
                .body(ApiResponse.success(MemberSuccessCode.SIGNUP_SUCCESS, signupResponse));
    }

    /**
     * Access Token 재발급
     */
    @PostMapping("/token/refresh")
    public ResponseEntity<SuccessResponse<Void>> refreshToken(
            @CookieValue(name = "refreshToken", required = false) String refreshToken,
            HttpServletResponse response
    ) {
        TokenRefreshView result = refreshTokenUseCase.refreshAccessToken(refreshToken);
        HeaderUtil.setAuthorizationHeader(response, result.accessToken());
        //현재 토큰 삭제
        CookieUtil.addRefreshTokenCookie(response, result.refreshToken());
        return ResponseEntity.status(MemberSuccessCode.TOKEN_REFRESHED.getStatus())
                        .body(ApiResponse.success(MemberSuccessCode.TOKEN_REFRESHED));
    }

    @GetMapping("/my-info")
    public ResponseEntity<SuccessResponse<MemberInfoResponse>> memberInfo(
            @AuthenticationPrincipal Long memberId,
            @RequestParam(required = false, defaultValue = "false") Boolean withScore
    ) {
        log.info("memberId: {}, withScore: {}", memberId, withScore);
        MemberInfoView memberInfo = getMemberInfoUseCase.getMemberInfo(memberId, withScore);
        MemberInfoResponse response = MemberInfoResponse.from(memberInfo, withScore);
        return ResponseEntity.status(MemberSuccessCode.MEMBER_IFNO.getStatus())
                .body(ApiResponse.success(MemberSuccessCode.MEMBER_IFNO, response));
    }
}