package com.treinamento.persistence.titular.domain;

import com.treinamento.persistence.movimentacao.domain.Movimentacao;

import java.util.List;

public interface TitularRepository {
    List<Titular> findAll();
    Titular save(Titular entity);
    Titular findBy(TitularId id);
}
