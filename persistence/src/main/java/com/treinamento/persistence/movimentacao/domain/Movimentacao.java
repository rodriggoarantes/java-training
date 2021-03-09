package com.treinamento.persistence.movimentacao.domain;

import com.treinamento.persistence.conta.domain.Conta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
@EqualsAndHashCode
@Entity
public class Movimentacao {

    @Id
    @GeneratedValue
    private Long id;
    private String descricao;
    @Enumerated(EnumType.STRING)
    private TipoMovimentacao tipo;

    @ManyToOne
    private Conta conta;

    @ManyToMany
    private final Set<Categoria> categorias = new HashSet<>();

    @CreationTimestamp
    private LocalDateTime data;
    @Version
    private Long version;

    public Movimentacao(Conta conta, TipoMovimentacao tipo, String descricao) {
        this.conta = conta;
        this.tipo = tipo;
        this.descricao = descricao;
    }

    public void adicionarCategoria(Categoria categoria) {
        this.categorias.add(categoria);
    }

    public void adicionarCategoria(Collection<Categoria> categorias) {
        if (!CollectionUtils.isEmpty(categorias)) {
            this.categorias.addAll(categorias);
        }
    }
}
