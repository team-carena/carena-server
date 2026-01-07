package org.sopt.carena.member.domain;

public enum AuthType {
    KAKAO("KAKAO"),
    NAVER("NAVER");

    private final String code;
    AuthType(String code) {
        this.code = code;
    }
    public static boolean isSupported(String provider) {
        try {
            fromCode(provider);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
    public static AuthType fromCode(String code) {
        for (AuthType type : values()) {
            if (type.code.equalsIgnoreCase(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("지원하지 않는 OAuth Provider: " + code);
    }
}
