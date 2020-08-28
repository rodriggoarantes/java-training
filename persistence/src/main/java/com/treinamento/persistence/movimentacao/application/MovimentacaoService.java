package com.treinamento.persistence.movimentacao.application;

import com.treinamento.persistence.movimentacao.domain.Movimentacao;
import com.treinamento.persistence.movimentacao.domain.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MovimentacaoService {
    private final MovimentacaoRepository repository;

    @Autowired
    public MovimentacaoService(MovimentacaoRepository repository) {
        this.repository = repository;
    }

    public Movimentacao create() {
        final Movimentacao creation = new Movimentacao();
        return repository.save(creation);
    }

    public List<Movimentacao> all() {
        return repository.findAll();
    }

    public Movimentacao find(Long id) {
        return repository.findBy(id);
    }

}


