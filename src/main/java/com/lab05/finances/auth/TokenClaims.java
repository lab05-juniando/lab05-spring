package com.lab05.finances.auth;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import io.jsonwebtoken.Claims;

public record TokenClaims(
        String userId,
        Instant expiresAt,
        Map<String, Object> raw) {

    public static TokenClaims from(Claims claims) {
        Object userIdValue = claims.get("userId");

        return new TokenClaims(
                userIdValue == null ? null : userIdValue.toString(),
                claims.getExpiration().toInstant(),
                claims);
    }

    public UUID companyId() {
        Object value = raw.get("companyId");
        if (value == null) {
            return null;
        }
        return UUID.fromString(value.toString());
    }

    public String role() {
        Object value = raw.get("role");
        return value == null ? null : value.toString();
    }
}