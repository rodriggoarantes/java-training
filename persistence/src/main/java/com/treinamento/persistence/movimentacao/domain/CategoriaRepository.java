package com.treinamento.persistence.movimentacao.domain;

import java.util.Collection;
import java.util.List;

public interface CategoriaRepository {
    List<Categoria> listar();
    Categoria findById(Long id);
    Categoria inserir(Categoria categoria);
    List<Categoria> inserirTodos(Collection<Categoria> collection);
}
