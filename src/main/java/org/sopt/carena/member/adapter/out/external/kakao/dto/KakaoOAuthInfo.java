package org.sopt.carena.member.adapter.out.external.kakao.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoOAuthInfo {
    private String sub;    // 카카오 식별자
    private String email;
}