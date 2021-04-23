package com.treinamento.transaction.executor;

import com.treinamento.infra.Logger;
import com.treinamento.infra.TransactionDb;
import com.treinamento.infra.exception.BusinessException;
import com.treinamento.transaction.user.application.UserService;
import com.treinamento.transaction.user.domain.User;
import com.treinamento.transaction.work.Work;
import com.treinamento.transaction.work.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ExecutorService implements Logger {

    private final WorkService workService;
    private final UserService userService;
    private final TransactionDb sessaoIsolada;

    @Autowired
    public ExecutorService(WorkService workService, UserService userService) {
        this.workService = workService;
        this.userService = userService;
        this.sessaoIsolada = new TransactionDb();
    }

    public boolean executeWithNew() {
        final List<Work> list = Arrays.asList(new Work("S034"), new Work("S032"));
        AtomicBoolean existed = new AtomicBoolean(false);

        sessaoIsolada.execute(() -> {
            list.forEach(workService::insert);
            existed.set(workService.exist("S034") && workService.exist("S032"));
        });

        if (existed.get()) {
            existed.set(workService.exist("S034") && workService.exist("S032"));
        }

        return existed.get();
    }

    public boolean executeWithError() {
        final List<Work> list = Arrays.asList(new Work("S034"), new Work("S032"), new Work("EEE"));
        AtomicBoolean existed = new AtomicBoolean(false);
        AtomicBoolean existedError = new AtomicBoolean(false);

        sessaoIsolada.execute(() -> {
            try {
                list.forEach(workService::insertWithError);
            } catch (Exception e) {
                this.log("erro no executeRoolback planejado");
            }

            existed.set(workService.exist("S034") && workService.exist("S032"));
            existedError.set(workService.exist("EEE"));
        });

        if (existed.get()) {
            // não há roolback, a sessaoIsolada nao possui roolback especificado
            existed.set(workService.exist("S034") && workService.exist("S032") && !workService.exist("EEE"));
        }

        return existed.get();
    }

    public boolean executeWithRoolback() {
        final List<Work> list = Arrays.asList(new Work("S034"), new Work("S032"), new Work("EEE"));
        AtomicBoolean existed = new AtomicBoolean(false);
        AtomicBoolean existedError = new AtomicBoolean(false);

        sessaoIsolada.executeRoolback(() -> {
            try {
                list.forEach(workService::insertWithError);
            } catch (Exception e) {
                this.log("erro no executeRoolback");
            }

            existed.set(workService.exist("S034") && workService.exist("S032"));
            existedError.set(workService.exist("EEE"));
        });

        sessaoIsolada.execute(() -> {
            // nenhum registro foi salvo devido houve um roolback ao ter uma falha no runWithError
            existed.set(!workService.exist("S034") && !workService.exist("S032") && !workService.exist("EEE"));
        });

        return existed.get();
    }

    public boolean executeWithRoolbackMethod() {
        final List<Work> list = Arrays.asList(new Work("S034"), new Work("S032"), new Work("EEE"));

        final AtomicBoolean existed = new AtomicBoolean(false);
        try {
            this.workService.runWithErrorList(list);
        } catch (Exception e) {
            this.log("erro no executeRoolback - realizando roolback");
        }

        // nenhum registro foi salvo devido houve um roolback ao ter uma falha no runWithError
        final boolean s1Exist = workService.exist("S034");
        final boolean s2Exist = workService.exist("S032");
        final boolean eeeExist = workService.exist("EEE");

        log(String.format("S034 %s : S032 %s : EEE %s", s1Exist, s2Exist, eeeExist));
        existed.set(!s1Exist && !s2Exist && !eeeExist);

        return existed.get();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void executeWithTransactionOfMix(boolean throwError) throws BusinessException {
        try {
            userService.insert(new User("1", "1"));
            userService.insertTransactional(new User("2", "2"));
            userService.insert(new User("3", "3"));
            workService.insertTransactional(new Work("S111"));
            workService.insertWithError(new Work("S222"));
            workService.insertWithError(new Work("EEE"));
            workService.insert(new Work("S333"));
        } catch (Exception e) {
            final AtomicInteger countUser = new AtomicInteger(0);
            final AtomicInteger countWork = new AtomicInteger(0);
            userService.list().forEach(u -> countUser.incrementAndGet());
            workService.list().forEach(u -> countWork.incrementAndGet());

            this.log("------ realizando consulta antes do roolback ------");
            this.log(String.format("users: %s", countUser.get()));
            this.log(String.format("works: %s", countWork.get()));
            this.log("------ realizando consulta antes do roolback ------");

            this.log("# executeWithTransactionOfTransaction - realizando roolback");
            if (throwError) {
                throw new BusinessException("roolback", e);
            }
        }
    }

}
