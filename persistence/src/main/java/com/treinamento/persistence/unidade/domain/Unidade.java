package com.treinamento.persistence.unidade.domain;


import com.treinamento.framework.domain.AbstractEntity;
import com.treinamento.persistence.funcionario.domain.Funcionario;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.StringUtils;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

import static java.util.Objects.requireNonNull;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "unidade")
public class Unidade extends AbstractEntity<UnidadeId> {

    private String descricao;

    @Embedded
    private Endereco endereco;

    @ManyToMany(mappedBy = "unidades", fetch = FetchType.EAGER)
    private List<Funcionario> funcionarios;

    @Builder
    private Unidade(UnidadeId id, String descricao, Endereco endereco) {
        if (!StringUtils.hasText(descricao)) throw new IllegalArgumentException();
        this.id = requireNonNull(id);
        this.descricao = requireNonNull(descricao);
        this.endereco = requireNonNull(endereco);
    }
}
