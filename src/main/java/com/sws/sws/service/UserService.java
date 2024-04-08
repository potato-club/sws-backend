package com.sws.sws.service;

import com.sws.sws.dto.user.*;
import com.sws.sws.entity.FriendsEntity;
import com.sws.sws.entity.UserEntity;
import com.sws.sws.enums.FriendStatus;
import com.sws.sws.enums.UserRole;
import com.sws.sws.error.ErrorCode;
import com.sws.sws.error.exception.BadRequestException;
import com.sws.sws.error.exception.NotFoundException;
import com.sws.sws.error.exception.UnAuthorizedException;
import com.sws.sws.jwt.JwtTokenProvider;
import com.sws.sws.repository.FriendRepository;
import com.sws.sws.repository.UserRepository;
import com.sws.sws.service.jwt.RedisService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.sws.sws.error.ErrorCode.ACCESS_DENIED_EXCEPTION;
import static com.sws.sws.error.ErrorCode.NOT_FOUND_EXCEPTION;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;
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
    
    //임시 탈퇴
    public void leave(HttpServletRequest request) {
        Optional<UserEntity> user = findByUserToken(request);

        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            userEntity.checkDeleted(); 
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

    public void createFriendship(FriendRequestDto friendRequestDto, HttpServletRequest request) { // 이거는 좀 물어보고싶은게 ID값으로 보내지나? 버튼누르면?
        String fromEmail = jwtTokenProvider.getUserEmail(jwtTokenProvider.resolveAccessToken(request));

        UserEntity fromUser = userRepository.findByEmail(fromEmail)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_EXCEPTION, "유저가 존재하지않습니다."));

        UserEntity toUser = userRepository.findByEmail(friendRequestDto.getToEmail())
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_EXCEPTION, "받는 유저를 찾을 수 없습니다."));

        FriendsEntity friendFrom = FriendsEntity.builder()
                .userEntity(fromUser)
                .userEmail(fromEmail)
                .friendEmail(friendRequestDto.getToEmail())
                .status(FriendStatus.WAITING)
                .isFrom(true)
                .counterpartId(toUser.getUserId())
                .build();

        FriendsEntity friendTo = FriendsEntity.builder()
                .userEntity(toUser)
                .userEmail(friendRequestDto.getToEmail())
                .friendEmail(fromEmail)
                .status(FriendStatus.WAITING)
                .isFrom(false)
                .counterpartId(fromUser.getUserId())
                .build();

        fromUser.getFriends().add(friendTo);
        toUser.getFriends().add(friendFrom);

        friendRepository.save(friendTo);
        friendRepository.save(friendFrom);

    }

    public ResponseEntity<?> getWaitingFriendList(HttpServletRequest request) {
        UserEntity user = userRepository.findByEmail(jwtTokenProvider.getUserEmail(jwtTokenProvider.resolveAccessToken(request)))
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_EXCEPTION, "유저를 찾을 수 없습니다."));
        List<FriendsEntity> friendsEntityList = user.getFriends(); // 유저 리스트 받아오고
        List<WaitingFriendListDto> result = new ArrayList<>(); // dto로 조회된 결과 받아오구

        for (FriendsEntity x : friendsEntityList) { // 리스트 받아온거 처음부터 끝까지 for문 돌림
            // 보낸 요청이 아니고 && 수락 대기중인 요청만 조회
            if (!x.isFrom() && x.getStatus() == FriendStatus.WAITING) {
                UserEntity friend = userRepository.findByEmail(x.getFriendEmail())
                        .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_EXCEPTION, "유저를 찾을 수 없습니다."));
                WaitingFriendListDto dto = WaitingFriendListDto.builder()
                        .friendshipId(x.getId())
                        .friendEmail(friend.getEmail())
                        .friendName(friend.getUserName())
                        .status(x.getStatus())
//                        .imageUrl(friend) // 이미지 왜없지
                        .build();
                result.add(dto);
            }
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public void approveFriendRequest(Long friendId, HttpServletRequest request) {

        // 입력받은 아이디값이 뭔데? 친구요청한 녀석의 id값
        // 그럼 그 친구요청한 녀석의 상대방 id값과 token을 넣은 id값이 일치하면 하면되겠따

        FriendsEntity friends = friendRepository.findById(friendId) // 보낸사람
                .orElseThrow(() -> new BadRequestException("잘못된 요청입니다.", NOT_FOUND_EXCEPTION));

        FriendsEntity counterpart = friendRepository.findById(friends.getCounterpartId()) // 받은사람
                .orElseThrow(() -> new BadRequestException("잘못된 요청입니다.", NOT_FOUND_EXCEPTION));

        Long responseUserId = findByUserToken(request).get().getUserId();

        if (counterpart.getUserEntity().getUserId() == responseUserId) {
            counterpart.acceptFriendshipRequest();
            friends.acceptFriendshipRequest();
        } else {
            throw new BadRequestException("잘못된 요청입니다.",NOT_FOUND_EXCEPTION);
        }
    }

}

