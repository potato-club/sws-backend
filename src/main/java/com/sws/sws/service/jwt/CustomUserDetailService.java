package com.sws.sws.service.jwt;

import com.sws.sws.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService  implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return new UserDetailsImpl(userRepository.findByEmail(userName)
                .orElseThrow(()-> new UsernameNotFoundException("존재하지 않는 회원입니다.")));
    }
}
