package com.treinamento.persistence.conta.application;

import com.treinamento.persistence.config.ConfigIT;
import com.treinamento.persistence.conta.domain.Conta;
import com.treinamento.persistence.conta.domain.ContaStatus;
import com.treinamento.persistence.titular.domain.TitularId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ContaServiceTest extends ConfigIT {

    @Autowired
    private ContaService service;

    @Test
    void alterarStatus() {
        final Conta conta = service.create(123, 345, TitularId.generate());
        service.alterarStatus(conta.getId(), ContaStatus.ATIVO);

        final Conta alterada = service.find(conta.getId());
        assertEquals(ContaStatus.ATIVO, alterada.getStatus());
    }

    @Test
    void realizarDebito() {
        final Conta conta = service.create(123, 345, TitularId.generate());
        service.realizarDebito(conta.getId(), BigDecimal.valueOf(10.0));

        final Conta alterada = service.find(conta.getId());
        assertEquals(Double.valueOf(-10), alterada.getSaldo().doubleValue());
    }


}