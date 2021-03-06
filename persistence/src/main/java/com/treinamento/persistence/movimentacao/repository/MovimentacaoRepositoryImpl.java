package com.treinamento.persistence.movimentacao.repository;

import com.treinamento.persistence.movimentacao.domain.Movimentacao;
import com.treinamento.persistence.movimentacao.domain.MovimentacaoId;
import com.treinamento.persistence.movimentacao.domain.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Repository
public class MovimentacaoRepositoryImpl implements MovimentacaoRepository {

    private final MovimentacaoCrudRepository crudRepository;

    @Autowired
    public MovimentacaoRepositoryImpl(MovimentacaoCrudRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public List<Movimentacao> findAll() {
        final List<Movimentacao> list = new ArrayList<>();
        crudRepository.findAll().forEach(list::add);
        return list;
    }

    @Override
    public Movimentacao save(Movimentacao entity) {
        return crudRepository.save(entity);
    }

    @Override
    public Movimentacao findBy(MovimentacaoId id) {
        return crudRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nenhum registro encontrado"));
    }

    @Override
    public BigDecimal saldo() {
        return crudRepository.saldo();
    }
}
