package com.treinamento.persistence.conta;

import com.treinamento.persistence.conta.domain.Conta;
import com.treinamento.persistence.conta.domain.ContaId;
import com.treinamento.persistence.movimentacao.domain.TipoMovimentacao;
import com.treinamento.persistence.titular.domain.TitularId;

import java.math.BigDecimal;

public final class ContaTestFactory {
    private ContaTestFactory() {}

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

    public static Conta umaContaComMovimentacoes() {
        final var conta = umaContaBuilder().build();
        conta.adicionarMovimentacao("mov1", TipoMovimentacao.CREDITO, BigDecimal.TEN);
        conta.adicionarMovimentacao("mov2", TipoMovimentacao.CREDITO, BigDecimal.TEN);
        conta.adicionarMovimentacao("mov3", TipoMovimentacao.DEBITO, BigDecimal.TEN);
        conta.adicionarMovimentacao("mov4", TipoMovimentacao.DEBITO, BigDecimal.TEN);
        return conta;
    }
}
