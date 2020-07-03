package com.totvs.treinamento.twitter.application;

import com.totvs.treinamento.twitter.domain.user.User;
import org.springframework.lang.NonNull;

public interface UserService {
    User find(@NonNull final Long id);
    User insert(@NonNull final User param);
    Iterable<User> list();
    User findByLogin(@NonNull final String login);
}
