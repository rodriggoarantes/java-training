package com.treinamento.persistence.conta.repository;

import com.treinamento.persistence.conta.domain.Conta;
import com.treinamento.persistence.conta.domain.ContaId;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ContaCrudRepository extends CrudRepository<Conta, ContaId>, JpaSpecificationExecutor<Conta> {
    @Query("SELECT c FROM Conta c LEFT JOIN FETCH c.movimentacoes")
    List<Conta> listarComMovimentacoes();
}
