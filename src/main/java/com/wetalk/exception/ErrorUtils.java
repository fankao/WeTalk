package com.wetalk.exception;

public class ErrorUtils {
    private static final long serialVersionUID = 1L;
    private String errorCode;
    private String message;
    private Integer status;
    private String url = "Not available";
    private String reqMethod = "Not available";

    private ErrorUtils() {
    }
    public static Error createError(final String errMsgKey,
                                    final String errorCode, final Integer httpStatusCode) {
        Error error = new Error();
        error.setMessage(errMsgKey);
        error.setErrorCode(errorCode);
        error.setStatus(httpStatusCode);
        return error;
    }
}
