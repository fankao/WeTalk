package com.wetalk.dto;

public record RefreshToken(
        String refreshToken
) {
    public static RefreshToken of(String refreshToken) {
        return new RefreshToken(refreshToken);
    }
}
