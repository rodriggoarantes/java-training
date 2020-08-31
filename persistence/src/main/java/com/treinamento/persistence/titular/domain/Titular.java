package com.treinamento.persistence.titular.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
@EqualsAndHashCode
@Entity
public class Titular {

    @EmbeddedId
    private TitularId id;
    private String nome;

    public Titular(String nome) {
        this.nome = nome;
    }
}
