package org.sopt.carena.member.adapter.in.web.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.global.api.response.ApiResponse;
import org.sopt.carena.global.api.response.SuccessResponse;
import org.sopt.carena.member.adapter.in.web.dto.OAuthLoginResponse;
import org.sopt.carena.member.adapter.in.web.dto.SignUpRequest;
import org.sopt.carena.member.adapter.in.web.dto.SignupResponse;
import org.sopt.carena.member.appliacation.code.MemberSuccessCode;
import org.sopt.carena.member.appliacation.dto.command.SignUpCommand;
import org.sopt.carena.member.appliacation.dto.view.SignupView;
import org.sopt.carena.member.appliacation.dto.view.TokenRefreshView;
import org.sopt.carena.member.appliacation.port.in.OAuthLoginUseCase;
import org.sopt.carena.member.appliacation.port.in.RefreshTokenUseCase;
import org.sopt.carena.member.appliacation.port.in.SignupUseCase;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class MemberController extends BaseController {

    private final OAuthLoginUseCase oAuthLoginUseCase;
    private final SignupUseCase signupUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;

    @PostMapping("/login/{oauthProvider}")
    public SuccessResponse<OAuthLoginResponse> login(@PathVariable String oauthProvider) {

        log.info("로그인 요청 - provider: {}", oauthProvider);

        String authUrl = oAuthLoginUseCase.getAuthUrl(oauthProvider);

        OAuthLoginResponse responseData = new OAuthLoginResponse(authUrl);

        return ApiResponse.success(MemberSuccessCode.LOGIN_URL_CREATED, responseData);
    }

    @PostMapping("/signup")
    public SuccessResponse<SignupResponse> signup(
            @RequestBody @Valid SignUpRequest request,
            HttpServletResponse response
    ) {
        log.info("회원가입 요청 - name: {}", request.name());

        SignUpCommand command = SignUpCommand.from(request);
        SignupView result = signupUseCase.signup(command);

        addAuthTokenCookies(
                response,
                result.accessToken(),
                result.refreshToken()
        );

        SignupResponse signupResponse = SignupResponse.from(result);

        return ApiResponse.success(MemberSuccessCode.SIGNUP_SUCCESS, signupResponse);
    }

    /**
     * Access Token 재발급
     */
    @PostMapping("/token/refresh")
    public SuccessResponse<Void> refreshToken(
            @CookieValue(name = "refreshToken", required = false) String refreshToken,
            HttpServletResponse response
    ) {
        log.info("토큰 재발급 요청");

        TokenRefreshView result = refreshTokenUseCase.refreshAccessToken(refreshToken);
        addAccessTokenCookie(response, result.accessToken());

        return ApiResponse.success(MemberSuccessCode.TOKEN_REFRESHED);
    }
}