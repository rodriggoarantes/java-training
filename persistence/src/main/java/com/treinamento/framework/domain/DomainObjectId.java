package com.treinamento.framework.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.Objects;

public abstract class DomainObjectId<T extends Serializable> implements Serializable {
    private static final long serialVersionUID = 1115372100599949901L;

    protected DomainObjectId() {
        this.id = null;
    }

    @JsonValue
    private final T id;

    @JsonCreator
    protected DomainObjectId(final T id) {
        this.id = id;
    }

    @NonNull
    public T getId() {
        return this.id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!this.getClass().equals(obj.getClass())) return false;

        final var objId = (DomainObjectId) obj;
        return Objects.equals(this.id, objId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return this.id.toString();
    }
}
