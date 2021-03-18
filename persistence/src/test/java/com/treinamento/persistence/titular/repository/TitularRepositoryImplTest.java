package com.treinamento.persistence.titular.repository;

import com.treinamento.persistence.config.RepositoryConfigIT;
import com.treinamento.persistence.titular.domain.Titular;
import com.treinamento.persistence.titular.domain.TitularId;
import com.treinamento.persistence.titular.domain.TitularRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Repositorio da entidade Titular - Teste Integração")
class TitularRepositoryImplTest extends RepositoryConfigIT {

    @Autowired
    private TitularRepository repository;

    @Test
    void findAll() {
        repository.save(Titular.builder().id(TitularId.generate()).nome(TitularId.generate().toString()).build());
        repository.save(Titular.builder().id(TitularId.generate()).nome(TitularId.generate().toString()).build());
        repository.save(Titular.builder().id(TitularId.generate()).nome(TitularId.generate().toString()).build());

        final List<Titular> all = repository.findAll();
        assertNotNull(all);
        assertEquals(3, all.size());
    }

    @Test
    void save() {
        final Titular titular = Titular.builder()
                                       .id(TitularId.generate())
                                       .nome("NomeTesteTitular")
                                       .build();
        final Titular saved = repository.save(titular);

        assertNotNull(saved);
        assertEquals(titular.getId(),  saved.getId());
        assertEquals("NomeTesteTitular",  saved.getNome());
    }

    @Test
    void findBy() {
        repository.save(Titular.builder().id(TitularId.generate()).nome(TitularId.generate().toString()).build());
        final var id = repository.save(Titular.builder().id(TitularId.generate()).nome("AssertNome").build()).getId();

        final var titularEncontrado = repository.findBy(id);
        assertNotNull(titularEncontrado);
        assertEquals(id, titularEncontrado.getId());
        assertEquals("AssertNome",  titularEncontrado.getNome());
    }
}