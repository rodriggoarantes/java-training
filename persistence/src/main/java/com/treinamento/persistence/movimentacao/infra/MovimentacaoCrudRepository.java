package com.treinamento.persistence.movimentacao.infra;

import com.treinamento.persistence.movimentacao.domain.Movimentacao;
import com.treinamento.persistence.movimentacao.domain.MovimentacaoId;
import org.springframework.data.repository.CrudRepository;

public interface MovimentacaoCrudRepository extends CrudRepository<Movimentacao, MovimentacaoId> {
}
