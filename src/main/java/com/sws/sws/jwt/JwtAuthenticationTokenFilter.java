package com.sws.sws.jwt;
import com.sws.sws.error.ErrorJwtCode;
import com.sws.sws.service.jwt.RedisService;
import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


import org.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;


@RequiredArgsConstructor
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        String ipAddress = request.getRemoteAddr();


        if (path.contains("/users/login") || path.contains("/users/signup"))  {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = jwtTokenProvider.resolveAccessToken(request);
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);
        ErrorJwtCode errorCode;

        try {
            if (accessToken == null && refreshToken != null) {
                if (jwtTokenProvider.validateToken(refreshToken) && redisService.isRefreshTokenValid(refreshToken, ipAddress)
                        && path.contains("/reissue")) {
                    filterChain.doFilter(request, response);
                }
            } else if (accessToken == null) {
                filterChain.doFilter(request, response);
                return;
            } else {
                if (jwtTokenProvider.validateToken(accessToken) && !redisService.isTokenInBlacklist(accessToken)) {
                    this.setAuthentication(accessToken);
                }
            }
        } catch (MalformedJwtException e) {
            errorCode = ErrorJwtCode.INVALID_JWT_TOKEN;
            setResponse(response, errorCode);
            return;
        } catch (ExpiredJwtException e) {
            errorCode = ErrorJwtCode.JWT_TOKEN_EXPIRED;
            setResponse(response, errorCode);
            return;
        } catch (UnsupportedJwtException e) {
            errorCode = ErrorJwtCode.UNSUPPORTED_JWT_TOKEN;
            setResponse(response, errorCode);
            return;
        } catch (IllegalArgumentException e) {
            errorCode = ErrorJwtCode.EMPTY_JWT_CLAIMS;
            setResponse(response, errorCode);
            return;
        } catch (JwtException e) {
            errorCode = ErrorJwtCode.JWT_SIGNATURE_MISMATCH;
            setResponse(response, errorCode);
            return;

        } catch (RuntimeException e) {
            errorCode = ErrorJwtCode.JWT_COMPLEX_ERROR;
            setResponse(response, errorCode);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void setResponse(HttpServletResponse response, ErrorJwtCode errorCode) throws IOException {
        JSONObject json = new JSONObject();
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            json.put("code", errorCode.getCode());
            json.put("message", errorCode.getMessage());
        response.getWriter().print(json);
        response.getWriter().flush();
    }

    private void setAuthentication(String token) {
        // 토큰으로부터 유저 정보를 받아옵니다.
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        // SecurityContext 에 Authentication 객체를 저장합니다.
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }



}
