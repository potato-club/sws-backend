package com.sws.sws.error.exception;

import com.sws.sws.error.ErrorCode;

public class CmtNotFoundException extends BusinessException{
    public CmtNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}