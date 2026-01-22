package org.sopt.carena.member.adapter.in.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.sopt.carena.global.config.security.util.AccessTokenResolver;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.global.api.response.ApiResponse;
import org.sopt.carena.global.api.response.SuccessResponse;
import org.sopt.carena.member.adapter.in.web.controller.util.CookieUtil;
import org.sopt.carena.member.adapter.in.web.controller.util.HeaderUtil;
import org.sopt.carena.member.adapter.in.web.dto.response.MemberInfoResponse;
import org.sopt.carena.member.adapter.in.web.dto.response.MyPageResponse;
import org.sopt.carena.member.adapter.in.web.dto.request.SignUpRequest;
import org.sopt.carena.member.adapter.in.web.code.MemberSuccessCode;
import org.sopt.carena.member.application.dto.command.SignUpCommand;
import org.sopt.carena.member.application.dto.view.MyPageInfoView;
import org.sopt.carena.member.application.dto.view.TokenGeneratedView;
import org.sopt.carena.member.application.port.in.GenerateTokenUseCase;
import org.sopt.carena.member.application.port.in.LogoutUseCase;
import org.sopt.carena.member.application.port.in.RefreshTokenUseCase;
import org.sopt.carena.member.application.port.in.SignupUseCase;
import org.sopt.carena.member.application.dto.view.MemberInfoView;
import org.sopt.carena.member.application.port.in.GetMemberInfoUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController implements MemberApiDocs{

    private final SignupUseCase signupUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;
    private final GetMemberInfoUseCase getMemberInfoUseCase;
    private final GenerateTokenUseCase generateTokenUseCase;
    private final LogoutUseCase logoutUseCase;

    @PostMapping("/signup")
    public ResponseEntity<SuccessResponse<Void>> signup(
            @CookieValue(name = "tempToken") final String tempToken,
            @RequestBody @Valid final SignUpRequest request,
            HttpServletResponse response
    ) {
        signupUseCase.signup(SignUpCommand.of(tempToken, request));
        CookieUtil.deleteCookie(response,"tempToken");
        return ResponseEntity.status(MemberSuccessCode.SIGNUP_SUCCESS.getStatus())
                .body(ApiResponse.success(MemberSuccessCode.SIGNUP_SUCCESS));
    }

    /**
     * Access Token 재발급
     */
    @PostMapping("/token/refresh")
    public ResponseEntity<SuccessResponse<Void>> refreshToken(
            @CookieValue(name = "refreshToken") final String refreshToken,
            HttpServletResponse response
    ) {
            TokenGeneratedView result = refreshTokenUseCase.refreshAccessToken(refreshToken);
            HeaderUtil.setAuthorizationHeader(response, result.accessToken());
            CookieUtil.addRefreshTokenCookie(response, result.refreshToken());
            return ResponseEntity.status(MemberSuccessCode.TOKEN_REFRESHED.getStatus())
                    .body(ApiResponse.success(MemberSuccessCode.TOKEN_REFRESHED));
    }

    @PostMapping("/tokens")
    public ResponseEntity<SuccessResponse<Void>> afterLogin(
            @CookieValue(name="oneTimeToken") final String oneTimeToken,
            HttpServletResponse response
    ) {
            TokenGeneratedView result = generateTokenUseCase.generateToken(oneTimeToken);
            HeaderUtil.setAuthorizationHeader(response, result.accessToken());
            CookieUtil.addRefreshTokenCookie(response, result.refreshToken());
            CookieUtil.deleteCookie(response, "oneTimeToken");
            return ResponseEntity.status(MemberSuccessCode.TOKEN_GENERATED.getStatus())
                    .body(ApiResponse.success(MemberSuccessCode.TOKEN_GENERATED));
    }

    @GetMapping("/my-page")
    public ResponseEntity<SuccessResponse<MyPageResponse>> myPage(
            @AuthenticationPrincipal Long memberId
    ) {
        log.info("memberId: {}", memberId);
        MyPageInfoView memberInfo = getMemberInfoUseCase.getMyPageInfo(memberId);
        MyPageResponse response = MyPageResponse.from(memberInfo);
        return ResponseEntity.status(MemberSuccessCode.MEMBER_INFO.getStatus())
                .body(ApiResponse.success(MemberSuccessCode.MEMBER_INFO, response));
    }

    @GetMapping("/my-info")
    public ResponseEntity<SuccessResponse<MemberInfoResponse>> memberInfo(
            @AuthenticationPrincipal Long memberId
    ) {
        log.info("memberId: {}", memberId);
        MemberInfoView memberInfo = getMemberInfoUseCase.getMemberInfo(memberId);
        MemberInfoResponse response = MemberInfoResponse.from(memberInfo);
        return ResponseEntity.status(MemberSuccessCode.MEMBER_INFO.getStatus())
                .body(ApiResponse.success(MemberSuccessCode.MEMBER_INFO, response));
    }

    @PostMapping("/logout")
    public ResponseEntity<SuccessResponse<Void>> logout(
            @AuthenticationPrincipal Long memberId,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String accessToken = AccessTokenResolver.resolve(request);
        logoutUseCase.logout(memberId,accessToken);
        CookieUtil.deleteCookie(response, "refreshToken");
        log.info("memberId: {}", memberId);
        return ResponseEntity.status(MemberSuccessCode.LOGOUT_SUCCESS.getStatus())
                .body(ApiResponse.success(MemberSuccessCode.LOGOUT_SUCCESS));
    }
}