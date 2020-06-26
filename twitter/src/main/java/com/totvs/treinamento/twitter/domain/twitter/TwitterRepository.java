package com.totvs.treinamento.twitter.domain.twitter;

import org.springframework.data.repository.CrudRepository;

public interface TwitterRepository extends CrudRepository<Twitter, Long> {
    Iterable<Twitter> findByUserId(Long id);
}
