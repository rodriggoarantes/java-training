package com.treinamento.persistence.funcionario.repository;

import com.treinamento.persistence.funcionario.domain.Cargo;
import com.treinamento.persistence.funcionario.domain.CargoId;
import com.treinamento.persistence.funcionario.domain.Funcionario;
import com.treinamento.persistence.funcionario.domain.FuncionarioId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CargoCrudRepository extends CrudRepository<Cargo, CargoId> { }