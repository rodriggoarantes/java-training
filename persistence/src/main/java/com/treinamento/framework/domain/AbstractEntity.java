package com.treinamento.framework.domain;

import org.springframework.lang.NonNull;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
public abstract class AbstractEntity<K extends DomainObjectId<?>> {

    @Id
    protected K id;

    protected AbstractEntity() {}

    protected AbstractEntity(@NonNull K id) {
        this.id = Objects.requireNonNull(id, "The identifier cannot be null!");
    }

    public K getId() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof AbstractEntity)) return false;

        final var that = (AbstractEntity<?>) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Id{" + id + '}';
    }
}
