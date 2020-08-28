package com.treinamento.persistence.conta.domain;

import com.treinamento.framework.exception.BusinessException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ContaTest {

    @Test
    void alterarStatus_contaCancelada() {
        assertThrows(BusinessException.class, () ->
                Conta.builder().status(ContaStatus.CANCELADA).build()
                        .alterarStatus(ContaStatus.ATIVO));
    }

    @Test
    void alterarStatus_contaJaAnalisada() {
        assertThrows(BusinessException.class, () ->
                Conta.builder().status(ContaStatus.ATIVO).build()
                        .alterarStatus(ContaStatus.ANALISE));
    }

    @Test
    void alterarStatus() {
        final Conta conta = Conta.builder().status(ContaStatus.ATIVO).build();
        conta.alterarStatus(ContaStatus.INATIVA);

        assertEquals(ContaStatus.INATIVA, conta.getStatus());
    }

    @Test
    void creditar() {
        final Conta conta = Conta.builder().saldo(BigDecimal.valueOf(500)).build();
        conta.creditar(BigDecimal.valueOf(1000));
        assertEquals(BigDecimal.valueOf(1500), conta.getSaldo());
    }

    @Test
    void debitar() {
        final Conta conta = Conta.builder().saldo(BigDecimal.valueOf(500)).build();
        conta.debitar(BigDecimal.valueOf(500));
        assertEquals(BigDecimal.ZERO, conta.getSaldo());
    }
}