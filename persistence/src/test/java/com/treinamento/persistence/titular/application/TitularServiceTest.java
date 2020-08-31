package com.treinamento.persistence.titular.application;

import com.treinamento.persistence.titular.domain.Titular;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TitularServiceTest {

    @Autowired
    private TitularService service;

    @Test
    void create() {
        final Titular titular = new Titular("Teste");
        final Titular created = service.create(titular);

        assertNotNull(created);
        assertEquals(titular.getNome(), created.getNome());
    }

    @Test
    void find() {
    }
}