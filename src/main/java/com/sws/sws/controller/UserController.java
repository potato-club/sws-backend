package com.sws.sws.controller;

import com.sws.sws.dto.LoginRequestDto;
import com.sws.sws.dto.LoginResponseDto;
import com.sws.sws.dto.SignupRequestDto;
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


    @PostMapping("/signup")
    public ResponseEntity<String> SignUp(@RequestBody SignupRequestDto requestDto, HttpServletResponse response) {
        userService.signUp(requestDto, response);
        return ResponseEntity.ok("회원가입 완료");
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
    return userService.login(loginRequestDto, response);
}

    @GetMapping("/refresh")
    public ResponseEntity<String> refreshAT(HttpServletRequest request, HttpServletResponse response) {
        return userService.refreshAccessToken(request,response);
    }

}
