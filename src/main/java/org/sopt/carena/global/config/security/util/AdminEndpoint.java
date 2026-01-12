package org.sopt.carena.global.config.security.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.AntPathMatcher;
import org.springframework.http.HttpMethod;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum AdminEndpoint {;

    private final String pattern;
    private final HttpMethod method;

    private static final AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * ADMIN 전용 엔드포인트인지 확인
     */
    public static boolean isAdminEndpoint(String uri, String httpMethod) {
        return Arrays.stream(values())
                .anyMatch(endpoint -> endpoint.matches(uri, httpMethod));
    }

    private boolean matches(String uri, String httpMethod) {
        boolean pathMatches = pathMatcher.match(this.pattern, uri);
        boolean methodMatches = this.method == null || this.method.matches(httpMethod);
        return pathMatches && methodMatches;
    }

}
