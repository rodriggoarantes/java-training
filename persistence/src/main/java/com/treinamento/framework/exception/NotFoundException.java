package com.treinamento.framework.exception;

public class NotFoundException extends BusinessException {
    private static final long serialVersionUID = -2652984264108775089L;

    public NotFoundException() {
        super("0404", "Nenhum resultado encontrado", 404);
    }

    public NotFoundException(String detailedMessage) {
        super("0404", "Nenhum resultado encontrado", detailedMessage, 404);
    }
}
