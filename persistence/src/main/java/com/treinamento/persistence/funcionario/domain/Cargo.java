package com.treinamento.persistence.funcionario.domain;

import com.treinamento.framework.domain.AbstractEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.StringUtils;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "cargo")
public class Cargo extends AbstractEntity<CargoId> {

    private String descricao;

    @OneToMany(mappedBy = "cargo")
    private List<Funcionario> funcionarios;

    @Builder
    private Cargo(CargoId id, String descricao) {
        if (!StringUtils.hasText(descricao)) {
            throw new IllegalArgumentException();
        }

        this.id = Objects.requireNonNull(id);
        this.descricao = descricao;
    }
}
