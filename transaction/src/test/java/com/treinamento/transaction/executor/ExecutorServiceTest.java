package com.treinamento.transaction.executor;

import com.treinamento.transaction.user.domain.UserRepository;
import com.treinamento.transaction.work.WorkRepository;
import io.vavr.control.Try;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExecutorServiceTest {

    @Autowired
    private ExecutorService executor;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WorkRepository workRepository;

    @BeforeEach
    void before() {
        workRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void executeWithNew() {
        boolean executed = executor.executeWithNew();
        assertTrue(executed);
    }

    @Test
    void executeWithError() {
        boolean executed = executor.executeWithError();
        assertTrue(executed);
    }

    /**
     * O controle de transaçao com a TransactionDb NAO funciona!!!
     */
    @Test
    void executeWithRoolback_semFuncionarCorretamente() {
        boolean executed = executor.executeWithRoolback();
        assertFalse(executed); // falso devido não funciona se nao for pelo proxy do Spring
    }

    /**
     * O controle de transaçao com a TransactionDb Funcionando
     */
    @Test
    void executeWithRoolback() {
        boolean executed = executor.executeWithRoolbackMethod();
        assertTrue(executed);
    }

    @Test
    void executeWithTransactionRoot() {
        Try.run(() -> executor.executeWithTransactionOfMix(true));

        final AtomicInteger countUser = new AtomicInteger(0);
        final AtomicInteger countWork = new AtomicInteger(0);
        userRepository.findAll().forEach(u -> countUser.incrementAndGet());
        workRepository.findAll().forEach(u -> countWork.incrementAndGet());
        assertEquals(0, countUser.get());
        assertEquals(0, countWork.get());
    }

    @Test
    void executeWithTransactionRoot_dontThrow_dontRollBack() {
        executor.executeWithTransactionOfMix(false);

        final AtomicInteger countUser = new AtomicInteger(0);
        final AtomicInteger countWork = new AtomicInteger(0);
        userRepository.findAll().forEach(u -> countUser.incrementAndGet());
        workRepository.findAll().forEach(u -> countWork.incrementAndGet());
        assertEquals(0, countUser.get());
        assertEquals(0, countWork.get());
    }


}