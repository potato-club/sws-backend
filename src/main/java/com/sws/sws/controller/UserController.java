package com.sws.sws.controller;


import com.sws.sws.dto.user.LoginResponseDto;

import com.sws.sws.dto.user.LoginRequestDto;
import com.sws.sws.dto.user.SignupRequestDto;
import com.sws.sws.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //회원가입 api
    @PostMapping("/signup")
    public ResponseEntity<String> SignUp(@RequestBody SignupRequestDto requestDto, HttpServletResponse response) {
        userService.signUp(requestDto, response);
        return ResponseEntity.ok("회원가입 완료");
    }


    //로그인 api
    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
        return userService.login(requestDto, response);
    }


    //로그아웃 api
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        userService.logout(request);
        return ResponseEntity.ok("로그아웃되었습니다.");
    }

}
