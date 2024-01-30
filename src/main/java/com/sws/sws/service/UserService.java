//package com.sws.sws.service;
//
//import com.sws.sws.dto.user.LoginRequestDto;
//import com.sws.sws.dto.user.SignupRequestDto;
//import com.sws.sws.entity.UserEntity;
//import com.sws.sws.repository.UserRepository;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//@Transactional
//@RequiredArgsConstructor
//public class UserService {
//    private final PasswordEncoder passwordEncoder;
//    private final UserRepository userRepository;
//
//    public UserEntity signUp(SignupRequestDto signupRequestDto) {
//        if (userRepository.existsByUserEmail(signupRequestDto.getUserEmail())) {
//            throw new IllegalStateException("이미 존재하는 이메일입니다.");
//        }
//
//        String encryptedPassword = passwordEncoder.encode(signupRequestDto.getPassword());
//
//        UserEntity user = UserEntity.builder()
//                .userName(signupRequestDto.getUserName())
//                .userEmail(signupRequestDto.getUserEmail())
//                .password(encryptedPassword)
//                // 다른 필요한 필드 설정...
//                .build();
//
//        return userRepository.save(user);
//
//    }
//
//    public UserEntity login(LoginRequestDto loginRequestDto) throws Exception {
//        UserEntity user = userRepository.findByUserEmail(loginRequestDto.getUserEmail())
//                .orElseThrow(() -> new Exception("해당 이메일을 가진 사용자를 찾을 수 없습니다."));
//
//        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
//            throw new Exception("비밀번호가 일치하지 않습니다.");
//        }
//
//        return user;
//    }
//}
