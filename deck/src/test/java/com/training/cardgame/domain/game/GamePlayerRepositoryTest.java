package com.training.cardgame.domain.game;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
class GamePlayerRepositoryTest {

    @Autowired
    private GamePlayerRepository repository;
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Test
    public void test_adicionarJogador_sucesso() {
        repository.adicionarJogador(1L, 1L);
        final Long player = jdbcTemplate.queryForObject("SELECT PLAYER FROM GAME_PLAYERS WHERE GAME = ?",
                new Object[]{1L}, Long.class);
        assertThat(player, equalTo(1L));
    }

}