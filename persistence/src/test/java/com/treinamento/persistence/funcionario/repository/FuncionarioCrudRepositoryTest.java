package com.treinamento.persistence.funcionario.repository;

import com.treinamento.persistence.config.RepositoryConfigIT;
import com.treinamento.persistence.funcionario.domain.Cargo;
import com.treinamento.persistence.funcionario.domain.CargoId;
import com.treinamento.persistence.funcionario.domain.FuncionarioId;
import com.treinamento.persistence.unidade.domain.Endereco;
import com.treinamento.persistence.unidade.domain.Unidade;
import com.treinamento.persistence.unidade.domain.UnidadeId;
import com.treinamento.persistence.unidade.repository.UnidadeCrudRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;

import static com.treinamento.persistence.funcionario.FuncionarioTestFactory.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Teste de cadastro de Funcionarios")
class FuncionarioCrudRepositoryTest extends RepositoryConfigIT {

    @Autowired
    private CargoCrudRepository cargoCrudRepository;

    @Autowired
    private UnidadeCrudRepository unidadeCrudRepository;

    @Autowired
    private FuncionarioCrudRepository repository;

    private Cargo cargoPadrao = Cargo.builder().id(CargoId.generate()).descricao("PADRAO").build();
    private Unidade unidadePadrao = Unidade.builder().id(UnidadeId.generate()).descricao("A").endereco(Endereco.of("rua", "cidade", "estado")).build();

    @BeforeEach
    void setup() {
        cargoPadrao = cargoCrudRepository.save(cargoPadrao);
        unidadePadrao = unidadeCrudRepository.save(unidadePadrao);
    }

    @Test
    @DisplayName("Validaçao de contadores")
    void count() {
        assertEquals(1, cargoCrudRepository.count());
        assertEquals(1, unidadeCrudRepository.count());
        assertEquals(0, repository.count());
    }

    @Test
    @DisplayName("Salvar um funcionario")
    void salvar() {
        final var funcionario = umFuncionarioBuilder().cargo(cargoPadrao).build();

        final var salvo = repository.save(funcionario);

        assertEqualsBase(funcionario, salvo);
        assertEquals(1, repository.count());
    }

    @Test
    @DisplayName("Salvar um funcionario com unidade")
    void salvarComUnidade() {
        final var funcionario = umFuncionarioBuilder().cargo(cargoPadrao).build();
        funcionario.adicionarUnidade(unidadePadrao);

        repository.save(funcionario);

        final var salvo = repository.findById(id).orElse(null);

        assertEquals(1, repository.count());
        assertNotNull(salvo);
        assertEqualsBase(funcionario, salvo);
        assertNotNull(salvo.getUnidades());
        assertEquals(1, salvo.getUnidades().size());
        assertTrue(salvo.getUnidades().contains(unidadePadrao));
    }

    @Test
    @DisplayName("Encontrar um funcionario pelo id")
    void findById() {
        final var funcionario = umFuncionarioBuilder().cargo(cargoPadrao).build();
        repository.save(funcionario);

        final var encontrado = repository.findById(id);

        assertTrue(encontrado.isPresent());
        assertEquals(1, repository.count());
        assertEqualsBase(funcionario, encontrado.get());
    }

    @Test
    @DisplayName("Encontrar varios funcionarios")
    void findAll() {
        repository.save(umFuncionarioBuilder().cargo(cargoPadrao).build());
        repository.save(umFuncionarioBuilder().cargo(cargoPadrao).id(FuncionarioId.generate()).build());
        repository.save(umFuncionarioBuilder().cargo(cargoPadrao).id(FuncionarioId.generate()).build());

        final var lista = new ArrayList<>();
        repository.findAll().forEach(lista::add);

        assertFalse(CollectionUtils.isEmpty(lista));
        assertEquals(3, lista.size());
        assertEquals(3, repository.count());
    }

}