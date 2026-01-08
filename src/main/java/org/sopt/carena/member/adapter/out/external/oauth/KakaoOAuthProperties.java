package org.sopt.carena.member.adapter.out.external.oauth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "oauth.kakao")
@Getter
@Setter
public class KakaoOAuthProperties {
    private String clientId;
    private String redirectUri;
}