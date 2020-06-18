package com.training.cardgame.application;

import com.training.cardgame.domain.game.Live;
import org.springframework.lang.NonNull;

import java.util.List;

public interface LiveService {
    Live obter(@NonNull final Long id);
    Live inserir(@NonNull final Live live);
    Live alterar(@NonNull final Live live);
    void deletar(@NonNull final Long id);
    Iterable<Live> listar();
    List<Live> pesquisar(String nome);
}
