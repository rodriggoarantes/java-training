package com.treinamento.framework.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.Objects;

public abstract class DomainObjectId<T extends Serializable> implements Serializable {
    private static final long serialVersionUID = 1115372100599949901L;

    @JsonValue
    private final T id;

    @JsonCreator
    protected DomainObjectId(@NonNull T id) {
        this.id = id;
    }

    @NonNull
    public T getId() {
        return this.id;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;

        return this.getClass().equals(obj.getClass())
                && Objects.equals(this.id, ((DomainObjectId)obj).id);
    }

    public int hashCode() {
        return Objects.hash(this.id);
    }

    public String toString() {
        return this.id.toString();
    }
}
