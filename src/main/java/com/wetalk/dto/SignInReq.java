package com.wetalk.dto;

import java.io.Serializable;

public record SignInReq(
        String username,
        String password
) implements Serializable {
    public static SignInReq of(String username, String password) {
        return new SignInReq(username, password);
    }
}
