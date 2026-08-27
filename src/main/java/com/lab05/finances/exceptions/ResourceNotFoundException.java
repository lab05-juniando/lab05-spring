package com.lab05.finances.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Exceção lançada quando um recurso solicitado não é encontrado.
 * Mapeia automaticamente para HTTP 404 Not Found.
 */
public class ResourceNotFoundException extends ApplicationException {

    public ResourceNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(
            String.format("%s não encontrado com %s: %s", resourceName, fieldName, fieldValue),
            HttpStatus.NOT_FOUND
        );
    }

    public ResourceNotFoundException(String resourceName, Long id) {
        super(
            String.format("%s com ID %d não encontrado", resourceName, id),
            HttpStatus.NOT_FOUND
        );
    }

    public ResourceNotFoundException(String resourceName, Object id) {
        super(
            String.format("%s com ID %s não encontrado", resourceName, id),
            HttpStatus.NOT_FOUND
        );
    }
}
