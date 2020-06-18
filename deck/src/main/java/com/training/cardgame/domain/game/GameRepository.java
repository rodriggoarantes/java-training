package com.training.cardgame.domain.game;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GameRepository extends CrudRepository<Game, Long> {
    @Query("select * from game where USER = :user")
    public List<Game> listarPorUsuario(Long user);
}
