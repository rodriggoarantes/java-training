package com.treinamento.persistence.movimentacao.repository;

import com.treinamento.persistence.movimentacao.domain.Movimentacao;
import com.treinamento.persistence.movimentacao.domain.MovimentacaoId;
import com.treinamento.persistence.movimentacao.repository.view.MovimentacaoMediaView;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.Collection;

public interface MovimentacaoCrudRepository extends CrudRepository<Movimentacao, MovimentacaoId> {

    @Query("select sum(m.valor) from Movimentacao m where m.tipo = 'CREDITO'")
    BigDecimal saldo();

    @Query("select avg(m.valor) from Movimentacao m where m.tipo = 'CREDITO'")
    BigDecimal media();

    @Query("select sum(m.valor) as valor, m.tipo as tipo from Movimentacao m group by m.tipo")
    Collection<MovimentacaoMediaView> mediaAgrupadoPorTipo();
}
