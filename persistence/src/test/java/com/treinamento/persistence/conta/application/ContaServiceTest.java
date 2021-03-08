package com.treinamento.persistence.conta.application;

import com.treinamento.persistence.conta.domain.Conta;
import com.treinamento.persistence.conta.domain.ContaRepository;
import com.treinamento.persistence.conta.domain.ContaStatus;
import com.treinamento.persistence.conta.infra.ContaCrudRepository;
import com.treinamento.persistence.movimentacao.domain.Movimentacao;
import com.treinamento.persistence.movimentacao.domain.MovimentacaoRepository;
import com.treinamento.persistence.movimentacao.domain.TipoMovimentacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ContaServiceTest {

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;
    @Autowired
    private ContaRepository contaRepository;
    @Autowired
    private ContaService service;

    @Test
    void alterarStatus() {
        final Conta conta = service.create(123, 345, 1L);
        service.alterarStatus(conta.getId().getContaId(), ContaStatus.ATIVO);

        final Conta alterada = service.find(conta.getId().getContaId());
        assertEquals(ContaStatus.ATIVO, alterada.getStatus());
    }

    @Test
    void realizarDebito() {
        final Conta conta = service.create(123, 345, 1L);
        service.realizarDebito(conta.getId().getContaId(), 10.0);

        final Conta alterada = service.find(conta.getId().getContaId());
        assertEquals(Double.valueOf(-10), alterada.getSaldo().doubleValue());
    }

    @Test
    void listarComMovimentacoes() {
        final Conta conta = service.create(123, 345, 1L);
        final Movimentacao movimentacao = movimentacaoRepository.save(
                Movimentacao.builder().descricao("CONTAA").tipo(TipoMovimentacao.CREDITO).conta(conta).build());

        final List<Conta> lista = service.listarComMovimentacoes();

        assertNotNull(lista);
        assertEquals(1, lista.size());

        assertNotNull(lista.get(0).getMovimentacoes());
        assertNotNull(lista.get(0).getMovimentacoes().get(0));

        final Movimentacao movListado = lista.get(0).getMovimentacoes().get(0);
        assertEquals(movimentacao.getDescricao(), movListado.getDescricao());
        assertEquals(movimentacao.getTipo(), movListado.getTipo());
    }
}