package org.sopt.carena.member.domain;

import org.sopt.carena.member.appliacation.exception.oauth.UnsupportedOAuthProviderException;

import java.util.Arrays;

public enum AuthType {
    KAKAO;
    //NAVER;


    public static AuthType from(String provider) {
        if (provider == null || provider.isBlank()) {
            throw new UnsupportedOAuthProviderException();
        }

        return Arrays.stream(values())
                .filter(type -> type.name().equalsIgnoreCase(provider))
                .findFirst()
                .orElseThrow(UnsupportedOAuthProviderException::new);
    }
}
