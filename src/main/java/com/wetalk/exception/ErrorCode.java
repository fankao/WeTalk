package com.wetalk.exception;

public enum ErrorCode {
    // Internal Errors: 1 to 0999
    GENERIC_ERROR(
            "WETALK-0001", "The system is unable to complete the request. Contact system support."),
    HTTP_MEDIATYPE_NOT_SUPPORTED(
            "WETALK-0002",
            "Requested media type is not supported. Please use application/json or application/xml as 'Content-Type' header value"),
    HTTP_MESSAGE_NOT_WRITABLE("WETALK-0003", "Missing 'Accept' header. Please add 'Accept' header."),
    HTTP_MEDIA_TYPE_NOT_ACCEPTABLE(
            "WETALK-0004",
            "Requested 'Accept' header value is not supported. Please use application/json or application/xml as 'Accept' value"),
    JSON_PARSE_ERROR("WETALK-0005", "Make sure request payload should be a valid JSON object."),
    HTTP_MESSAGE_NOT_READABLE(
            "WETALK-0006",
            "Make sure request payload should be a valid JSON or XML object according to 'Content-Type'."),
    HTTP_REQUEST_METHOD_NOT_SUPPORTED("WETALK-0007", "Request method not supported."),
    CONSTRAINT_VIOLATION("WETALK-0008", "Validation failed."),
    ILLEGAL_ARGUMENT_EXCEPTION("WETALK-0009", "Invalid data passed."),
    RESOURCE_NOT_FOUND("WETALK-0010", "Requested resource not found."),
    USER_NOT_FOUND("WETALK-0011", "Requested user not found."),
    GENERIC_ALREADY_EXISTS("WETALK-0013", "Already exists."),
    ACCESS_DENIED("WETALK-0014", "Access Denied."),
    UNAUTHORIZED("WETALK-0015", "Unauthorized");

    private final String errCode;
    private final String errMsgKey;

    ErrorCode(final String errCode, final String errMsgKey) {
        this.errCode = errCode;
        this.errMsgKey = errMsgKey;
    }

    /**
     * @return the errCode
     */
    public String getErrCode() {
        return errCode;
    }

    /**
     * @return the errMsgKey
     */
    public String getErrMsgKey() {
        return errMsgKey;
    }
}

