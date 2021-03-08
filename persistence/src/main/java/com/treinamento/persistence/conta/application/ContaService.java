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

    public Conta create(Integer agencia, Integer conta, Long titularId) {
        final Conta novaConta = new Conta(agencia, conta, new TitularId(titularId));
        return repository.save(novaConta);
    }

    public List<Conta> all() {
        return repository.findAll();
    }

    public List<Conta> listarComMovimentacoes() {
        return repository.findWithMovimentacoes();
    }

    public Conta find(Long id) {
        return repository.findBy(new ContaId(id));
    }

    public void alterarStatus(Long contaId, ContaStatus newStatus) {
        final Conta conta = repository.findBy(ContaId.from(contaId));
        conta.alterarStatus(newStatus);
    }

    public void realizarDebito(Long contaId, Double valor) {
        final Conta conta = repository.findBy(ContaId.from(contaId));
        conta.debitar(BigDecimal.valueOf(valor));
    }
}
