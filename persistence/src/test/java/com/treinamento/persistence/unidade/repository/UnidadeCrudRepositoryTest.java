package com.treinamento.persistence.unidade.repository;

import com.treinamento.persistence.config.RepositoryConfigIT;
import com.treinamento.persistence.unidade.domain.Endereco;
import com.treinamento.persistence.unidade.domain.UnidadeId;
import com.treinamento.persistence.unidade.domain.Unidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UnidadeCrudRepositoryTest extends RepositoryConfigIT {

    @Autowired
    private UnidadeCrudRepository repository;

    private static final Endereco enderecoPadrao = Endereco.of("rua", "cidade", "estado");

    @Test
    @DisplayName("Contar numero totais de unidades")
    void contar() {
        repository.save(Unidade.builder().id(UnidadeId.generate()).descricao("A").endereco(enderecoPadrao).build());
        repository.save(Unidade.builder().id(UnidadeId.generate()).descricao("B").endereco(enderecoPadrao).build());
        repository.save(Unidade.builder().id(UnidadeId.generate()).descricao("C").endereco(enderecoPadrao).build());

        final var quantidade = repository.count();
        assertEquals(3, quantidade);
    }

    @Test
    @DisplayName("Salvando uma unidade")
    void salvar() {
        final var unidade = Unidade.builder()
                                   .id(UnidadeId.generate())
                                   .descricao("Unidade A")
                                   .endereco(enderecoPadrao)
                                   .build();

        final var saved = repository.save(unidade);
        assertNotNull(saved);
        assertEquals(unidade.getId(), saved.getId());
        assertEquals(unidade.getDescricao(), saved.getDescricao());
        assertEquals(enderecoPadrao, saved.getEndereco());
    }

    @Test
    @DisplayName("Listar vários unidades")
    void listar() {
        repository.save(Unidade.builder().id(UnidadeId.generate()).descricao("A").endereco(enderecoPadrao).build());
        repository.save(Unidade.builder().id(UnidadeId.generate()).descricao("B").endereco(enderecoPadrao).build());
        repository.save(Unidade.builder().id(UnidadeId.generate()).descricao("C").endereco(enderecoPadrao).build());

        final var unidades = new ArrayList<>();
        repository.findAll().forEach(unidades::add);

        assertEquals(3, unidades.size());
    }

    @Test
    @DisplayName("Obter uma unidade especifica")
    void obter() {

        final var unidadeId = UnidadeId.generate();
        repository.save(Unidade.builder().id(unidadeId).descricao("UnidadeA").endereco(enderecoPadrao).build());
        repository.save(Unidade.builder().id(UnidadeId.generate()).descricao("UnidadeB").endereco(enderecoPadrao).build());

        final var find = repository.findById(unidadeId);
        assertTrue(find.isPresent());
        assertEquals(unidadeId, find.get().getId());
        assertEquals("UnidadeA", find.get().getDescricao());
        assertEquals(enderecoPadrao, find.get().getEndereco());
    }

    @Test
    @DisplayName("Atualizar uma unidade especifica")
    void atualizar() {

        // given
        final var unidadeId = UnidadeId.generate();
        repository.save(Unidade.builder().id(unidadeId).descricao("UNIDADE").endereco(enderecoPadrao).build());
        final var contagemAnterior = repository.count();

        // when
        final var atualizado = repository.save(Unidade.builder().id(unidadeId).descricao("RASCompany").endereco(enderecoPadrao).build());
        final var contagemPosterior = repository.count();

        // then
        assertNotNull(atualizado);
        assertEquals(contagemAnterior, contagemPosterior);
        assertEquals(unidadeId, atualizado.getId());
        assertEquals("RASCompany", atualizado.getDescricao());
        assertEquals(enderecoPadrao, atualizado.getEndereco());
    }

    @Test
    @DisplayName("Remover uma unidade")
    void remover() {

        final var unidadeId = UnidadeId.generate();
        repository.save(Unidade.builder().id(unidadeId).descricao("DELETAR").endereco(enderecoPadrao).build());
        final var contagemAnterior = repository.count();

        // when
        repository.deleteById(unidadeId);
        final var contagemPosterior = repository.count();

        // then
        final var naoExiste = repository.findById(unidadeId);
        assertFalse(naoExiste.isPresent());
        assertEquals(1, contagemAnterior);
        assertEquals(0, contagemPosterior);
    }
}