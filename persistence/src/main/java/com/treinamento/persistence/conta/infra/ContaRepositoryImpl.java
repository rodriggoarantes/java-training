package com.treinamento.persistence.conta.infra;

import com.treinamento.persistence.conta.domain.Conta;
import com.treinamento.persistence.conta.domain.ContaId;
import com.treinamento.persistence.conta.domain.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
                .orElseThrow(() -> new RuntimeException("Nenhuma registro encontrado"));
    }
}
