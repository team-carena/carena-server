package org.sopt.carena.member.domain;

public enum AuthType {
    KAKAO("KAKAO"),
    NAVER("NAVER");

    private final String code;
    AuthType(String code) {
        this.code = code;
    }
}
