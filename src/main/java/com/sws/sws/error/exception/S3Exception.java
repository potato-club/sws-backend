package com.sws.sws.error.exception;

import com.sws.sws.error.ErrorCode;

public class S3Exception extends BusinessException{
    public S3Exception(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
