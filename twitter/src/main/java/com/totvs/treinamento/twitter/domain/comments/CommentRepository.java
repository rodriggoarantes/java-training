package com.totvs.treinamento.twitter.domain.comments;

import com.totvs.treinamento.twitter.domain.twitter.Twitter;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    Iterable<Comment> findByTwitterId(Long id);
}
