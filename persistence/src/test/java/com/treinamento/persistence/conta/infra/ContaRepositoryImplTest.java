package com.treinamento.persistence.conta.infra;

import com.treinamento.persistence.conta.domain.Conta;
import com.treinamento.persistence.conta.domain.ContaStatus;
import com.treinamento.persistence.movimentacao.domain.Movimentacao;
import com.treinamento.persistence.movimentacao.domain.MovimentacaoRepository;
import com.treinamento.persistence.movimentacao.domain.TipoMovimentacao;
import com.treinamento.persistence.titular.domain.TitularId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ContaRepositoryImplTest {

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;
    @Autowired
    private ContaCrudRepository crudRepository;
    @Autowired
    private ContaRepositoryImpl repository;

    @BeforeEach
    void setup() {
        crudRepository.deleteAll();
    }

    @Test
    void findAll() {
        repository.save(new Conta(123, 456, new TitularId(10L)));
        repository.save(new Conta(456, 567, new TitularId(11L)));

        final List<Conta> list = repository.findAll();

        assertNotNull(list);
        assertEquals(list.size(), 2);
    }

    @Test
    void save() {
        repository.save(new Conta(123, 456, new TitularId(10L)));
        assertEquals(1, crudRepository.count());
    }

    @Test
    void findBy() {
        final Conta conta = repository.save(Conta.builder().agencia(123).numero(123)
                .saldo(BigDecimal.valueOf(100)).titular(new TitularId(1L)).build());

        final Conta encontrada = repository.findBy(conta.getId());

        assertNotNull(encontrada);
        assertEquals(conta.getId(), encontrada.getId());
        assertEquals(conta.getAgencia(), encontrada.getAgencia());
        assertEquals(conta.getNumero(), encontrada.getNumero());
        assertEquals(conta.getTitular(), encontrada.getTitular());
        assertEquals(conta.getSaldo().doubleValue(), encontrada.getSaldo().doubleValue());
    }

    @Test
    void update() {
        final Conta conta = repository.save(Conta.builder().agencia(123).numero(123)
                .saldo(BigDecimal.valueOf(100)).status(ContaStatus.ANALISE)
                    .titular(new TitularId(1L)).build());

        conta.debitar(BigDecimal.TEN);
        conta.alterarStatus(ContaStatus.INATIVA);
        repository.save(conta);

        final Conta encontrada = repository.findBy(conta.getId());

        assertNotNull(encontrada);
        assertEquals(conta.getId(), encontrada.getId());
        assertEquals(90, encontrada.getSaldo().intValue());
        assertEquals(ContaStatus.INATIVA, encontrada.getStatus());
    }

    @Test
    void findWithTipoMovimentacao() {
        final Conta conta = repository.save(Conta.builder()
                                                 .agencia(123)
                                                 .numero(123)
                                                 .saldo(BigDecimal.valueOf(100))
                                                 .titular(new TitularId(1L))
                                                 .build());

        final Movimentacao credito = movimentacaoRepository.save(
                Movimentacao.builder().descricao("CONTAA").tipo(TipoMovimentacao.CREDITO).conta(conta).build());
        final Movimentacao debito = movimentacaoRepository.save(
                Movimentacao.builder().descricao("CONTAB").tipo(TipoMovimentacao.DEBITO).conta(conta).build());

        final List<Conta> lista = repository.findWithTipoMovimentacao(TipoMovimentacao.CREDITO);

        assertNotNull(lista);
        assertEquals(1, lista.size());

        final var movimentacoes = lista.get(0).getMovimentacoes();
        assertNotNull(movimentacoes);
        assertEquals(2, movimentacoes.size());

        final var mov1 = movimentacoes.get(0);
        assertNotNull(mov1);
        assertEquals("CONTAA", mov1.getDescricao());

    }
}