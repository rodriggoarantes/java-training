package com.totvs.treinamento.twitter.domain.likes;

import org.springframework.data.repository.CrudRepository;

public interface LikeRepository extends CrudRepository<Like, Long> {
    Iterable<Like> findByTwitterId(Long id);
    Like findByTwitterIdAndUserId(Long twitter, Long user);
    boolean existsByTwitterIdAndUserId(Long twitter, Long user);
    void deleteByTwitterId(Long twitter);
}
