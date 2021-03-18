package com.treinamento.persistence.funcionario.repository;

import com.treinamento.persistence.funcionario.domain.Cpf;
import com.treinamento.persistence.funcionario.domain.Funcionario;
import com.treinamento.persistence.funcionario.domain.FuncionarioSituacaoValor;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Set;

public final class FuncionarioSpecification {
    private FuncionarioSpecification() {}

    private static final String CONTRATACAO = "dataContratacao";
    private static final String SITUACAO = "situacao";
    private static final String VALOR = "valor";

    public static Specification<Funcionario> where() {
        return (root, query, builder) -> null;
    }

    public static Specification<Funcionario> nome(String nome) {
        return (root, query, builder) -> builder.like(root.get("nome"), "%" + nome + "%");
    }

    public static Specification<Funcionario> cpf(Cpf cpf) {
        return (root, query, builder) -> builder.equal(root.get("cpf"), cpf);
    }

    public static Specification<Funcionario> salario(Double salario) {
        return (root, query, builder) -> builder.greaterThan(root.get("salario"), salario);
    }

    public static Specification<Funcionario> dataContratacao(LocalDate data) {
        return (root, query, builder) -> builder.greaterThan(root.get(CONTRATACAO), data);
    }

    public static Specification<Funcionario> dataContratacaoEntre(LocalDate inicio, LocalDate fim) {
        return (root, query, builder) -> builder.between(root.get(CONTRATACAO), inicio, fim);
    }

    public static Specification<Funcionario> situacao(FuncionarioSituacaoValor situacao) {
        return (root, query, builder) -> builder.greaterThan(root.get(SITUACAO).get(VALOR), situacao);
    }

    public static Specification<Funcionario> situacaoCom(Set<FuncionarioSituacaoValor> situacoes) {
        return (root, query, builder) -> builder.in(root.get(SITUACAO).get(VALOR)).value(situacoes);
    }

    public static Specification<Funcionario> excetoAtivo() {
        return (root, query, builder) -> builder.notEqual(root.get(SITUACAO).get(VALOR), FuncionarioSituacaoValor.ATIVO);
    }
}
