package org.sopt.carena.member.adapter.in.web.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.global.api.response.ApiResponse;
import org.sopt.carena.global.api.response.SuccessResponse;
import org.sopt.carena.member.adapter.in.web.dto.SignUpRequest;
import org.sopt.carena.member.adapter.in.web.dto.SignupResponse;
import org.sopt.carena.member.adapter.in.web.dto.TokenResponse;
import org.sopt.carena.member.appliacation.code.MemberSuccessCode;
import org.sopt.carena.member.appliacation.dto.command.SignUpCommand;
import org.sopt.carena.member.appliacation.dto.view.SignupView;
import org.sopt.carena.member.appliacation.dto.view.TokenRefreshView;
import org.sopt.carena.member.appliacation.port.in.RefreshTokenUseCase;
import org.sopt.carena.member.appliacation.port.in.SignupUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController extends BaseController {

    private final SignupUseCase signupUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;

    @PostMapping("/signup")
    public ResponseEntity<SuccessResponse<SignupResponse>> signup(
            @CookieValue(name = "tempToken") String tempToken,
            @RequestBody @Valid SignUpRequest request,
            HttpServletResponse response
    ) {
        SignUpCommand command = SignUpCommand.of(tempToken, request);
        SignupView result = signupUseCase.signup(command);

        addAccessTokenCookie(response, result.accessToken());
        addRefreshTokenCookie(response, result.refreshToken());
        deleteTempTokenCookie(response);
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
        TokenResponse tokenResponse = new TokenResponse(result.accessToken());
        addAccessTokenCookie(response, tokenResponse.accessToken());
        return ResponseEntity.status(MemberSuccessCode.TOKEN_REFRESHED.getStatus())
                        .body(ApiResponse.success(MemberSuccessCode.TOKEN_REFRESHED));
    }
}