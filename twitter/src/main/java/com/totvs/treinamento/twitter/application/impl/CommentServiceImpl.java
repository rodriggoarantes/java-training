package com.totvs.treinamento.twitter.application.impl;

import com.totvs.treinamento.twitter.application.CommentService;
import com.totvs.treinamento.twitter.application.UserService;
import com.totvs.treinamento.twitter.domain.comments.Comment;
import com.totvs.treinamento.twitter.domain.comments.CommentRepository;
import com.totvs.treinamento.twitter.domain.user.User;
import com.totvs.treinamento.twitter.infra.exception.BusinessException;
import com.totvs.treinamento.twitter.infra.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;
    private final UserService userService;

    @Autowired
    public CommentServiceImpl(CommentRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    @Override
    public Comment get(@NonNull Long id) {
        return repository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public Comment save(@NonNull Comment param) {
        return repository.save(param);
    }

    @Override
    public Iterable<Comment> list() {
        return repository.findAll();
    }

    @Override
    public Iterable<Comment> findByTwitter(@NonNull Long id) {
        return repository.findByTwitterId(id);
    }

    @Override
    public void deleteByOwner(@NonNull Long id, @NonNull Long user) {
        final Comment comment = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Comentário não encontrado"));
        final User owner = userService.find(user);

        if (owner.getId().equals(comment.getUser().getId())) {
            repository.delete(comment);
        } else {
            throw new BusinessException("Somente o proprietário pode deletar o comentário");
        }
    }


}
