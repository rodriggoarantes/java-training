package com.treinamento.persistence.conta.domain;

import com.treinamento.framework.exception.BusinessException;
import com.treinamento.persistence.conta.ContaTestFactory;
import org.hibernate.internal.util.SerializationHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Teste de dominio - Conta")
class ContaTest {

    @Test
    void contaId() {
        final var id1 = ContaId.generate();
        final var id2 = ContaId.generate();

        assertNotEquals(id1, id2);
    }

    @Test
    void contaEquals() {
        final var c1 = ContaTestFactory.umaContaBuilder().id(ContaTestFactory.contaId).build();
        final var c2 = ContaTestFactory.umaContaBuilder().id(ContaTestFactory.contaId).build();

        assertEquals(c1, c2);
    }

    @Test
    void contaIdEqualsSerializable() {
        final var id1 = ContaId.generate();
        final var id2 = ContaId.generate();

        assertNotEquals(id1, id2);
        assertNotEquals(id1.hashCode(), id2.hashCode());

        final var serialized1 = SerializationHelper.serialize(id1);
        final var serialized2 = SerializationHelper.serialize(id2);

        final var equals = Arrays.equals(serialized1, serialized2);

        assertFalse(equals);
    }

    @Test
    void contaNotEquals() {
        final var c1 = ContaTestFactory.umaContaBuilder().build();
        final var c2 = ContaTestFactory.umaContaBuilder().build();
        assertNotEquals(c1, c2);
    }

    @Test
    void alterarStatus_contaCancelada() {

        final Conta novaConta = ContaTestFactory.umaContaBuilder().build();
        novaConta.alterarStatus(ContaStatus.CANCELADA);

        assertThrows(BusinessException.class, () -> novaConta.alterarStatus(ContaStatus.ATIVO));
    }

    @Test
    void alterarStatus_contaJaAnalisada() {
        final Conta novaConta = ContaTestFactory.umaContaBuilder().build();
        novaConta.alterarStatus(ContaStatus.ATIVO);

        assertThrows(BusinessException.class, () ->
                novaConta.alterarStatus(ContaStatus.ANALISE));
    }

    @Test
    void alterarStatus() {
        final var conta = ContaTestFactory.umaContaBuilder().build();
        conta.alterarStatus(ContaStatus.INATIVA);

        assertEquals(ContaStatus.INATIVA, conta.getStatus());
    }

    @Test
    void creditar() {
        final var conta = ContaTestFactory.umaContaBuilder().build();

        conta.creditar(BigDecimal.valueOf(1000));
        assertEquals(BigDecimal.valueOf(1000), conta.getSaldo());
    }

    @Test
    void debitar() {
        final var conta = ContaTestFactory.umaContaBuilder()
                                          .saldo(BigDecimal.valueOf(500))
                                          .build();

        conta.debitar(BigDecimal.valueOf(500));
        assertEquals(BigDecimal.ZERO, conta.getSaldo());
    }
}