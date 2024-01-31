//package com.sws.sws.controller;
//
//import com.sws.sws.dto.user.LoginRequestDto;
//import com.sws.sws.dto.user.SignupRequestDto;
//import com.sws.sws.service.UserService;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/user")
//public class UserController {
//    private final UserService userService;
//
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }
//
//    @PostMapping("/signup")
//    public String signUp(@RequestBody SignupRequestDto signupRequestDto) {
//        userService.signUp(signupRequestDto);
//        return "회원가입 성공";
//    }
//
//    @PostMapping("/login")
//    public String login(@RequestBody LoginRequestDto loginRequestDto) {
//        try {
//            userService.login(loginRequestDto);
//            return "로그인 성공";
//        } catch (Exception e) {
//            return "로그인 실패: " + e.getMessage();
//        }
//    }
//}
