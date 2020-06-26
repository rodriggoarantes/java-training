package com.training.cardgame.domain.game;

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

    private static LocalDateTime dataAlteracao;

    @BeforeAll
    static void setup() {
        dataAlteracao = LocalDateTime.now();
    }

    @Test
    public void test_save_novo_sucesso() {
        final Game teste = new Game();
        teste.setNome("TESTE_INSERIR");
        teste.setData(dataAlteracao);
        teste.setSaved(dataAlteracao);

        final Game inserted = repository.save(teste);

        assertThat(inserted, notNullValue());
        assertThat(inserted.getId(), notNullValue());
        assertThat(inserted.getId(), equalTo(teste.getId()));
        assertThat(inserted.getData(), notNullValue());
        assertThat(inserted.getNome(), equalTo(teste.getNome()));
    }

    @Test
    public void test_save_alterar_sucesso() {
        final Game teste = new Game();
        teste.setNome("teste");
        teste.setData(dataAlteracao);
        teste.setSaved(dataAlteracao);

        repository.save(teste); // insere

        teste.setNome("ALTERADO");
        teste.setData(dataAlteracao.plusDays(5L));

        final Game alter = repository.save(teste); // altera

        assertThat(alter, notNullValue());
        assertThat(alter.getId(), notNullValue());
        assertThat(alter.getId(), equalTo(teste.getId()));
        assertThat(alter.getData(), notNullValue());
        assertThat(alter.getData(), equalTo(dataAlteracao.plusDays(5L)));
    }


}

