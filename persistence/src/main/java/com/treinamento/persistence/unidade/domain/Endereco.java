package com.treinamento.persistence.unidade.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class Endereco {
    private String logradouro;
    private String cidade;
    private String estado;
}
