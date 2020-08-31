package com.treinamento.persistence.titular.application;

import com.treinamento.persistence.titular.domain.Titular;
import com.treinamento.persistence.titular.domain.TitularId;
import com.treinamento.persistence.titular.domain.TitularRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TitularService {

    private final TitularRepository repository;

    @Autowired
    public TitularService(TitularRepository repository) {
        this.repository = repository;
    }

    public Titular create(Titular titular) {
        return repository.save(titular);
    }

    public Titular find(TitularId id) {
        return repository.findBy(id);
    }
}
