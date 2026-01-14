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
import org.sopt.carena.member.adapter.in.web.dto.request.SignUpRequest;
import org.sopt.carena.member.adapter.in.web.code.MemberSuccessCode;
import org.sopt.carena.member.application.dto.command.SignUpCommand;
import org.sopt.carena.member.application.dto.view.TokenGeneratedView;
import org.sopt.carena.member.application.port.in.GenerateTokenUseCase;
import org.sopt.carena.member.application.port.in.RefreshTokenUseCase;
import org.sopt.carena.member.application.port.in.SignupUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController implements MemberApiDocs{

    private final SignupUseCase signupUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;
    private final GenerateTokenUseCase generateTokenUseCase;

    @PostMapping("/signup")
    public ResponseEntity<SuccessResponse<Void>> signup(
            @CookieValue(name = "tempToken") final String tempToken,
            @RequestBody @Valid final SignUpRequest request,
            HttpServletResponse response
    ) {
        signupUseCase.signup(SignUpCommand.of(tempToken, request));
        CookieUtil.deleteTempTokenCookie(response);
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
        TokenGeneratedView result=generateTokenUseCase.generateToken(oneTimeToken);
        HeaderUtil.setAuthorizationHeader(response, result.accessToken());
        CookieUtil.addRefreshTokenCookie(response, result.refreshToken());
        return ResponseEntity.status(MemberSuccessCode.TOKEN_GENERATED.getStatus())
                .body(ApiResponse.success(MemberSuccessCode.TOKEN_GENERATED));
    }
}