package com.sws.sws.controller;


import com.sws.sws.dto.user.*;

import com.sws.sws.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    //회원가입 api
    @PostMapping("/signup")
    @Operation(summary = "회원가입")
    public ResponseEntity<String> signUp(@RequestBody SignupRequestDto requestDto) {
        userService.signUp(requestDto);
        return ResponseEntity.ok("회원가입 완료");
    }


    //로그인 api
    @PostMapping("/login")
    @Operation(summary = "로그인")
    public LoginResponseDto login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
        return userService.login(requestDto, response);
    }


    //로그아웃 api
    @PostMapping("/logout")
    @Operation(summary = "로그아웃")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        userService.logout(request);
        return ResponseEntity.ok("로그아웃되었습니다.");
    }

    //사용자 정보 조회 api
    @GetMapping("/info")
    @Operation(summary = "유저정보 조회")
    public ResponseEntity<InfoResponseDto> getUserInfo(HttpServletRequest request) {
        InfoResponseDto userInfo = userService.getUserInfo(request);
        return ResponseEntity.ok(userInfo);
    }

    //사용자 정보 수정 api
    @PostMapping("/update")  // api 이름 고민해보자
    @Operation(summary = "유저정보 수정")
    public ResponseEntity<String> updateUser(@RequestBody InfoUpdateRequestDto requestDto, HttpServletRequest request) {
        try {
            userService.updateUser(requestDto, request);
            return new ResponseEntity<>("정보 수정 완료", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("정보 수정 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //토큰 재발급 api
    @GetMapping("/reissue")
    @Operation(summary = "토큰 재발급")
    public ResponseEntity<String> reissueToken(HttpServletRequest request, HttpServletResponse response) {
        userService.reissueToken(request, response);
        return ResponseEntity.ok("토큰 재발급이 완료되었습니다");
    }

    @GetMapping("/check") // 추후 수정
    @Operation(summary = "???")
    public ResponseEntity<String> validateToken() {
        return ResponseEntity.ok("Accessed Token");
    }


}