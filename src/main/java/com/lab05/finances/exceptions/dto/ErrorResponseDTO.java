package com.lab05.finances.exceptions.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;

/**
 * DTO para padronizar respostas de erro da API.
 * Fornece contexto completo do erro para debugging e logging.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponseDTO(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        String traceId
) {

    /**
     * Construtor simplificado sem traceId
     */
    public ErrorResponseDTO(LocalDateTime timestamp, int status, String error, String message, String path) {
        this(timestamp, status, error, message, path, null);
    }
}
