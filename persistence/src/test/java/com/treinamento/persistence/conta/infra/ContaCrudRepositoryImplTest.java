package com.treinamento.persistence.conta.infra;

import com.treinamento.persistence.config.RepositoryConfigIT;
import com.treinamento.persistence.conta.ContaTestFactory;
import com.treinamento.persistence.conta.domain.Conta;
import com.treinamento.persistence.conta.domain.ContaId;
import com.treinamento.persistence.conta.domain.ContaStatus;
import com.treinamento.persistence.movimentacao.domain.Movimentacao;
import com.treinamento.persistence.movimentacao.domain.TipoMovimentacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ContaCrudRepositoryImplTest extends RepositoryConfigIT {

    @Autowired
    private ContaCrudRepository contaCrudRepository;

    @BeforeEach
    void beforEach() {
        contaCrudRepository.deleteAll();
    }

    @Test
    void findAll() {
        final var conta1 = ContaTestFactory.umaContaBuilder().id(ContaId.generate()).build();
        final var conta2 = ContaTestFactory.umaContaBuilder().id(ContaId.generate()).build();
        contaCrudRepository.save(conta1);
        contaCrudRepository.save(conta2);

        final List<Conta> list = new ArrayList<>(2);
        contaCrudRepository.findAll().forEach(list::add);

        assertNotNull(list);
        assertEquals(2, list.size());
    }

    @Test
    void save() {
        contaCrudRepository.save(ContaTestFactory.umaContaBuilder().build());
        final List<Conta> list = new ArrayList<>(1);
        contaCrudRepository.findAll().forEach(list::add);

        assertNotNull(list);
        assertEquals(1, list.size());
    }

    @Test
    void findBy() {
        final Conta conta = contaCrudRepository.save(ContaTestFactory.umaContaBuilder().build());

        final Optional<Conta> opt = contaCrudRepository.findById(conta.getId());
        assertTrue(opt.isPresent());

        final var encontrada = opt.get();
        assertNotNull(encontrada);
        assertEquals(conta.getId(), encontrada.getId());
        assertEquals(conta.getAgencia(), encontrada.getAgencia());
        assertEquals(conta.getNumero(), encontrada.getNumero());
        assertEquals(conta.getTitular(), encontrada.getTitular());
        assertEquals(conta.getSaldo().doubleValue(), encontrada.getSaldo().doubleValue());
    }

    @Test
    void update() {
        final Conta conta = contaCrudRepository.save(ContaTestFactory.umaContaBuilder()
                                                            .saldo(BigDecimal.valueOf(100))
                                                            .build());
        conta.debitar(BigDecimal.TEN);
        conta.alterarStatus(ContaStatus.INATIVA);
        contaCrudRepository.save(conta);

        final Conta encontrada = contaCrudRepository.findById(conta.getId()).orElseThrow();
        assertNotNull(encontrada);
        assertEquals(conta.getId(), encontrada.getId());
        assertEquals(90, encontrada.getSaldo().intValue());
        assertEquals(ContaStatus.INATIVA, encontrada.getStatus());
    }

    @Test
    void findWithMovimentacoes() {
        final var movimentacaoDescricao = "CONTAA";
        final var movimentacaoTipo = TipoMovimentacao.CREDITO;
        final Conta conta = ContaTestFactory.umaContaBuilder().build();
        conta.adicionarMovimentacao(movimentacaoDescricao, movimentacaoTipo);

        contaCrudRepository.save(conta);

        final List<Conta> lista = contaCrudRepository.listarComMovimentacoes();
        assertNotNull(lista);
        assertEquals(1, lista.size());

        final var conta0  = lista.get(0);

        assertNotNull(conta0);
        assertNotNull(conta0.getMovimentacoes());
        assertNotNull(conta0.getMovimentacoes().get(0));

        final Movimentacao movListado = lista.get(0).getMovimentacoes().get(0);
        assertEquals(movimentacaoDescricao, movListado.getDescricao());
        assertEquals(movimentacaoTipo, movListado.getTipo());
    }
}