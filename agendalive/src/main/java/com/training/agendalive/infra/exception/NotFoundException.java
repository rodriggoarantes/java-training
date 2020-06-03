package com.training.agendalive.infra.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BusinessException {
    private static final long serialVersionUID = -2652984264108775089L;

    public NotFoundException() {
        super("PERSONA-0404", "Nenhum resultado encontrado", HttpStatus.NOT_FOUND.value());
    }

    public NotFoundException(String detailedMessage) {
        super("PERSONA-0404", "Nenhum resultado encontrado", detailedMessage, HttpStatus.NOT_FOUND.value());
    }
}
