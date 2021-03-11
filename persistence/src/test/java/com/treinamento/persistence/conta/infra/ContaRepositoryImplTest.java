package com.treinamento.persistence.conta.infra;

import com.treinamento.persistence.config.ConfigIT;
import com.treinamento.persistence.conta.domain.Conta;
import com.treinamento.persistence.conta.domain.ContaId;
import com.treinamento.persistence.conta.domain.ContaRepository;
import com.treinamento.persistence.conta.domain.ContaStatus;
import com.treinamento.persistence.conta.util.ContaTestFactory;
import com.treinamento.persistence.movimentacao.domain.Categoria;
import com.treinamento.persistence.movimentacao.domain.Movimentacao;
import com.treinamento.persistence.movimentacao.domain.TipoMovimentacao;
import com.treinamento.persistence.movimentacao.infra.MovimentacaoCrudRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ContaRepositoryImplTest extends ConfigIT {

    @Autowired
    private ContaCrudRepository contaCrudRepository;
    @Autowired
    private MovimentacaoCrudRepository movimentacaoCrudRepository;

    @Autowired
    private ContaRepository repository;

    @BeforeEach
    void beforEach() {
        movimentacaoCrudRepository.deleteAll();
        contaCrudRepository.deleteAll();
    }

    @Test
    void findAll() {
        final var conta1 = ContaTestFactory.umaContaBuilder().id(ContaId.generate()).build();
        final var conta2 = ContaTestFactory.umaContaBuilder().id(ContaId.generate()).build();
        repository.save(conta1);
        repository.save(conta2);

        final List<Conta> list = repository.findAll();

        assertNotNull(list);
        assertEquals(2, list.size());
    }

    @Test
    void save() {
        repository.save(ContaTestFactory.umaContaBuilder().build());
        final var list = repository.findAll();

        assertNotNull(list);
        assertEquals(1, list.size());
    }

    @Test
    void findBy() {
        final Conta conta = repository.save(ContaTestFactory.umaContaBuilder().build());

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
        final Conta conta = repository.save(ContaTestFactory.umaContaBuilder()
                                                            .saldo(BigDecimal.valueOf(100))
                                                            .build());

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

        final var conta1 = repository.save(ContaTestFactory.umaContaBuilder().build());
        movimentacaoCrudRepository.save(
                Movimentacao.builder().descricao("CONTA1").tipo(TipoMovimentacao.DEBITO).conta(conta1).build());

        final var conta2 = repository.save(ContaTestFactory.umaContaBuilder().build());
        movimentacaoCrudRepository.save(
                Movimentacao.builder().descricao("CONTA2").tipo(TipoMovimentacao.CREDITO).conta(conta2).build());
        movimentacaoCrudRepository.save(
                Movimentacao.builder().descricao("CONTA2").tipo(TipoMovimentacao.DEBITO).conta(conta2).build());

        final List<Conta> lista = repository.findWithTipoMovimentacao(TipoMovimentacao.CREDITO);

        assertNotNull(lista);
        assertEquals(1, lista.size());

        final var movimentacoes = lista.get(0).getMovimentacoes();
        assertNotNull(movimentacoes);
        assertEquals(2, movimentacoes.size());

        final var mov1 = movimentacoes.get(0);
        assertNotNull(mov1);
        assertEquals("CONTA2", mov1.getDescricao());
    }

    @Test
    void findWithMovimentacoes() {
        final var movimentacaoDescricao = "CONTAA";
        final var movimentacaoTipo = TipoMovimentacao.CREDITO;
        final Conta conta = ContaTestFactory.umaContaBuilder().build();
        conta.adicionarMovimentacao(movimentacaoDescricao, movimentacaoTipo);

        repository.save(conta);

        final List<Conta> lista = repository.findWithMovimentacoes();
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

    @Test
    void findWithCategoria() {

        // given
        final var conta0 = repository.save(ContaTestFactory.umaContaBuilder().build());
        final var movimentacao1 = Movimentacao.builder().descricao("CONTA0").conta(conta0).tipo(TipoMovimentacao.DEBITO).build();
        movimentacao1.adicionarCategoria(new Categoria("mov1"));
        movimentacaoCrudRepository.save(movimentacao1);

        final var conta1 = repository.save(ContaTestFactory.umaContaBuilder().agencia(456).numero(456).build());
        final var movimentacao2 = Movimentacao.builder().descricao("CONTA1").conta(conta1).tipo(TipoMovimentacao.DEBITO).build();
        movimentacao2.adicionarCategoria(new Categoria("mov1"));
        movimentacao2.adicionarCategoria(new Categoria("mov2"));
        movimentacaoCrudRepository.save(movimentacao2);

        // when
        final List<Conta> lista = repository.findWithCategoriaMovimentacao("mov2");

        // then
        assertNotNull(lista);
        assertEquals(1, lista.size());

        final var contaEncontrada = lista.get(0);
        assertEquals(456, contaEncontrada.getAgencia());
        assertEquals(456, contaEncontrada.getNumero());
    }

}