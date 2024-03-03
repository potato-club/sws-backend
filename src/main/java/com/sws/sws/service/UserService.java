package com.sws.sws.service;

import com.sws.sws.dto.user.*;
import com.sws.sws.entity.UserEntity;
import com.sws.sws.enums.UserRole;
import com.sws.sws.error.ErrorCode;
import com.sws.sws.error.exception.UnAuthorizedException;
import com.sws.sws.jwt.JwtTokenProvider;
import com.sws.sws.repository.UserRepository;
import com.sws.sws.service.jwt.RedisService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;

import static com.sws.sws.error.ErrorCode.ACCESS_DENIED_EXCEPTION;
import static com.sws.sws.error.ErrorCode.NOT_FOUND_EXCEPTION;


@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;


    public void signUp(SignupRequestDto requestDto) {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new UnAuthorizedException("401", ACCESS_DENIED_EXCEPTION);
        }
        if (userRepository.existsByNickname(requestDto.getNickname())) {
            throw new UnAuthorizedException("401", ACCESS_DENIED_EXCEPTION);
        }
        //카카오 로그인 로직 추후 추가
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        UserEntity userEntity = requestDto.toEntity();
        userRepository.save(userEntity);
    }


    public LoginResponseDto login(LoginRequestDto requestDto, HttpServletResponse response) {
        UserEntity userEntity = userRepository.findByEmail(requestDto.getEmail()).orElseThrow();

        // 탈퇴한 사용자인지 확인
        if (Boolean.TRUE.equals(userEntity.getIsDel())) {
            throw new UnAuthorizedException("404", NOT_FOUND_EXCEPTION);
        }

        //패스워드 다를 때
        if (!passwordEncoder.matches(requestDto.getPassword(), userEntity.getPassword())) {
            throw new UnAuthorizedException("401", ACCESS_DENIED_EXCEPTION);
        }

        this.setJwtTokenInHeader(requestDto.getEmail(), response);

        return LoginResponseDto.builder()
                .responseCode("200")
                .build();
    }


    public void logout(HttpServletRequest request) {
        redisService.delValues(jwtTokenProvider.resolveRefreshToken(request));
        jwtTokenProvider.expireToken(jwtTokenProvider.resolveAccessToken(request));
    }


    public void setJwtTokenInHeader(String email, HttpServletResponse response) {
        UserRole userRole = userRepository.findByEmail(email).get().getUserRole();

        String accessToken = jwtTokenProvider.createAccessToken(email, userRole);
        String refreshToken = jwtTokenProvider.createRefreshToken(email, userRole);


        jwtTokenProvider.setHeaderAT(response, accessToken);
        jwtTokenProvider.setHeaderRT(response, refreshToken);

        redisService.setValues(refreshToken, email);
    }

    //사용자 정보 조회
    public InfoResponseDto getUserInfo(HttpServletRequest request) {
        Optional<UserEntity> userEntity = findByUserToken(request);
        if (!userEntity.isPresent()) {
            throw new UnAuthorizedException("404", NOT_FOUND_EXCEPTION);
        }

        UserEntity user = userEntity.get();
        return new InfoResponseDto(user.getUserName(), user.getNickname(), user.getLevel());
    }

    //사용자 정보 수정
    public void updateUser(InfoUpdateRequestDto requestDto, HttpServletRequest request) {
        Optional<UserEntity> userOptional = findByUserToken(request);
        userOptional.ifPresent(user -> {
            if (requestDto.getUserName() != null && !requestDto.getUserName().isEmpty()
                    && requestDto.getNickname() != null && !requestDto.getNickname().isEmpty()) {

                user.update(requestDto);
            }
        });
    }


    public void reissueToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);

        String accessToken = jwtTokenProvider.resolveAccessToken(request);

        // 액세스 토큰이 요청에 포함되어 있는지 확인
        if (accessToken != null && !accessToken.isEmpty()) {
            throw new UnAuthorizedException("AccessToken은 사용할 수 없습니다.", ErrorCode.INVALID_TOKEN_EXCEPTION);
        }

        String newAccessToken = jwtTokenProvider.reissueAccessToken(refreshToken);
        String newRefreshToken = jwtTokenProvider.reissueRefreshToken(refreshToken);

        jwtTokenProvider.setHeaderAccessToken(response, newAccessToken);
        jwtTokenProvider.setHeaderRefreshToken(response, newRefreshToken);
    }

    public Optional<UserEntity> findByUserToken(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveAccessToken(request);
        String accessTokenType = jwtTokenProvider.extractTokenType(token);

        if ("refresh".equals(accessTokenType)) {
            throw new UnAuthorizedException("RefreshToken은 사용할 수 없습니다.", ErrorCode.INVALID_TOKEN_EXCEPTION);
        }

        return token == null ? null : userRepository.findByEmail(jwtTokenProvider.getEmail(token));
    }

    //탈퇴
    public void leave(HttpServletRequest request) {
        String email = jwtTokenProvider.getEmail(jwtTokenProvider.resolveAccessToken(request));

        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // 사용자 탈퇴 처리
        userEntity.leave();
        userRepository.save(userEntity);

        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);
        if (refreshToken != null && !refreshToken.isEmpty()) {
            jwtTokenProvider.expireToken(refreshToken);
        }

        String accessToken = jwtTokenProvider.resolveAccessToken(request);
        if (accessToken != null && !accessToken.isEmpty()) {
            jwtTokenProvider.expireToken(accessToken);
        }


    }

}