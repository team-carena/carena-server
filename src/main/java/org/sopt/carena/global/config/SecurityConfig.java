package org.sopt.carena.global.config;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                //  CSRF 비활성화
                .csrf(csrf -> csrf.disable())
                // 기본 로그인 페이지 비활성화
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth

                        // 인증 불필요
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/api/v1/member/login/**",
                                "/api/oauth/**",
                                "/api/v1/member/signup",
                                "/api-docs/**",
                                "/api/v1/member/token/refresh"
                        ).permitAll()
                        // 나머지는 인증 필요
                        //.anyRequest().authenticated()

                        //임시로 나머지는 일단 permitAll로 설정
                        .anyRequest().permitAll()
                )
                .addFilterBefore(
                        (Filter) jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}
