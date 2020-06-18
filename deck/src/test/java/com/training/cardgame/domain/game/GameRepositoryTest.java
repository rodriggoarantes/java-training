package com.training.cardgame.domain.game;

import com.training.cardgame.application.GameService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
class GameRepositoryTest {

    @Autowired
    private GameRepository repository;

    public static final Game teste = new Game();

    @BeforeAll
    static void setup() {
        teste.setNome("teste");
        teste.setData(LocalDateTime.now());
        teste.setSaved(LocalDateTime.now());
    }

    @Test
    public void test_save_novo_sucesso() {
        final Game inserted = repository.save(teste);

        assertThat(inserted, notNullValue());
        assertThat(inserted.getId(), notNullValue());
        assertThat(inserted.getId(), equalTo(teste.getId()));
        assertThat(inserted.getData(), notNullValue());
    }

    // inserir_sucesso
}
