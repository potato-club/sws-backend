package com.sws.sws.error.exception;

import com.sws.sws.error.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessException  extends RuntimeException{
    private final ErrorCode errorCode;

    public BusinessException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}