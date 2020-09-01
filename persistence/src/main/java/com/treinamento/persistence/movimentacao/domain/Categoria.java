package com.treinamento.persistence.movimentacao.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
public class Categoria {

    @Id
    @GeneratedValue
    private Long id;
    private String nome;

    public Categoria(String nome) {
        this.nome = nome;
    }
}