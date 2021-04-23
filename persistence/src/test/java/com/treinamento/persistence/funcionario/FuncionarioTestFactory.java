package com.treinamento.persistence.funcionario;

import com.treinamento.persistence.funcionario.domain.Cpf;
import com.treinamento.persistence.funcionario.domain.Funcionario;
import com.treinamento.persistence.funcionario.domain.FuncionarioId;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FuncionarioTestFactory {

    public static final FuncionarioId id = FuncionarioId.generate();
    public static final String nome = "Funcionario Padrao";
    public static final Cpf cpf = Cpf.from("001.002.003-04");
    public static final Double salario = 9876.54;
    public static final LocalDate contratacao = LocalDate.now();

    public static Funcionario.FuncionarioBuilder umFuncionarioBuilder() {
        return Funcionario.builder()
                          .id(id)
                          .nome(nome)
                          .cpf(cpf)
                          .salario(salario)
                          .dataContratacao(contratacao);
    }

    public static void assertEqualsBase(Funcionario a, Funcionario b) {
        assertEquals(a.getId(), b.getId());
        assertEquals(a.getNome(), b.getNome());
        assertEquals(a.getCpf(), b.getCpf());
        assertEquals(a.getCargo(), b.getCargo());
        assertEquals(a.getSalario(), b.getSalario());
        assertEquals(a.getDataContratacao(), b.getDataContratacao());
    }

}
