package com.treinamento.persistence.conta;

import com.treinamento.persistence.conta.domain.Conta;
import com.treinamento.persistence.conta.domain.ContaId;
import com.treinamento.persistence.titular.domain.TitularId;

import java.math.BigDecimal;

public final class ContaTestFactory {
    private ContaTestFactory() {
    }

    public static final ContaId contaId = ContaId.generate();
    public static final Integer agencia = 123;
    public static final Integer numero = 123;
    public static final TitularId titular = TitularId.generate();

    public static Conta.ContaBuilder umaContaBuilder() {
        return Conta.builder()
                    .id(ContaId.generate())
                    .agencia(agencia)
                    .numero(numero)
                    .titular(titular)
                    .saldo(BigDecimal.ZERO);
    }
}
