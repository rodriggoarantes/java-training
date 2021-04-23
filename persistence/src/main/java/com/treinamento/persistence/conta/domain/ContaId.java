package com.treinamento.persistence.conta.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.treinamento.framework.domain.DomainObjectId;

import java.util.UUID;

public class ContaId extends DomainObjectId<UUID> {
    private static final long serialVersionUID = -2711304044127955012L;

    private ContaId(UUID id) {
        super(id);
    }

    public static ContaId generate() {
        return new ContaId(UUID.randomUUID());
    }

    @JsonCreator
    public static ContaId from(final String id) {
        return id != null ? new ContaId(UUID.fromString(id)) : null;
    }
}


