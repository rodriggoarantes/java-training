package com.treinamento.transaction.application.impl;

import com.treinamento.infra.TransactionDb;
import com.treinamento.transaction.user.application.UserServiceImpl;
import com.treinamento.transaction.user.domain.User;
import com.treinamento.transaction.user.domain.UserRepository;
import com.treinamento.infra.exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserServiceImpl service;

    @Test
    public void insert_ok() {
        final User u = new User();
        u.setLogin("ok");
        u.setName("ok");
        service.insert(u);

        final Optional<User> saved = repository.findById(u.getId());

        assertThat(saved.isPresent(), equalTo(true));
        assertThat(saved.get(), notNullValue());
        assertThat(saved.get().getId(), notNullValue());
        assertThat(saved.get().getId(), equalTo(u.getId()));
    }

    @Test
    public void insert_existentLogin_error() {
        User u = new User();
        u.setLogin("teste");
        u.setName("tester");
        repository.save(u);

        assertThatThrownBy(() -> service.insert(u))
                .isInstanceOf(BusinessException.class)
                    .hasMessageContaining("Usuario já existente");
    }

    @Test
    public void transaction_default() {
        final User u = new User("teste", "teste");
        service.insertTransactional(u);

        final Optional<User> saved = repository.findById(u.getId());
        assertThat(saved.isPresent(), equalTo(true));
    }

    @Test
    public void transaction_isolado() {
        User u = new User();
        u.setLogin("testea");
        u.setName("tester");
        repository.save(u);

        TransactionDb requirednew = new TransactionDb();
        final User u1 = new User("testeb", "testeb");

        requirednew.execute(() -> {
            service.insert(u1);

            final Optional<User> saved = repository.findById(u.getId());
            assertThat(saved.isPresent(), equalTo(true));

            final Optional<User> saved1 = repository.findById(u1.getId());
            assertThat(saved1.isPresent(), equalTo(true));
        });
    }

    @Test
    public void transaction_isolado_isolado() {
        User a = new User("a", "a");
        repository.save(a);

        TransactionDb requirednew = new TransactionDb();
        final User b = new User("b", "b");

        requirednew.execute(() -> {
            service.insertTransactional(b);

            final Optional<User> saved = repository.findById(a.getId());
            assertThat(saved.isPresent(), equalTo(true));

            final Optional<User> saved1 = repository.findById(b.getId());
            assertThat(saved1.isPresent(), equalTo(true));

            final User c = new User("c", "c");

            requirednew.execute(() -> {
                service.insert(c);
            });
        });
    }
}