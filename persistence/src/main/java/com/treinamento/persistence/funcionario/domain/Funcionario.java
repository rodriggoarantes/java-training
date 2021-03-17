package com.treinamento.persistence.funcionario.domain;

import com.treinamento.framework.domain.AbstractEntity;
import com.treinamento.persistence.unidade.domain.Unidade;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.lang.NonNull;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.requireNonNull;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "funcionario")
public class Funcionario extends AbstractEntity<FuncionarioId> {

    private final String nome;

    @Embedded
    private final Cpf cpf;

    private final LocalDate dataContratacao;

    private Double salario;

    @ManyToOne(targetEntity= Cargo.class, fetch = FetchType.LAZY)
    @JoinColumn(name="cargoId")
    private Cargo cargo;

    @Fetch(FetchMode.SELECT)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "funcionarios_unidades", joinColumns = {
            @JoinColumn(name = "funcionarioId") },
            inverseJoinColumns = { @JoinColumn(name = "unidadeId") })
    private final Set<Unidade> unidades;

    @CreationTimestamp
    @EqualsAndHashCode.Exclude
    private LocalDateTime created;

    @Builder
    private Funcionario(FuncionarioId id,
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
        this.unidades = new HashSet<>();
    }

    public void adicionarUnidade(@NonNull final Unidade unidade) {
        requireNonNull(unidades).add(requireNonNull(unidade));
    }
}
