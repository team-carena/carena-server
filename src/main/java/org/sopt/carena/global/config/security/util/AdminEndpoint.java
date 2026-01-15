package org.sopt.carena.global.config.security.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.util.AntPathMatcher;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum AdminEndpoint {
    CREATE_HEALTH_TIP("/api/v1/health-tip",HttpMethod.POST),
    CREATE_DIET("/api/v1/diet",HttpMethod.POST);

    private final String pattern;
    private final HttpMethod method;

    private static final AntPathMatcher pathMatcher = new AntPathMatcher();
    public static boolean isAdminEndpoint(String uri, String httpMethod) {
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
                .map(AdminEndpoint::getPattern)
                .toArray(String[]::new);
    }

}
