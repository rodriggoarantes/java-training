package com.treinamento.persistence.movimentacao.domain;

import com.treinamento.persistence.conta.domain.Conta;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@NoArgsConstructor
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
    @CreationTimestamp
    private LocalDateTime data;

    @ManyToOne
    private Conta conta;

    public Movimentacao(Conta conta, String descricao, TipoMovimentacao tipo) {
        this.conta = conta;
        this.descricao = descricao;
        this.tipo = tipo;
    }
}
