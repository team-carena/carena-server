package org.sopt.carena.global.config.security.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.util.AntPathMatcher;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum PublicEndpoint {

    HEALTH_CHECK("/actuator/health", HttpMethod.GET),
    OAUTH2_START("/oauth2/**", HttpMethod.GET),
    OAUTH2_CALLBACK("/login/oauth2/**", HttpMethod.GET),
    LOGIN("/login", HttpMethod.GET),
    MEMBER_SIGNUP("/api/v1/member/signup", HttpMethod.POST),
    TOKEN_REFRESH("/api/v1/member/token/refresh", HttpMethod.POST),

    HOME("/", HttpMethod.GET),
    INDEX("/index.html", HttpMethod.GET),
    SIGNUP_PAGE("/signup.html", HttpMethod.GET),
    FAVICON("/favicon.ico", HttpMethod.GET),

    SWAGGER_UI("/swagger-ui/**", HttpMethod.GET),
    API_DOCS("/v3/api-docs/**", HttpMethod.GET),
    API_DOCS_V1("/api-docs/**", HttpMethod.GET),
    WEBJARS("/webjars/**", HttpMethod.GET),
    GENERATED_TOKEN("/api/v1/member/tokens", HttpMethod.POST);

    private final String pattern;
    private final HttpMethod method;

    private static final AntPathMatcher pathMatcher = new AntPathMatcher();

    public static boolean isPublicEndpoint(String uri, String httpMethod) {
        return Arrays.stream(values())
                .anyMatch(endpoint -> endpoint.matches(uri, httpMethod));
    }

    private boolean matches(String uri, String httpMethod) {
        boolean pathMatches = pathMatcher.match(this.pattern, uri);
        boolean methodMatches = this.method == null || this.method.matches(httpMethod);

        return pathMatches && methodMatches;
    }
    public static String[] getEndpoints() {
        return Arrays.stream(values())
                .map(PublicEndpoint::getPattern)
                .toArray(String[]::new);
    }
}