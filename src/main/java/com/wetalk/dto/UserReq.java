package com.wetalk.dto;

public record UserReq(
        String username,
        String email,
        String password,
        String profilePicture
) {
    public static UserReq of(String username, String email, String password, String profilePicture) {
        return new UserReq(username, email, password, profilePicture);
    }
}
