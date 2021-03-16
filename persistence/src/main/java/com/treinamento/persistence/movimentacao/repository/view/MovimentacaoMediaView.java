package com.treinamento.persistence.movimentacao.repository.view;

import com.treinamento.persistence.movimentacao.domain.TipoMovimentacao;

import java.math.BigDecimal;

public interface MovimentacaoMediaView {
    TipoMovimentacao getTipo();
    BigDecimal getValor();
}
