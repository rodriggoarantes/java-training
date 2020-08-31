package com.treinamento.persistence.movimentacao.infra;

import com.treinamento.persistence.movimentacao.domain.Categoria;
import com.treinamento.persistence.movimentacao.domain.CategoriaRepository;
import com.treinamento.persistence.movimentacao.domain.Movimentacao;
import com.treinamento.persistence.movimentacao.domain.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Repository
public class CategoriaRepositoryImpl implements CategoriaRepository {

    private final CategoriaCrudRepository crudRepository;

    @Autowired
    public CategoriaRepositoryImpl(CategoriaCrudRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public List<Categoria> listar() {
        final List<Categoria> list = new ArrayList<>();
        crudRepository.findAll().forEach(list::add);
        return list;
    }

    @Override
    public Categoria findById(Long id) {
        return crudRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nenhuma registro encontrado"));
    }

    @Override
    public Categoria inserir(Categoria entity) {
        return crudRepository.save(entity);
    }

    @Override
    public List<Categoria> inserirTodos(Collection<Categoria> collection) {
        final List<Categoria> list = new ArrayList<>();
        crudRepository.saveAll(list).forEach(list::add);
        return list;
    }
}
