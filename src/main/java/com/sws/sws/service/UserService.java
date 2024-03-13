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
    public MyPageDto viewMyPage(HttpServletRequest request) {
        Optional<UserEntity> userEntity = findByUserToken(request);
        if (!userEntity.isPresent()) {
            throw new UnAuthorizedException("404", NOT_FOUND_EXCEPTION);
        }

        UserEntity user = userEntity.get();
        return MyPageDto.builder()
                .email(user.getEmail())
                .userName(user.getUserName())
                .nickname(user.getNickname())
                .userRole(user.getUserRole())
                .level(user.getLevel())
                .build();
    }

    //사용자 정보 수정
    public void updateUser(MyPageDto requestDto, HttpServletRequest request) {
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

        String newAccessToken = jwtTokenProvider.reissueAccessToken(refreshToken);
        String newRefreshToken = jwtTokenProvider.reissueRefreshToken(refreshToken);

        jwtTokenProvider.setHeaderAT(response, newAccessToken);
        jwtTokenProvider.setHeaderRT(response, newRefreshToken);
    }

    //탈퇴
    public void leave(HttpServletRequest request) {
        Optional<UserEntity> user = findByUserToken(request);

        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            userEntity.setIsDel(true);
            this.logout(request);
        } else {
            throw new UnAuthorizedException("404", NOT_FOUND_EXCEPTION);
        }
    }

    //db에서 완전히 삭제하는 탈퇴
    @Transactional
    public void delUser(HttpServletRequest request) {

        String email = jwtTokenProvider.getUserEmail(jwtTokenProvider.resolveAccessToken(request));
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);

        // 사용자 정보가 존재하지 않는 경우 예외 처리
        if (!userEntityOptional.isPresent()) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        // 사용자 데이터베이스에서 완전히 삭제
        userRepository.delete(userEntityOptional.get());


        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);
        if (refreshToken != null && !refreshToken.isEmpty()) {
            jwtTokenProvider.expireToken(refreshToken);
        }

        String accessToken = jwtTokenProvider.resolveAccessToken(request);
        if (accessToken != null && !accessToken.isEmpty()) {
            jwtTokenProvider.expireToken(accessToken);
        }


    }



    public Optional<UserEntity> findByUserToken(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveAccessToken(request);
        String accessTokenType = jwtTokenProvider.extractTokenType(token);

        if ("refresh".equals(accessTokenType)) {
            throw new UnAuthorizedException("RefreshToken은 사용할 수 없습니다.", ErrorCode.INVALID_TOKEN_EXCEPTION);
        }

        return token == null ? null : userRepository.findByEmail(jwtTokenProvider.getUserEmail(token));
    }



}

