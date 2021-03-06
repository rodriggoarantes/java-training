package com.training.cardgame.domain.player;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
public class PlayerRepositoryTest {

    @Autowired
    private PlayerRepository repository;

    public static final Player teste = new Player();

    @BeforeAll
    static void setup() {
        teste.setNome("Player TESTE");
        teste.setLogin("login");
    }

    @Test
    public void test_save_novo_sucesso() {
        final Player inserted = new Player();
        inserted.setNome(teste.getNome());
        inserted.setLogin(teste.getLogin());
        repository.save(inserted);

        assertThat(inserted, notNullValue());
        assertThat(inserted.getId(), notNullValue());
        assertThat(inserted.getId().compareTo(0L), equalTo(1));
        assertThat(inserted.getNome(), equalTo(teste.getNome()));
        assertThat(inserted.getLogin(), equalTo(teste.getLogin()));
    }
}
