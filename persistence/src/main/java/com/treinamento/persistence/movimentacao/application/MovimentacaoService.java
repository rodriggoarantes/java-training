package com.treinamento.persistence.movimentacao.application;

import com.treinamento.persistence.conta.application.ContaService;
import com.treinamento.persistence.conta.domain.Conta;
import com.treinamento.persistence.movimentacao.domain.Categoria;
import com.treinamento.persistence.movimentacao.domain.CategoriaRepository;
import com.treinamento.persistence.movimentacao.domain.Movimentacao;
import com.treinamento.persistence.movimentacao.domain.MovimentacaoRepository;
import com.treinamento.persistence.movimentacao.domain.TipoMovimentacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class MovimentacaoService {

    private final MovimentacaoRepository repository;
    private final CategoriaRepository categoriaRepository;
    private final ContaService contaService;

    @Autowired
    public MovimentacaoService(MovimentacaoRepository repository,
                               CategoriaRepository categoriaRepository,
                               ContaService contaService) {
        this.repository = repository;
        this.categoriaRepository = categoriaRepository;
        this.contaService = contaService;
    }

    public Movimentacao create(Long contaId, TipoMovimentacao tipo, String descricao) {
        return this.create(contaId, tipo, descricao, Collections.emptySet());
    }

    public Movimentacao create(Long contaId, TipoMovimentacao tipo, String descricao, Set<Categoria> categorias) {
        final Conta conta = contaService.find(contaId);
        final Movimentacao creation = new Movimentacao(conta, tipo, descricao);

        if (!CollectionUtils.isEmpty(categorias)) {
            final Collection<Categoria> list = categoriaRepository.inserirTodos(categorias);
            creation.adicionarCategoria(list);
        }

        return repository.save(creation);
    }

    public List<Movimentacao> all() {
        return repository.findAll();
    }

    public Movimentacao find(Long id) {
        return repository.findBy(id);
    }

}


