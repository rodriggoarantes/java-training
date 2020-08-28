package com.treinamento.persistence.conta.infra;

import com.treinamento.persistence.conta.domain.Conta;
import com.treinamento.persistence.conta.domain.ContaId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ContaCrudRepository extends CrudRepository<Conta, ContaId> {
    @Query("select max(c.id) from Conta c")
    ContaId findMaxId();
}
