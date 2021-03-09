package com.treinamento.persistence.movimentacao.domain;

import com.treinamento.persistence.conta.domain.Conta;
import lombok.AccessLevel;
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

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "movimentacao_categoria",
            joinColumns = {@JoinColumn(name = "movimentacao_id")},
            inverseJoinColumns = {@JoinColumn(name = "categoria_id")}
    )
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
