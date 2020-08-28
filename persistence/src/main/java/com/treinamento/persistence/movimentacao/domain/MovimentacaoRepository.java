package com.treinamento.persistence.movimentacao.domain;

import java.util.List;

public interface MovimentacaoRepository {
    List<Movimentacao> findAll();
    Movimentacao save(Movimentacao entity);
    Movimentacao findBy(Long id);
}
