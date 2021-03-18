package com.treinamento.persistence.funcionario.domain;

import com.treinamento.framework.util.DateUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor(staticName = "of", access = AccessLevel.PRIVATE)
public class FuncionarioSituacao {
    private final FuncionarioSituacaoValor valor;
    private final ZonedDateTime quando;


    public static FuncionarioSituacao ofAtivo() {
        return FuncionarioSituacao.of(FuncionarioSituacaoValor.ATIVO, DateUtils.getNow());
    }

    public static FuncionarioSituacao ofInativo() {
        return FuncionarioSituacao.of(FuncionarioSituacaoValor.INATIVO, DateUtils.getNow());
    }
}
