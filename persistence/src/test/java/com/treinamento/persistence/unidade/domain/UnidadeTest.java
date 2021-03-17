package com.treinamento.persistence.unidade.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Teste unitario para entidade Unidade")
class UnidadeTest {

    private final static Endereco endereco = Endereco.of("a", "b", "c");

    @Test
    void unidadeEqualsById() {
        final var unidadeId = UnidadeId.generate();
        final var unidade1 = Unidade.builder().id(unidadeId).descricao("a").endereco(endereco).build();
        final var unidade2 = Unidade.builder().id(unidadeId).descricao("b").endereco(endereco).build();

        assertEquals(unidade1, unidade2);
    }

}