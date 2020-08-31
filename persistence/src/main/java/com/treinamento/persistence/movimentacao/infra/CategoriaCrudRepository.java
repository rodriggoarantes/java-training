package com.treinamento.persistence.movimentacao.infra;

import com.treinamento.persistence.movimentacao.domain.Categoria;
import com.treinamento.persistence.movimentacao.domain.Movimentacao;
import org.springframework.data.repository.CrudRepository;

interface CategoriaCrudRepository extends CrudRepository<Categoria, Long> {
}
