package com.treinamento.persistence.funcionario.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.treinamento.framework.domain.DomainObjectId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class FuncionarioId extends DomainObjectId<UUID> {

    private static final long serialVersionUID = -9019121710189120427L;

    private FuncionarioId(UUID id) {
        super(id);
    }

    public static FuncionarioId generate() {
        return new FuncionarioId(UUID.randomUUID());
    }

    @JsonCreator
    public static FuncionarioId from(final String id) {
        return id != null ? new FuncionarioId(UUID.fromString(id)) : null;
    }
}


