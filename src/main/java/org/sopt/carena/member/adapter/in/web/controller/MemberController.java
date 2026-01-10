package org.sopt.carena.member.adapter.in.web.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.Cookie;
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
    /*

    @PostMapping("/login/{oauthProvider}")
    public ResponseEntity<SuccessResponse<OAuthLoginResponse>> login(@PathVariable String oauthProvider) {

        log.info("로그인 요청 - provider: {}", oauthProvider);

        String authUrl = oAuthLoginUseCase.getAuthUrl(oauthProvider);

        OAuthLoginResponse responseData = new OAuthLoginResponse(authUrl);

        return ResponseEntity.status(MemberSuccessCode.LOGIN_URL_CREATED.getStatus())
                .body(ApiResponse.success(
                        MemberSuccessCode.LOGIN_URL_CREATED,
                        responseData
                ));
    }

     */

    @PostMapping("/signup")
    public SuccessResponse<SignupResponse> signup(
            @CookieValue(name = "tempToken") String tempToken,
            @RequestBody @Valid SignUpRequest request,
            HttpServletResponse response
    ) {
        log.info("회원가입 요청 - name: {}", request.name());
        log.info("tempToken (쿠키): {}", tempToken);

        SignUpCommand command = SignUpCommand.of(tempToken, request);
        SignupView result = signupUseCase.signup(command);

        addRefreshTokenCookie(
                response,
                result.refreshToken()
        );

        deleteTempTokenCookie(response);

        SignupResponse signupResponse = SignupResponse.from(result);

        return ApiResponse.success(MemberSuccessCode.SIGNUP_SUCCESS, signupResponse);
    }

    /**
     * Access Token 재발급
     */
    @PostMapping("/token/refresh")
    public ResponseEntity<SuccessResponse<TokenResponse>> refreshToken(
            @CookieValue(name = "refreshToken", required = false) String refreshToken,
            HttpServletResponse response
    ) {
        log.info("토큰 재발급 요청");

        TokenRefreshView result = refreshTokenUseCase.refreshAccessToken(refreshToken);
        TokenResponse tokenResponse = new TokenResponse(result.accessToken());


        return ResponseEntity.status(MemberSuccessCode.TOKEN_REFRESHED.getStatus())
                .body(ApiResponse.success(
                        MemberSuccessCode.TOKEN_REFRESHED,
                        tokenResponse
                ));
    }
    private void deleteTempTokenCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("tempToken", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}