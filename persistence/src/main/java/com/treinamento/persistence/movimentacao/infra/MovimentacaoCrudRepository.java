package com.treinamento.persistence.movimentacao.infra;

import com.treinamento.persistence.movimentacao.domain.Movimentacao;
import org.springframework.data.repository.CrudRepository;

interface MovimentacaoCrudRepository extends CrudRepository<Movimentacao, Long> {
}
