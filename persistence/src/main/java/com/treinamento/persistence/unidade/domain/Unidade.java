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
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.util.StringUtils;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "unidade")
public class Unidade extends AbstractEntity<UnidadeId> {

    private String descricao;

    @Embedded
    private Endereco endereco;

    @ManyToMany(mappedBy = "unidades", fetch = FetchType.EAGER)
    private List<Funcionario> funcionarios;

    @CreationTimestamp
    private LocalDateTime created;

    @Builder
    private Unidade(UnidadeId id, String descricao, Endereco endereco) {
        if (!StringUtils.hasText(descricao)) throw new IllegalArgumentException();
        this.id = requireNonNull(id);
        this.descricao = requireNonNull(descricao);
        this.endereco = requireNonNull(endereco);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
