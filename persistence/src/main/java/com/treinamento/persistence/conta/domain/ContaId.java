package com.treinamento.persistence.conta.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embeddable;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Embeddable
public class ContaId implements Serializable {

    private Long contaId;

    public static ContaId from(Number id) {
        return new ContaId(id.longValue());
    }

    public ContaId next() {
        return from(contaId + 1);
    }
}


