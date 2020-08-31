package com.treinamento.persistence.movimentacao.application;

import com.treinamento.persistence.conta.application.ContaService;
import com.treinamento.persistence.conta.domain.Conta;
import com.treinamento.persistence.movimentacao.domain.Movimentacao;
import com.treinamento.persistence.movimentacao.domain.MovimentacaoRepository;
import com.treinamento.persistence.movimentacao.domain.TipoMovimentacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MovimentacaoService {

    private final MovimentacaoRepository repository;
    private final ContaService contaService;

    @Autowired
    public MovimentacaoService(MovimentacaoRepository repository,
                               ContaService contaService) {
        this.repository = repository;
        this.contaService = contaService;
    }

    public Movimentacao create(Long contaId, TipoMovimentacao tipo, String descricao) {
        final Conta conta = contaService.find(contaId);
        final Movimentacao creation = new Movimentacao(conta, tipo, descricao);
        return repository.save(creation);
    }

    public List<Movimentacao> all() {
        return repository.findAll();
    }

    public Movimentacao find(Long id) {
        return repository.findBy(id);
    }

}


