package com.treinamento.persistence.conta.infra;

import com.treinamento.persistence.conta.domain.Conta;
import com.treinamento.persistence.conta.domain.ContaId;
import com.treinamento.persistence.conta.domain.ContaRepository;
import com.treinamento.persistence.movimentacao.domain.TipoMovimentacao;
import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import java.awt.print.Book;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Repository
public class ContaRepositoryImpl implements ContaRepository {

    private final ContaIdSequenceGenerator sequenceGenerator;
    private final ContaCrudRepository crudRepository;

    @Autowired
    public ContaRepositoryImpl(ContaIdSequenceGenerator sequenceGenerator, ContaCrudRepository crudRepository) {
        this.sequenceGenerator = sequenceGenerator;
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
        if (conta.getId() == null || conta.getId().getContaId() == -1) {
            conta = Conta.builder()
                         .id(sequenceGenerator.nextId())
                         .agencia(conta.getAgencia()).numero(conta.getNumero())
                         .saldo(conta.getSaldo()).status(conta.getStatus())
                         .titular(conta.getTitular())
                         .build();
        }
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

    public List<Conta> findWithTipoMovimentacao(TipoMovimentacao tipoMovimentacao) {
        return crudRepository.findAll(withTipoMovimentacao(tipoMovimentacao));
    }

    private static Specification<Conta> withTipoMovimentacao(TipoMovimentacao tipoMovimentacao) {
        return (root, cq, cb) -> {
            final var joined = root.join("movimentacoes", JoinType.INNER);
            return cb.equal(joined.get("tipo"), tipoMovimentacao);
        };
    }

}
