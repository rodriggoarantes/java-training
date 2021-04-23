package com.treinamento.persistence.titular.application;

import com.treinamento.persistence.config.ConfigIT;
import com.treinamento.persistence.titular.domain.Titular;
import com.treinamento.persistence.titular.domain.TitularId;
import com.treinamento.persistence.titular.domain.TitularRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TitularServiceTest extends ConfigIT {

    @Autowired
    private TitularRepository repository;
    @Autowired
    private TitularService service;

    private static final String nomeTitular = "Titular Conta";

    @Test
    void create() {
        final var titularId = TitularId.generate();
        final Titular titular = Titular.builder()
                                       .id(titularId)
                                       .nome(nomeTitular)
                                       .build();
        final Titular created = service.create(titular);

        assertNotNull(created);
        assertEquals(titularId,  created.getId());
        assertEquals(nomeTitular,  created.getNome());
    }

    @Test
    void find() {
        final var titularId = repository.save(Titular.builder()
                                                     .id(TitularId.generate())
                                                     .nome(nomeTitular)
                                                     .build())
                                        .getId();

        final Titular encontrado = service.find(titularId);
        assertNotNull(encontrado);
        assertNotNull(encontrado.getId());
        assertEquals(titularId, encontrado.getId());
        assertEquals(nomeTitular, encontrado.getNome());
    }
}