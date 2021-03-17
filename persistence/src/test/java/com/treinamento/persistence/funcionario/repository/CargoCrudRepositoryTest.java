package com.treinamento.persistence.funcionario.repository;

import com.treinamento.persistence.config.RepositoryConfigIT;
import com.treinamento.persistence.funcionario.domain.Cargo;
import com.treinamento.persistence.funcionario.domain.CargoId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Repositorio de cadastro de Cargos")
class CargoCrudRepositoryTest extends RepositoryConfigIT {

    @Autowired
    private CargoCrudRepository repo;

    @Test
    @DisplayName("Contar numero totais de cargos")
    void contar() {
        repo.save(Cargo.builder().id(CargoId.generate()).descricao("Gerente").build());
        repo.save(Cargo.builder().id(CargoId.generate()).descricao("Assistente").build());
        repo.save(Cargo.builder().id(CargoId.generate()).descricao("Tecnico").build());

        final var quantidade = repo.count();
        assertEquals(3, quantidade);
    }

    @Test
    @DisplayName("Salvando um cargo no banco de dados")
    void salvar() {
        final var cargo = Cargo.builder()
                               .id(CargoId.generate())
                               .descricao("Gerente")
                               .build();

        final var saved = repo.save(cargo);
        assertNotNull(saved);
        assertEquals(cargo.getId(), saved.getId());
        assertEquals(cargo.getDescricao(), saved.getDescricao());
    }

    @Test
    @DisplayName("Listar vários cargos")
    void listar() {
        repo.save(Cargo.builder().id(CargoId.generate()).descricao("Gerente").build());
        repo.save(Cargo.builder().id(CargoId.generate()).descricao("Assistente").build());
        repo.save(Cargo.builder().id(CargoId.generate()).descricao("Tecnico").build());

        final var cargos = new ArrayList<>();
        repo.findAll().forEach(cargos::add);

        assertEquals(3, cargos.size());
    }

    @Test
    @DisplayName("Obter um cargo especifico")
    void obter() {

        final var cargoId = CargoId.generate();
        repo.save(Cargo.builder().id(cargoId).descricao("Gerente").build());
        repo.save(Cargo.builder().id(CargoId.generate()).descricao("Assistente").build());

        final var find = repo.findById(cargoId);
        assertTrue(find.isPresent());
        assertEquals(cargoId, find.get().getId());
        assertEquals("Gerente", find.get().getDescricao());
    }

    @Test
    @DisplayName("Atualizar um cargo especifico")
    void atualizar() {

        // given
        final var cargoId = CargoId.generate();
        repo.save(Cargo.builder().id(cargoId).descricao("Gerente").build());
        final var contagemAnterior = repo.count();

        // when
        final var atualizado = repo.save(Cargo.builder().id(cargoId).descricao("Desenvolvedor").build());
        final var contagemPosterior = repo.count();

        // then
        assertNotNull(atualizado);
        assertEquals(contagemAnterior, contagemPosterior);
        assertEquals(cargoId, atualizado.getId());
        assertEquals("Desenvolvedor", atualizado.getDescricao());
    }

    @Test
    @DisplayName("Remover um cargo especifico")
    void remover() {

        final var cargoId = CargoId.generate();
        repo.save(Cargo.builder().id(cargoId).descricao("Deletar").build());
        final var contagemAnterior = repo.count();

        // when
        repo.deleteById(cargoId);
        final var contagemPosterior = repo.count();

        // then
        final var naoExiste = repo.findById(cargoId);
        assertFalse(naoExiste.isPresent());
        assertEquals(1, contagemAnterior);
        assertEquals(0, contagemPosterior);
    }

}