package com.sws.sws.error;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public enum ErrorCode {
    RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, "400", "400 Bad Request"),
    ACCESS_DENIED_EXCEPTION(HttpStatus.UNAUTHORIZED, "401", "401 UnAuthorized"),
    NOT_ALLOW_WRITE_EXCEPTION(HttpStatus.UNAUTHORIZED, "401_NOT_ALLOW", "401 UnAuthorized"),
    FORBIDDEN_EXCEPTION(HttpStatus.FORBIDDEN, "403", "403 Forbidden"),
    CMT_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "404", "comment not found"),
    CONFLICT_EXCEPTION(HttpStatus.CONFLICT, "409", "409 Conflict"),
    INVALID_TOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED, "401_Invalid", "Invalid access: token in blacklist"),
    EMAIL_DUPLICATION(HttpStatus.BAD_REQUEST, "400", "duplicated email"),
    NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "404", "user not found"),
    POST_NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, "404", "post not found"),
    JWT_EXPIRED_EXCEPTION(HttpStatus.UNAUTHORIZED, "401_Invalid", "JWT token has expired");


    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
