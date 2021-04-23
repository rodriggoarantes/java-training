package com.treinamento.infra;

import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class TransactionDb implements Logger {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void execute(@NonNull final Procedure procedure) {
        log("execute");
        procedure.execute();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void executeRoolback(@NonNull final Procedure procedure) {
        log("executeRoolback");
        procedure.execute();
    }

    public interface Procedure {
        void execute();
    }
}


