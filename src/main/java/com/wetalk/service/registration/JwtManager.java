package com.wetalk.service.registration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.wetalk.config.ApplicationConstants;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.stream.Collectors;

import static com.wetalk.config.ApplicationConstants.ROLE_CLAIM;

/**
 * This class is responsible for generating and validating JWT tokens.
 */
@Component
public class JwtManager {
    private final RSAPrivateKey privateKey;
    private final RSAPublicKey publicKey;

    public JwtManager(@Lazy RSAPrivateKey privateKey, @Lazy RSAPublicKey publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    /**
     * This method generates a JWT token for the given principal.
     * @param principal The principal for which the token is generated.
     * @return The generated JWT token.
     */
    public String create(UserDetails principal){
        final long now = System.currentTimeMillis();
        return JWT.create()
                // The issuer of the token
                .withIssuer("WeTalk API Server")
                // The subject of the token
                .withSubject(principal.getUsername())
                .withClaim(
                        ROLE_CLAIM,
                        principal.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .withIssuedAt(new Date(now))
                .withExpiresAt(new Date(now + ApplicationConstants.EXPIRATION_TIME))
                // Sign the token with the private key
                .sign(Algorithm.RSA256(publicKey, privateKey));

    }

}
