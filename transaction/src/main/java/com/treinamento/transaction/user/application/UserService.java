package com.treinamento.transaction.user.application;

import com.treinamento.transaction.user.domain.User;
import org.springframework.lang.NonNull;

public interface UserService {
    User find(@NonNull final Long id);
    User insert(@NonNull final User param);
    User insertTransactional(@NonNull final User param);
    Iterable<User> list();
    User findByLogin(@NonNull final String login);
}
