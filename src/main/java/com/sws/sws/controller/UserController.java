package com.sws.sws.controller;


import com.sws.sws.dto.user.*;

import com.sws.sws.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
@Tag(name = "User Controller", description = "User API")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입")
    public ResponseEntity<String> signUp(@RequestBody SignupRequestDto requestDto) {
        userService.signUp(requestDto);
        return ResponseEntity.ok("회원가입 완료");
    }

    @PostMapping("/login")
    @Operation(summary = "로그인")
    public LoginResponseDto login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
        return userService.login(requestDto, response);
    }

    @GetMapping("/logout")
    @Operation(summary = "로그아웃")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        userService.logout(request);
        return ResponseEntity.ok("로그아웃되었습니다.");
    }

    @GetMapping("/myPage")
    @Operation(summary = "마이페이지")
    public ResponseEntity<MyPageDto> getUserInfo(HttpServletRequest request) {
        MyPageDto userInfo = userService.viewMyPage(request);
        return ResponseEntity.ok(userInfo);
    }

    @PostMapping("/updateUser")
    @Operation(summary = "유저정보수정")
    public ResponseEntity<String> updateUser(@RequestBody MyPageDto requestDto, HttpServletRequest request) {
        try {
            userService.updateUser(requestDto, request);
            return new ResponseEntity<>("정보 수정 완료", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("정보 수정 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/reissue")
    @Operation(summary = "토큰 재발급")
    public ResponseEntity<String> reissueToken(HttpServletRequest request, HttpServletResponse response) {
        userService.reissueToken(request, response);
        return ResponseEntity.ok("토큰 재발급이 완료되었습니다");
    }

    @PostMapping("/leave")
    @Operation(summary = "회원탈퇴")
    public ResponseEntity<?> leave(HttpServletRequest request) {
        userService.leave(request);
        return ResponseEntity.ok().body("탈퇴 처리되었습니다.");
    }

    @PostMapping("/delUser")
    @Operation(summary = "???")
    public ResponseEntity<?> delUser(HttpServletRequest request) {
        userService.delUser(request);
        return ResponseEntity.ok().body("영구 탈퇴 처리되었습니다.");
    }

    @PostMapping("/friends")
    @Operation(summary = "친구추가 요청 api")
    public ResponseEntity<String> sendFriendRequest(@RequestBody FriendRequestDto toEmail, HttpServletRequest request) {
        userService.createFriendship(toEmail, request);
        return ResponseEntity.ok().body("친구추가 요청이 전송되었습니다.");
    }

    @GetMapping("/friends/received")
    @Operation(summary = "친구요청 목록 조회")
    public ResponseEntity<?> getWaitingFriendsInfo(HttpServletRequest request) {
        return userService.getWaitingFriendList(request);
    }

    @PostMapping("/friends/approve/{id}") // 받은사람만 요청을 받을 수 있게 수정해야하고 다시 수락버튼 누르면 오류처리
    @Operation(summary = "친구 요청 수락")
    public ResponseEntity<String> approveFriendRequest(@PathVariable("id") Long id) {
        userService.approveFriendRequest(id);
        return ResponseEntity.ok().body("친구 요청이 수락되었습니다.");
    }




}