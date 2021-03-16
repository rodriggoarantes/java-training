package com.treinamento.persistence.titular.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.treinamento.framework.domain.DomainObjectId;

import java.util.UUID;

public class TitularId extends DomainObjectId<UUID> {
    private static final long serialVersionUID = 4926354380561033952L;

    protected TitularId() {
        super(UUID.randomUUID());
    }

    private TitularId(UUID id) {
        super(id);
    }

    public static TitularId generate() {
        return new TitularId(UUID.randomUUID());
    }

    @JsonCreator
    public static TitularId from(final String id) {
        return id != null ? new TitularId(UUID.fromString(id)) : null;
    }
}


