package com.treinamento.persistence.titular.infra;

import com.treinamento.persistence.titular.domain.Titular;
import com.treinamento.persistence.titular.domain.TitularId;
import org.springframework.data.repository.CrudRepository;

interface TitularCrudRepository extends CrudRepository<Titular, TitularId> {
}
