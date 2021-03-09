package com.treinamento.persistence.conta.domain;

import com.treinamento.framework.exception.BusinessException;
import com.treinamento.persistence.movimentacao.domain.Movimentacao;
import com.treinamento.persistence.titular.domain.TitularId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
@Builder
@EqualsAndHashCode
@Entity
public class Conta {

    @EmbeddedId
    private ContaId id;
    private Integer agencia;
    private Integer numero;
    @Embedded
    private TitularId titular;
    private ContaStatus status;
    private BigDecimal saldo;
    @Version
    private Long version;

    @OneToMany(mappedBy = "conta", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Movimentacao> movimentacoes;

    public Conta(Integer agencia, Integer numero, TitularId titular) {
        this(ContaId.from(-1), agencia, numero, titular, ContaStatus.ANALISE, BigDecimal.ZERO, null, null);
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
}
