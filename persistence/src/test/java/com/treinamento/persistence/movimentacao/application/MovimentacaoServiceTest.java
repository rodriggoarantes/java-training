package com.treinamento.persistence.movimentacao.application;

import com.treinamento.persistence.conta.domain.Conta;
import com.treinamento.persistence.conta.domain.ContaRepository;
import com.treinamento.persistence.movimentacao.domain.Categoria;
import com.treinamento.persistence.movimentacao.domain.Movimentacao;
import com.treinamento.persistence.movimentacao.domain.TipoMovimentacao;
import com.treinamento.persistence.titular.domain.TitularId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MovimentacaoServiceTest {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private MovimentacaoService service;

    @Test
    void create() {
    }

    @Test
    void create_categorias() {
        final Conta conta = contaRepository.save(new Conta(1, 1, new TitularId(1L)));
        final Categoria combustivel = new Categoria("COMBUSTIVEL");

        final Movimentacao movimentacao = service.create(
                conta.getId().getContaId(), TipoMovimentacao.DEBITO, "Posto de Gasolina",
                    Collections.singleton(combustivel));

        assertNotNull(movimentacao);
    }

    @Test
    void testCreate() {
    }

    @Test
    void all() {
    }

    @Test
    void find() {
    }
}