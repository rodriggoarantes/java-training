package com.treinamento.persistence.conta.application;

import com.treinamento.persistence.conta.domain.Conta;
import com.treinamento.persistence.conta.domain.ContaId;
import com.treinamento.persistence.conta.domain.ContaRepository;
import com.treinamento.persistence.conta.domain.ContaStatus;
import com.treinamento.persistence.titular.domain.TitularId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class ContaService {

    private final ContaRepository repository;

    @Autowired
    public ContaService(ContaRepository repository) {
        this.repository = repository;
    }

    public Conta create(Integer agencia, Integer conta, TitularId titularId) {
        final Conta novaConta = Conta.builder()
                                     .id(ContaId.generate())
                                     .agencia(agencia)
                                     .numero(conta)
                                     .titular(titularId)
                                     .saldo(BigDecimal.ZERO)
                                     .build();
        return repository.save(novaConta);
    }

    public Conta find(ContaId id) {
        return repository.findBy(id);
    }

    public void alterarStatus(ContaId contaId, ContaStatus newStatus) {
        final Conta conta = repository.findBy(contaId);
        conta.alterarStatus(newStatus);
    }

    public void realizarDebito(ContaId contaId, BigDecimal valor) {
        final Conta conta = repository.findBy(contaId);
        conta.debitar(valor);
    }
}
