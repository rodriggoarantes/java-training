package com.treinamento.persistence.conta.infra;

import com.treinamento.persistence.config.RepositoryConfigIT;
import com.treinamento.persistence.conta.domain.Conta;
import com.treinamento.persistence.conta.domain.ContaId;
import com.treinamento.persistence.conta.domain.ContaRepository;
import com.treinamento.persistence.conta.domain.ContaStatus;
import com.treinamento.persistence.conta.ContaTestFactory;
import com.treinamento.persistence.movimentacao.domain.Categoria;
import com.treinamento.persistence.movimentacao.domain.Movimentacao;
import com.treinamento.persistence.movimentacao.domain.TipoMovimentacao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ContaRepositoryImplTest extends RepositoryConfigIT {

    @Autowired
    private ContaRepository repository;

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
        conta1.adicionarMovimentacao("CONTA1", TipoMovimentacao.DEBITO);
        repository.save(conta1);

        final var conta2 = repository.save(ContaTestFactory.umaContaBuilder().build());
        conta2.adicionarMovimentacao("CONTA2", TipoMovimentacao.CREDITO);
        conta2.adicionarMovimentacao("CONTA2", TipoMovimentacao.DEBITO);
        repository.save(conta2);

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
        // given
        final var movimentacaoDescricao = "CONTAA";
        final var movimentacaoTipo = TipoMovimentacao.CREDITO;
        final Conta conta = ContaTestFactory.umaContaBuilder().build();
        conta.adicionarMovimentacao(movimentacaoDescricao, movimentacaoTipo);
        repository.save(conta);

        // when
        final List<Conta> lista = repository.findWithMovimentacoes();

        // then
        assertNotNull(lista);
        assertEquals(1, lista.size());

        final var conta0  = lista.get(0);
        assertNotNull(conta0);
        assertNotNull(conta0.getMovimentacoes());
        assertNotNull(conta0.getMovimentacoes().get(0));

        final Movimentacao movListado = conta0.getMovimentacoes().get(0);
        assertEquals(movimentacaoDescricao, movListado.getDescricao());
        assertEquals(movimentacaoTipo, movListado.getTipo());
    }

    @Test
    void findWithCategoria() {

        // given
        final var conta0 = ContaTestFactory.umaContaBuilder().build();
        conta0.adicionarMovimentacao("movimentacao1:conta0", TipoMovimentacao.DEBITO, Set.of(new Categoria("alpha")));
        repository.save(conta0);

        final var conta1 = ContaTestFactory.umaContaBuilder().agencia(456).numero(456).build();
        conta1.adicionarMovimentacao("movimentacao2:conta1", TipoMovimentacao.CREDITO, Set.of(new Categoria("beta"),
                                                                                              new Categoria("gama")));
        repository.save(conta1);

        // when
        final List<Conta> lista = repository.findWithCategoriaMovimentacao("gama");

        // then
        assertNotNull(lista);
        assertEquals(1, lista.size());

        final var contaEncontrada = lista.get(0);
        assertEquals(456, contaEncontrada.getAgencia());
        assertEquals(456, contaEncontrada.getNumero());
    }

}