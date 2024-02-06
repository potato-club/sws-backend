package com.sws.sws.service;

import com.sws.sws.dto.LoginRequestDto;
import com.sws.sws.dto.LoginResponseDto;
import com.sws.sws.dto.SignupRequestDto;
import com.sws.sws.entity.UserEntity;
import com.sws.sws.enums.Level;
import com.sws.sws.enums.UserRole;
import com.sws.sws.error.ErrorCode;
import com.sws.sws.error.exception.NotFoundException;
import com.sws.sws.error.exception.UnAuthorizedException;
import com.sws.sws.jwt.JwtTokenProvider;
import com.sws.sws.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public ResponseEntity<String> signUp(SignupRequestDto signupRequestDto, HttpServletResponse response){
        if (userRepository.existsByEmail(signupRequestDto.getEmail())) {
            throw new IllegalStateException("이미 존재하는 이메일 입니다.");
        }

        if (userRepository.existsByIsDel(true)) {
            throw new IllegalStateException("가입할 수 없는 상태인 사용자가 존재합니다.");
        }

        if (userRepository.findByNickname(signupRequestDto.getNickname()).isPresent()) {
            throw new IllegalStateException("이미 존재하는 닉네임 입니다.");
        }

        UserEntity userEntity = UserEntity.builder()
                .userName(signupRequestDto.getUserName())
                .email(signupRequestDto.getEmail())
                .userRole(UserRole.USER)
                .nickname(signupRequestDto.getNickname()) // 닉네임 필드 추가
                .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                .level(Level.LEVEL1)
                .refreshToken("dummy")
                .build();

        userRepository.save(userEntity);


        String AT = jwtTokenProvider.createAccessToken(userEntity.getEmail(), userEntity.getUserRole());
        String RT = jwtTokenProvider.createRefreshToken(userEntity.getEmail(), userEntity.getUserRole());



        userEntity.setRefreshToken(RT);

        response.setHeader("Authorization","Bearer " + AT);
        response.setHeader("RefreshToken","Bearer "+ RT);

        return ResponseEntity.ok("회원가입 성공");
    }

    public ResponseEntity<LoginResponseDto> login(LoginRequestDto loginRequestDto, HttpServletResponse response){
        UserEntity userEntity = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("해당 이메일을 가진 사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), userEntity.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }
        String AT = jwtTokenProvider.createAccessToken(userEntity.getEmail(), userEntity.getUserRole());
        String RT = jwtTokenProvider.createRefreshToken(userEntity.getEmail(), userEntity.getUserRole());


        userEntity.setRefreshToken(RT);

        response.setHeader("Authorization","Bearer " + AT);
        response.setHeader("RefreshToken","Bearer "+ RT);


        return ResponseEntity.ok(new LoginResponseDto(true, "로그인 성공", AT, RT));

    }

    public ResponseEntity<String> refreshAccessToken(HttpServletRequest request, HttpServletResponse response) {
        String RT = jwtTokenProvider.resolveRefreshToken(request);

        if ( !jwtTokenProvider.validateToken(RT) ) {
            throw new UnAuthorizedException(ErrorCode.INVALID_TOKEN_EXCEPTION.getMessage(), ErrorCode.INVALID_TOKEN_EXCEPTION);
        }

        UserEntity userEntity = userRepository
                .findByEmail(jwtTokenProvider.getEmail(RT))
                .orElseThrow(()->{throw new NotFoundException(ErrorCode.NOT_FOUND_EXCEPTION, ErrorCode.RUNTIME_EXCEPTION.getMessage());});

        if ( !(RT.equals(userEntity.getRefreshToken())) ) {
            throw new UnAuthorizedException("저장된 RT와 다릅니다.",ErrorCode.INVALID_TOKEN_EXCEPTION);
        }

        response.setHeader("Authorization", "Bearer " + jwtTokenProvider.createAccessToken(userEntity.getEmail(), userEntity.getUserRole()));
        return ResponseEntity.ok("good,check header");
    }
}
