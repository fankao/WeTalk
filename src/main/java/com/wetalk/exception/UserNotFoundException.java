package com.wetalk.exception;

public class UserNotFoundException extends RuntimeException {
    private final String errMsgKey;
    private final String errorCode;

    public UserNotFoundException(ErrorCode code) {
        super(code.getErrMsgKey());
        this.errMsgKey = code.getErrMsgKey();
        this.errorCode = code.getErrCode();
    }

    public UserNotFoundException(final String message) {
        super(message);
        this.errMsgKey = ErrorCode.USER_NOT_FOUND.getErrMsgKey();
        this.errorCode = ErrorCode.USER_NOT_FOUND.getErrCode();
    }

    public String getErrMsgKey() {
        return errMsgKey;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
