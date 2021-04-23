package com.treinamento.persistence.movimentacao.repository;

import com.treinamento.persistence.config.RepositoryConfigIT;
import com.treinamento.persistence.conta.ContaTestFactory;
import com.treinamento.persistence.conta.domain.Conta;
import com.treinamento.persistence.conta.domain.ContaId;
import com.treinamento.persistence.conta.repository.ContaCrudRepository;
import com.treinamento.persistence.movimentacao.domain.TipoMovimentacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MovimentacaoCrudRepositoryTest extends RepositoryConfigIT {

    @Autowired
    private MovimentacaoCrudRepository repository;

    @Autowired
    private ContaCrudRepository contaRepository;

    private static final ContaId contaId = ContaId.generate();
    private static final Conta contaPadrao = ContaTestFactory.umaContaComMovimentacoes();

    @BeforeEach
    void setup() {
        contaRepository.save(contaPadrao);
    }


    @Test
    void findAll() {
        final var list = new ArrayList<>();
        repository.findAll().forEach(list::add);

        assertFalse(CollectionUtils.isEmpty(list));
        assertEquals(4, list.size());
    }

    @Test
    void findBy() {
        final var movimentacao = contaPadrao.getMovimentacoes().stream().findAny().orElseThrow();
        final var mov = repository.findById(movimentacao.getId());

        assertTrue(mov.isPresent());
        assertEquals(movimentacao.getId(), mov.get().getId());
        assertEquals(movimentacao.getDescricao(), mov.get().getDescricao());
        assertEquals(movimentacao.getValor().setScale(2, RoundingMode.HALF_UP), mov.get().getValor().setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    void saldo() {
        final var saldo = repository.saldo();
        assertEquals(BigDecimal.valueOf(20).setScale(2, RoundingMode.HALF_UP), saldo.setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    void media() {
        final var media = repository.media();
        assertEquals(10, media.intValue());
    }

    @Test
    void mediaAgrupada() {

        // when
        final var media = repository.mediaAgrupadoPorTipo();

        final var vinte = BigDecimal.valueOf(20).setScale(2, RoundingMode.HALF_UP);
        assertEquals(2, media.size());

        final var mediaCredito = media.stream().filter(m -> m.getTipo().equals(TipoMovimentacao.CREDITO)).findAny().orElseThrow();
        final var mediaDebito = media.stream().filter(m -> m.getTipo().equals(TipoMovimentacao.DEBITO)).findAny().orElseThrow();

        assertEquals(vinte, mediaCredito.getValor());
        assertEquals(vinte, mediaDebito.getValor());
    }


}