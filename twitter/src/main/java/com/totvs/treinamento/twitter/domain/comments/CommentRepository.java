package com.totvs.treinamento.twitter.domain.comments;

import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    Iterable<Comment> findByTwitterId(Long id);
    void deleteByTwitterId(Long twitter);
}
