package com.totvs.treinamento.twitter.application.impl;

import com.totvs.treinamento.twitter.application.UserService;
import com.totvs.treinamento.twitter.domain.user.User;
import com.totvs.treinamento.twitter.domain.user.UserRepository;
import com.totvs.treinamento.twitter.infra.exception.BusinessException;
import org.junit.jupiter.api.Assertions;
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
    private UserService service;

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

}