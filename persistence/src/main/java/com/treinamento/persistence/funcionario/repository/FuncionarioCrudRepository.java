package com.treinamento.persistence.funcionario.repository;

import com.treinamento.persistence.funcionario.domain.Funcionario;
import com.treinamento.persistence.funcionario.domain.FuncionarioId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface FuncionarioCrudRepository extends PagingAndSortingRepository<Funcionario, FuncionarioId> {

    List<Funcionario> findByNome(final String nome);

    List<Funcionario> findByNome(final String nome, final Pageable pageable);

    List<Funcionario> findByNomeStartingWith(final String prefixo);

    @Query("FROM Funcionario f WHERE f.nome like :nome AND f.salario >= :salario")
    List<Funcionario> findNomeSalarioMaior(String nome, Double salario);

    List<Funcionario> findByCargoDescricaoLike(String descricao);
}
