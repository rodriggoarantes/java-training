package com.treinamento.persistence.funcionario.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class Cpf {
    private final String numero;

    public static Cpf from(String numero) {
        // TODO validar cpf
        return new Cpf(numero);
    }
}
