package com.treinamento.persistence.conta.domain;

import com.treinamento.persistence.movimentacao.domain.TipoMovimentacao;

import java.util.List;

public interface ContaRepository {
    List<Conta> findAll();
    Conta save(Conta issue);
    Conta findBy(ContaId id);

    List<Conta> findWithMovimentacoes();
    List<Conta> findWithTipoMovimentacao(TipoMovimentacao tipoMovimentacao);
}
