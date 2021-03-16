package com.treinamento.persistence.conta.repository;

import com.treinamento.persistence.conta.domain.Conta;
import com.treinamento.persistence.conta.domain.ContaId;
import com.treinamento.persistence.conta.domain.ContaRepository;
import com.treinamento.persistence.movimentacao.domain.TipoMovimentacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.JoinType;
import java.util.ArrayList;
import java.util.List;


@Repository
public class ContaRepositoryImpl implements ContaRepository {

    private final ContaCrudRepository crudRepository;

    @Autowired
    public ContaRepositoryImpl(ContaCrudRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public List<Conta> findAll() {
        final List<Conta> listaContas = new ArrayList<>();
        crudRepository.findAll().forEach(listaContas::add);
        return listaContas;
    }

    @Override
    public Conta save(Conta conta) {
        return crudRepository.save(conta);
    }

    @Override
    public Conta findBy(ContaId contaId) {
        return crudRepository.findById(contaId)
                             .orElseThrow(() -> new RuntimeException("Nenhuma registro de conta foi encontrado"));
    }

    @Override
    public List<Conta> findWithMovimentacoes() {
        return crudRepository.listarComMovimentacoes();
    }

    @Override
    public List<Conta> findWithTipoMovimentacao(TipoMovimentacao tipoMovimentacao) {
        return crudRepository.findAll(withTipoMovimentacao(tipoMovimentacao));
    }

    @Override
    public List<Conta> findWithCategoriaMovimentacao(String categoria) {
        return crudRepository.findAll(withCategoriaMovimentacao(categoria));
    }

    private static Specification<Conta> withCategoriaMovimentacao(String categoria) {
        return (root, cq, cb) -> {
            final var joinMovimentacoes = root.join("movimentacoes", JoinType.INNER);
            final var joinCategorias = joinMovimentacoes.join("categorias", JoinType.INNER);
            return cb.equal(joinCategorias.get("nome"), categoria);
        };
    }

    private static Specification<Conta> withTipoMovimentacao(TipoMovimentacao tipoMovimentacao) {
        return (root, cq, cb) -> {
            final var joined = root.join("movimentacoes", JoinType.INNER);
            return cb.equal(joined.get("tipo"), tipoMovimentacao);
        };
    }

}
