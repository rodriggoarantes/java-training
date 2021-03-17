package com.treinamento.persistence.funcionario.domain;

import com.treinamento.framework.domain.AbstractEntity;
import com.treinamento.persistence.unidade.domain.Unidade;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;

import static java.util.Objects.requireNonNull;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "funcionario")
public class Funcionario extends AbstractEntity<FuncionarioId> {

    private String nome;

    @Embedded
    private Cpf cpf;

    private Double salario;

    private LocalDate dataContratacao;

    @ManyToOne(targetEntity= Cargo.class, fetch = FetchType.LAZY)
    @JoinColumn(name="cargoId")
    private Cargo cargo;

    @Fetch(FetchMode.SELECT)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "funcionarios_unidades", joinColumns = {
            @JoinColumn(name = "funcionarioId") },
            inverseJoinColumns = { @JoinColumn(name = "unidadeId") })
    private List<Unidade> unidades;

    public Funcionario(FuncionarioId id,
                       String nome,
                       Cpf cpf,
                       Double salario,
                       LocalDate dataContratacao,
                       Cargo cargo) {
        this.id = requireNonNull(id);
        this.nome = requireNonNull(nome);
        this.cpf = requireNonNull(cpf);
        this.salario = requireNonNull(salario);
        this.dataContratacao = requireNonNull(dataContratacao);
        this.cargo = requireNonNull(cargo);
    }
}
