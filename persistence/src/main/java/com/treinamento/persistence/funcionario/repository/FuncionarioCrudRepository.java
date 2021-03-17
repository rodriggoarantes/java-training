package com.treinamento.persistence.funcionario.repository;

import com.treinamento.persistence.funcionario.domain.Funcionario;
import com.treinamento.persistence.funcionario.domain.FuncionarioId;
import org.springframework.data.repository.CrudRepository;

public interface FuncionarioCrudRepository extends CrudRepository<Funcionario, FuncionarioId> {
}
