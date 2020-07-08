package com.totvs.treinamento.twitter.application;

import com.totvs.treinamento.twitter.domain.comments.Comment;
import com.totvs.treinamento.twitter.domain.user.User;
import org.springframework.lang.NonNull;

public interface CommentService {
    Iterable<Comment> findByTwitter(@NonNull Long twitterId);
    Iterable<Comment> list();
    Comment get(@NonNull Long id);
    Comment save(@NonNull final Comment comment);
    void deleteByOwner(@NonNull final Long id, @NonNull final Long userId);
}
