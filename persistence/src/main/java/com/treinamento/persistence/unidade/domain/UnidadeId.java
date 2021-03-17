package com.treinamento.persistence.unidade.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.treinamento.framework.domain.DomainObjectId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UnidadeId extends DomainObjectId<UUID> {
    private static final long serialVersionUID = -2711304044127955012L;

    private UnidadeId(UUID id) {
        super(id);
    }

    public static UnidadeId generate() {
        return new UnidadeId(UUID.randomUUID());
    }

    @JsonCreator
    public static UnidadeId from(final String id) {
        return id != null ? new UnidadeId(UUID.fromString(id)) : null;
    }
}


