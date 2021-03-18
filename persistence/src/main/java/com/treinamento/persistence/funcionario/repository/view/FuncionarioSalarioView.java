package com.treinamento.persistence.funcionario.repository.view;

import com.treinamento.persistence.funcionario.domain.FuncionarioId;

public interface FuncionarioSalarioView {
    FuncionarioId getId();
    String getNome();
    Double getSalario();
}
