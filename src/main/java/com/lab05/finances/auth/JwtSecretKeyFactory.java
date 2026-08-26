package com.lab05.finances.auth;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Constrói a chave HMAC a partir do secret em texto plano, no mesmo formato
 * usado pelo jsonwebtoken do Node ({@code process.env.JWT_SECRET}).
 */
final class JwtSecretKeyFactory {

    private JwtSecretKeyFactory() {
    }

    static SecretKey hmacSha256Key(String secret) {
        return new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
    }
}
