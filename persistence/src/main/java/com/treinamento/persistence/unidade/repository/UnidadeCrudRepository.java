package com.treinamento.persistence.unidade.repository;

import com.treinamento.persistence.unidade.domain.Unidade;
import com.treinamento.persistence.unidade.domain.UnidadeId;
import org.springframework.data.repository.CrudRepository;

public interface UnidadeCrudRepository extends CrudRepository<Unidade, UnidadeId> { }
