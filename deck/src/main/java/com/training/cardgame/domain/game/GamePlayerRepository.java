package com.training.cardgame.domain.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class GamePlayerRepository {

    @Autowired
    private NamedParameterJdbcTemplate template;

    public void adicionarJogador(Long gameId, Long playerId) {
        final String sql = " INSERT INTO game_players (game, player) VALUES (:game, :player)";

        final Map<String, Long> parametros = new HashMap<>(0);
        parametros.put("game", gameId);
        parametros.put("player", playerId);

        template.update(sql, parametros);
    }
}

