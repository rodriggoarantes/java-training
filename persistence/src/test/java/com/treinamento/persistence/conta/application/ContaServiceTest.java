package com.treinamento.persistence.conta.application;

import com.treinamento.persistence.conta.domain.Conta;
import com.treinamento.persistence.conta.domain.ContaStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ContaServiceTest {

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
}