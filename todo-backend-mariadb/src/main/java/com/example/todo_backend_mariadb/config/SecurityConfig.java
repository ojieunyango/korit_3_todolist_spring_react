
package com.example.todo_backend_mariadb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import java.util.Arrays;
// Spring Security 보안 설정을 담고 있습니다.
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    // 어떤 요청이 인증(로그인)되어야 하는지 정함. 보안 필터 체인을 설정하는 메서드
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // CORS 설정을 사용하겠다는 뜻입니다.
        http.cors(cors -> corsConfigurationSource())
                // CSRF 보안은 REST API에서는 보통 꺼둡니다. (브라우저에서 폼을 쓰는 게 아니기 때문)
                .csrf(csrf -> csrf.disable())
                // 세션을 사용하지 않고, 매번 요청마다 인증 정보를 확인하겠다는 설정입니다.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 요청에 따라 접근 권한을 설정합니다.
                .authorizeHttpRequests(authz ->
                        // preflinght 요청 (OPTUION 메서드)은 인증없이 모두 허용, 브라우저가 보내는 사전 요청(OPTIONS)은 인증 없이 허용합니다. (CORS를 위해 필요)
                        authz.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                // api 관련 경로는 인증된 사용자만 접근 가능하도록 변경할겁니다
                                .requestMatchers("/api/**").authenticated()//.permitAll()에서 수정했음
                                // 로그인한 사용자만 접근 가능하도록 설정
                                .anyRequest().authenticated() //.permitAll()에서 수정했음
        ) // OAuth2 방식으로 JWT 토큰을 검사하도록 설정합니다.JWT 토큰으로 사용자 인증 처리.
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        // 설정된 보안 필터 체인을 반환합니다.
        return http.build();
    }

    // CORS 설정을 정의하는 메서드입니다.
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // 어떤 출처(도메인)에서 들어온 요청을 허용할지 정합니다.
        // 여기에 적힌 도메인만 이 서버에 요청할 수 있습니다.
        // 프론트엔드 서버(예: Vite - localhost:5173)에서 요청을 받을 수 있도록 허용.
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        // 어떤 HTTP 메서드를 허용할지 정합니다.
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        // 어떤 헤더들을 허용할지 정합니다.
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        // 인증 정보를 쿠키로 함께 보내는 것을 허용합니다.
        configuration.setAllowCredentials(true);
        // 위에서 만든 설정을 전체 경로("/**")에 적용합니다.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        // 설정을 반환합니다.
        return source;
    }
}