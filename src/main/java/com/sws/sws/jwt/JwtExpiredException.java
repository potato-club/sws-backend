package com.sws.sws.jwt;

import javax.naming.AuthenticationException;

public class JwtExpiredException extends AuthenticationException {
    public JwtExpiredException(String message) {

        super(message);
    }
}
