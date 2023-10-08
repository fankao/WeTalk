package com.wetalk.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.*;

import java.io.Serializable;

/**
 * properties:
 *         refreshToken:
 *           description: Refresh Token
 *           type: string
 *         accessToken:
 *           description: JWT Token aka access token
 *           type: string
 *         username:
 *           description: User Name
 *           type: string
 *         userId:
 *           description: User Identifier
 *           type: string
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignedInUser implements Serializable {
    private String refreshToken;
    private String accessToken;
    private String username;
    private Long userId;
    private String password;

    public SignedInUser refreshToken(String refreshToken) {
        this.setRefreshToken(refreshToken);
        return this;
    }
}
