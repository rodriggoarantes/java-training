package com.treinamento.infra.exception;

public class NotFoundException extends BusinessException {
    private static final long serialVersionUID = -2652984264108775089L;

    public NotFoundException() {
        super("TWIT-0404", "Nenhum resultado encontrado", 404);
    }

    public NotFoundException(String detailedMessage) {
        super("TWIT-0404", "Nenhum resultado encontrado", detailedMessage, 404);
    }
}
