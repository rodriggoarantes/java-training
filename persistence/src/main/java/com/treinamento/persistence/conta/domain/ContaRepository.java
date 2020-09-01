package com.treinamento.persistence.conta.domain;

import java.util.List;

public interface ContaRepository {
    List<Conta> findAll();
    Conta save(Conta issue);
    Conta findBy(ContaId id);

    List<Conta> listarComMovimentacoes();
}
