package com.training.agendalive.application.impl;


import com.training.agendalive.application.LiveService;
import com.training.agendalive.domain.live.Live;
import com.training.agendalive.domain.video.Video;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

@SpringBootTest
class LiveServiceImplTest {

    @Autowired
    private LiveService service;

    public static final Live teste = new Live();

    @BeforeAll
    static void setup() {
        teste.setNome("teste");
        teste.setData(LocalDateTime.now());
        teste.setVideo(new Video());
        teste.getVideo().setTitulo(teste.getNome());
        teste.getVideo().setCanal("@teste");
        teste.getVideo().setLink("http://localhost");
    }

    @Test
    public void test_inserir_sucesso() {
        final Live inserted = service.inserir(teste);

        assertThat(inserted, notNullValue());
        assertThat(inserted.getId(), notNullValue());
        assertThat(inserted.getId().compareTo(0L), equalTo(1));
    }

}