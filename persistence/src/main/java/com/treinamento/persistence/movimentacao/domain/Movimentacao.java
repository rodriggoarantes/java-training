package com.treinamento.persistence.movimentacao.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.treinamento.framework.domain.AbstractEntity;
import com.treinamento.persistence.conta.domain.Conta;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
@Entity(name = "Movimentacao")
@Table(name = "movimentacao")
public class Movimentacao extends AbstractEntity<MovimentacaoId> {

    private String descricao;

    @Enumerated(EnumType.STRING)
    private TipoMovimentacao tipo;

    private BigDecimal valor;

    @ManyToOne(targetEntity=Conta.class, fetch = FetchType.LAZY)
    @JoinColumn(name="contaId")
    @JsonIgnore
    private Conta conta;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "movimentacao_categoria",
            joinColumns = @JoinColumn(name = "movimentacaoId", referencedColumnName = "id"))
    private Set<Categoria> categorias;

    @CreationTimestamp
    private LocalDateTime created;

    @Version
    private Long version;

    @Builder
    private Movimentacao(@NonNull MovimentacaoId id,
                         @NonNull Conta conta,
                         @NonNull BigDecimal valor,
                         @NonNull TipoMovimentacao tipo,
                         @NonNull String descricao) {
        this.id = id;
        this.conta = conta;
        this.tipo = tipo;
        this.descricao = descricao;
        this.valor = valor;
    }

    public void adicionarCategoria(Categoria categoria) {
        if (CollectionUtils.isEmpty(this.categorias))
            this.categorias = new HashSet<>(0);
        this.categorias.add(categoria);
    }

    public void adicionarCategoria(Collection<Categoria> categorias) {
        if (!CollectionUtils.isEmpty(categorias)) {
            categorias.forEach(this::adicionarCategoria);
        }
    }
}
