package com.treinamento.persistence.movimentacao.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.treinamento.framework.domain.DomainObjectId;

import java.util.UUID;

public class MovimentacaoId extends DomainObjectId<UUID> {
    private static final long serialVersionUID = -2711304044127955012L;

    private MovimentacaoId(UUID id) {
        super(id);
    }

    public static MovimentacaoId generate() {
        return new MovimentacaoId(UUID.randomUUID());
    }

    @JsonCreator
    public static MovimentacaoId from(final String id) {
        return id != null ? new MovimentacaoId(UUID.fromString(id)) : null;
    }
}


