package com.training.cardgame.domain.game;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.List;

public interface GameRepository extends CrudRepository<Game, Long> {

    @Query("SELECT * FROM game WHERE nome LIKE :user")
    List<Game> pesquisarPorNome(@NonNull String name);
}
