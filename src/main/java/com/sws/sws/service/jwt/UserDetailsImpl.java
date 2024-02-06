package com.sws.sws.service.jwt;

import com.sws.sws.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private final UserEntity userEntity;
    @Override
    @Transactional
//    사용자 권한 처리
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> userRole = new ArrayList<>();
        userRole.add(userEntity.getUserRole().toString());
        String authority = userRole.toString();

        SimpleGrantedAuthority simpleAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleAuthority);

        return authorities;
    }

    @Override
    public String getPassword() {
        return userEntity.getEmail();
    }

    @Override
    public String getUsername() {
        return userEntity.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
