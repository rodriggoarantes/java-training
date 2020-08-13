package com.treinamento.transaction.user.application;

import com.treinamento.infra.Logger;
import com.treinamento.infra.exception.BusinessException;
import com.treinamento.infra.exception.NotFoundException;
import com.treinamento.transaction.user.domain.User;
import com.treinamento.transaction.user.domain.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements Logger, UserService {

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
        log("inserir: " + param);
        validate(param);

        if (repository.existsByLogin(param.getLogin()))
            throw new BusinessException("Usuario já existente com o login informado");

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
        Validate.isTrue(StringUtils.isNotBlank(obj.getLogin()), "Login do usuario não informado");
    }

    @Override
    @Transactional
    public User insertTransactional(@NonNull User param) {
        log("insertTransactional: " + param);
        return repository.save(param);
    }
}
