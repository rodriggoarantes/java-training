package com.training.cardgame.application;

import com.training.cardgame.domain.game.Game;
import com.training.cardgame.domain.player.Player;
import org.springframework.lang.NonNull;

import java.util.List;

public interface GameService {
    Game obter(@NonNull final Long id);
    Game inserir(@NonNull final Game game);
    Game alterar(@NonNull final Game game);
    void deletar(@NonNull final Long id);
    Iterable<Game> listar();
}
