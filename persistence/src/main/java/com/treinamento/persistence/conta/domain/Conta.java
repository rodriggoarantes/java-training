package com.treinamento.persistence.conta.domain;

import com.treinamento.framework.domain.AbstractEntity;
import com.treinamento.framework.exception.BusinessException;
import com.treinamento.persistence.movimentacao.domain.Categoria;
import com.treinamento.persistence.movimentacao.domain.Movimentacao;
import com.treinamento.persistence.movimentacao.domain.MovimentacaoId;
import com.treinamento.persistence.movimentacao.domain.TipoMovimentacao;
import com.treinamento.persistence.titular.domain.TitularId;
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

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "conta")
public class Conta extends AbstractEntity<ContaId> {

    private Integer agencia;
    private Integer numero;
    @Embedded
    private TitularId titular;
    private ContaStatus status;
    private BigDecimal saldo;

    @OneToMany(mappedBy = "conta", cascade = {CascadeType.PERSIST, CascadeType.ALL}, fetch = FetchType.EAGER, targetEntity = Movimentacao.class)
    private List<Movimentacao> movimentacoes;

    @Version
    private Long version;
    @CreationTimestamp
    private LocalDateTime created;

    @Builder
    private Conta(@NonNull ContaId id,
                 @NonNull Integer agencia,
                 @NonNull Integer numero,
                 @NonNull TitularId titular,
                 @NonNull BigDecimal saldo) {
        this.id = id;
        this.agencia = agencia;
        this.numero = numero;
        this.titular = titular;
        this.saldo = saldo;
        this.status = ContaStatus.ANALISE;
    }

    public void alterarStatus(ContaStatus newStatus) {
        if (this.status != newStatus
                && (ContaStatus.CANCELADA.equals(this.status) || ContaStatus.ANALISE.equals(newStatus))) {
            throw new BusinessException(
                    String.format("Não é possível alterar o status da conta de %s para %s", this.status, newStatus));
        }
        this.status = newStatus;
    }

    public void creditar(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) < 1) {
            throw new BusinessException("Valor para credito deve ser maior que zero");
        }
        this.saldo = this.saldo.add(valor);
    }

    public void debitar(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) < 1) {
            throw new BusinessException("Valor para debito deve ser maior que zero");
        }
        this.saldo = this.saldo.subtract(valor);
    }

    public void adicionarMovimentacao(@NonNull String descricao,
                                      @NonNull TipoMovimentacao tipo) {
        this.adicionarMovimentacao(descricao, tipo, Collections.emptySet());
    }

    public void adicionarMovimentacao(@NonNull String descricao,
                                      @NonNull TipoMovimentacao tipo,
                                      @NonNull Set<Categoria> categorias) {
        final var movimentacao = Movimentacao.builder()
                                             .id(MovimentacaoId.generate())
                                             .conta(this)
                                             .descricao(descricao)
                                             .tipo(tipo)
                                             .build();
        movimentacao.adicionarCategoria(categorias);

        if (CollectionUtils.isEmpty(this.movimentacoes))
            this.movimentacoes = new ArrayList<>(1);

        this.movimentacoes.add(movimentacao);
    }
}
