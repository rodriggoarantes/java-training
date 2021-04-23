package com.treinamento.persistence.titular.domain;

import com.treinamento.framework.domain.AbstractEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "titular")
public class Titular extends AbstractEntity<TitularId> {

    private String nome;

    @Builder
    private Titular(@NonNull TitularId id,
                    @NonNull String nome) {
        this.id = id;
        this.nome = nome;
    }
}
