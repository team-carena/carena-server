package org.sopt.carena.member.adapter.in.web.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.carena.member.appliacation.dto.view.KakaoLoginView;
import org.sopt.carena.member.appliacation.port.in.KakaoLoginUseCase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/oauth")
@RequiredArgsConstructor
public class OAuthController {

    private final KakaoLoginUseCase kakaoLoginUseCase;

    @Value("${frontend.url}")
    private String frontendUrl;

    private static final int TEMP_TOKEN_MAX_AGE = 600; //10분
    private static final int ACCESS_TOKEN_MAX_AGE = 86400; //24시간

    /**
     * 카카오 콜백 엔드포인트
     * - 카카오가 인가코드를 이 엔드포인트로 전달
     * - 서버에서 ID Token 처리 후 프론트엔드로 리다이렉트
     * - 이 API는 공통 응답 구조 사용하지 않음(브라우저 직접 호출 + 리다이렉트 응답)
     */
    @GetMapping("/kakao/callback")
    // 콜백 인자는 카카오 서버에서 보내는 단순한 쿼리 파라미터이기 때문에 dto나 command가아닌 단순하게 처리함.
    public ResponseEntity<Void> kakaoCallback(
            @RequestParam String code,
            HttpServletResponse response) {

        log.info("카카오 콜백 수신 - code: {}", code);

        try {
            // 서버에서 인가코드로 ID Token 처리
            KakaoLoginView result = kakaoLoginUseCase.handleCallback(code);
            if (result.needsSignup()) {
                // 신규 회원: tempToken을 쿠키에 담아서 회원가입 페이지로
                return handleNewMember(result, response);
            } else {
                // 기존 회원: JWT를 쿠키에 담아서 메인 페이지로
                return handleExistingMember(result, response);
            }
        } catch (Exception e){
             log.error("카카오 OAuth 콜백 처리 실패", e);
             return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(false);
        response.addCookie(cookie);
    }

    /**
     * 신규 회원 처리
     */
    private ResponseEntity<Void> handleNewMember(
            KakaoLoginView result,
            HttpServletResponse response
    ) {
        log.info("신규 회원 - 회원가입 페이지로 리다이렉트");

        // tempToken을 쿠키에 저장 (10분 유효)
        addCookie(response, "tempToken", result.tempToken(), TEMP_TOKEN_MAX_AGE);

        // 회원가입 페이지로 리다이렉트
        return redirect(frontendUrl + "/signup");
    }

    /**
     * 기존 회원 처리
     */
    private ResponseEntity<Void> handleExistingMember(
            KakaoLoginView result,
            HttpServletResponse response
    ) {
        log.info("기존 회원 - 메인 페이지로 리다이렉트 (memberId: {})",
                result.member().id());

        // JWT를 쿠키에 저장 (24시간 유효)
        addCookie(response, "accessToken", result.accessToken(), ACCESS_TOKEN_MAX_AGE);

        // 메인 페이지로 리다이렉트
        return redirect(frontendUrl + "/");
    }

    /**
     * 리다이렉트 응답 생성 헬퍼 메서드
     * @param url 리다이렉트 URL
     * @return 302 리다이렉트 응답
     */
    private ResponseEntity<Void> redirect(String url) {
        log.debug("리다이렉트 - url: {}", url);
        return ResponseEntity
                .status(HttpStatus.FOUND) // 302
                .location(URI.create(url))
                .build();
    }
}
