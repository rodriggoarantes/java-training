package com.treinamento.persistence.funcionario.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.treinamento.framework.domain.DomainObjectId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class CargoId extends DomainObjectId<UUID> {

    private static final long serialVersionUID = 7946681171875028481L;

    private CargoId(UUID id) {
        super(id);
    }

    public static CargoId generate() {
        return new CargoId(UUID.randomUUID());
    }

    @JsonCreator
    public static CargoId from(final String id) {
        return id != null ? new CargoId(UUID.fromString(id)) : null;
    }
}


