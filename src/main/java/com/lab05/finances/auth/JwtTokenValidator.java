package com.lab05.finances.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

public class JwtTokenValidator {

    public static final String REQUEST_ATTRIBUTE = "jwtClaims";

    private final JwtProperties properties;

    public JwtTokenValidator(JwtProperties properties) {
        this.properties = properties;
    }

    /**
     * Valida assinatura e expiração localmente, usando o mesmo secret do serviço Node.
     * Compatível com tokens emitidos por jsonwebtoken (HS256 + secret em texto plano).
     */
    public TokenClaims validate(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(JwtSecretKeyFactory.hmacSha256Key(properties.getSecret()))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return TokenClaims.from(claims);
        } catch (JwtException | IllegalArgumentException ex) {
            throw new InvalidTokenException("Token inválido ou expirado", ex);
        }
    }
}