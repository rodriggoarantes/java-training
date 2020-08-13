package com.treinamento.transaction.work;

import com.treinamento.infra.Logger;
import com.treinamento.infra.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WorkService implements Logger {

    private final WorkRepository repository;

    @Autowired
    public WorkService(WorkRepository repository) {
        this.repository = repository;
    }

    public Iterable<Work> list() {
        return repository.findAll();
    }

    public boolean exist(final String name) {
        return repository.existsByName(name);
    }

    public void insert(Work servico) {
        this.repository.save(servico);
    }

    public void insertWithError(Work servico) {
        this.log("insertWithError");
        try {
            if (servico.getName().equals("EEE")) {
                throw new IllegalArgumentException("Erro planejado");
            }
            this.repository.save(servico);
        } catch (Exception e) {
            this.log("Erro durante a execucao");
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void runWithErrorList(final List<Work> list) {
        list.forEach(this::insertWithError);
    }

    @Transactional(rollbackFor = Exception.class)
    public void insertTransactional(Work servico) {
        this.repository.save(servico);
        if (servico.getName().equals("EEE")) {
            throw new IllegalArgumentException("Erro planejado");
        }
    }
}
