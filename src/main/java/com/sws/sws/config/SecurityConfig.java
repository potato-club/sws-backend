package com.sws.sws.config;

import com.sws.sws.jwt.JwtAuthenticationTokenFilter;
import com.sws.sws.jwt.JwtTokenProvider;
import com.sws.sws.service.jwt.RedisService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private final RedisService redisService;
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // CSRF, CORS
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(withDefaults());

        http.httpBasic(AbstractHttpConfigurer::disable);
        // 폼로그인 disable
        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);
        // 자체 회원가입, 로그인 permitAll 설정
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/signup", "/login", "/reissue", "/client/**").permitAll()
                .anyRequest().authenticated()
        );

        http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS));

        http.addFilterBefore(new JwtAuthenticationTokenFilter(jwtTokenProvider, redisService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
