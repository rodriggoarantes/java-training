package com.treinamento.persistence.conta.infra;

import com.treinamento.persistence.conta.domain.ContaId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContaIdSequenceGenerator {

    private final ContaCrudRepository repository;

    @Autowired
    public ContaIdSequenceGenerator(ContaCrudRepository repository) {
        this.repository = repository;
    }

    public ContaId nextId() {
        final ContaId currentId = repository.findMaxId();
        return currentId == null ? ContaId.from(1) : currentId.next();
    }
}