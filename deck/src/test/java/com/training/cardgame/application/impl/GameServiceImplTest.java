package com.training.cardgame.application.impl;


import com.training.cardgame.application.GameService;
import com.training.cardgame.domain.game.Game;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
class GameServiceImplTest {

    @Autowired
    private GameService service;

    public static final Game teste = new Game();

    @BeforeAll
    static void setup() {
        teste.setNome("teste");
        teste.setData(LocalDateTime.now());
        teste.setSaved(LocalDateTime.now());
    }

    @Test
    public void test_inserir_sucesso() {
        final Game inserted = service.inserir(teste);

        assertThat(inserted, notNullValue());
        assertThat(inserted.getId(), notNullValue());
        assertThat(inserted.getId(), equalTo(teste.getId()));
        assertThat(inserted.getData(), notNullValue());
    }

}