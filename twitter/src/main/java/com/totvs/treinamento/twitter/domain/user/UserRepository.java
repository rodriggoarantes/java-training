package com.totvs.treinamento.twitter.domain.user;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByLogin(String login);
    boolean existsByLogin(String login);
}
