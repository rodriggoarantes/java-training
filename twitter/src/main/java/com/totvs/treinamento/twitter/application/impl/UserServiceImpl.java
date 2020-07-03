package com.totvs.treinamento.twitter.application.impl;

import com.totvs.treinamento.twitter.application.UserService;
import com.totvs.treinamento.twitter.domain.user.User;
import com.totvs.treinamento.twitter.domain.user.UserRepository;
import com.totvs.treinamento.twitter.infra.Logger;
import com.totvs.treinamento.twitter.infra.exception.BusinessException;
import com.totvs.treinamento.twitter.infra.exception.NotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class UserServiceImpl implements Logger, UserService {

    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User find(@NonNull Long id) {
        log("find: " + id);
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("User não encontrada com ID %s", id)));
    }

    @Override
    public User insert(@NonNull User param) {
        log("obter: " + param);
        validate(param);

        Optional.ofNullable(repository.findByLogin(param.getLogin()))
            .ifPresent((user) -> {
                throw new BusinessException("FOUND", "Usuario já existente", HttpStatus.BAD_REQUEST.value());
            });

        return repository.save(param);
    }

    @Override
    public Iterable<User> list() {
        return repository.findAll();
    }

    @Override
    public User findByLogin(@NonNull String login) {
        return repository.findByLogin(login);
    }

    private static void validate(User obj) {
        Validate.notNull(obj, "Dados do usuario não informado");
        Validate.isTrue(StringUtils.isNotBlank(obj.getName()), "Nome do usuario não informado");
    }
}
