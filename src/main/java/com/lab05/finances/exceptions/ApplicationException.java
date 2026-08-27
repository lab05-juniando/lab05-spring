package com.lab05.finances.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Classe base abstrata para exceções da aplicação.
 * Fornece uma hierarquia consistente com mapeamento automático para HttpStatus.
 */
public abstract class ApplicationException extends RuntimeException {

    private final HttpStatus status;

    public ApplicationException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public ApplicationException(String message, HttpStatus status, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
