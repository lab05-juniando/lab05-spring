package com.lab05.finances.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Exceção lançada quando há violação de regra de negócio.
 * Mapeia automaticamente para HTTP 400 Bad Request.
 */
public class BusinessException extends ApplicationException {

    public BusinessException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, HttpStatus.BAD_REQUEST, cause);
    }
}
