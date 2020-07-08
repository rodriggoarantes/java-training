package com.totvs.treinamento.twitter.application.impl;

import com.totvs.treinamento.twitter.application.LikeService;
import com.totvs.treinamento.twitter.application.TwitterService;
import com.totvs.treinamento.twitter.application.UserService;
import com.totvs.treinamento.twitter.domain.likes.Like;
import com.totvs.treinamento.twitter.domain.likes.LikeRepository;
import com.totvs.treinamento.twitter.domain.twitter.Twitter;
import com.totvs.treinamento.twitter.domain.user.User;
import com.totvs.treinamento.twitter.infra.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeServiceImpl implements LikeService {

    private final LikeRepository repository;
    private final UserService userService;
    private final TwitterService twitterService;

    @Autowired
    public LikeServiceImpl(LikeRepository repository, UserService userService, TwitterService twitterService) {
        this.repository = repository;
        this.userService = userService;
        this.twitterService = twitterService;
    }

    @Override
    public Iterable<Like> findByTwitter(@NonNull Long id) {
        return repository.findByTwitterId(id);
    }

    @Override
    public Like like(@NonNull Long twitter, @NonNull Long user) {
        final boolean exists = repository.existsByTwitterIdAndUserId(twitter, user);
        if (exists) {
            return repository.findByTwitterIdAndUserId(twitter, user);
        }
        return repository.save(new Like(twitter, user));
    }

    @Override
    public void unlike(@NonNull Long twitterId, @NonNull Long userId) {
        final Twitter twitter = Optional.ofNullable(twitterService.get(twitterId))
                .orElseThrow(() -> new BusinessException("Twitter não encontrado"));
        final User owner = Optional.ofNullable(userService.find(userId))
                .orElseThrow(() -> new BusinessException("Usuário não encontrado"));;

        if (owner.getId().equals(twitter.getUser().getId())) {
            Optional.ofNullable(repository.findByTwitterIdAndUserId(twitterId, userId))
                    .ifPresent(repository::delete);
        } else {
            throw new BusinessException("Somente o proprietário pode alterar um like");
        }
    }

    @Override
    public boolean toggleLike(@NonNull Long twitter, @NonNull Long user) {
        final boolean exists = repository.existsByTwitterIdAndUserId(twitter, user);
        if (exists) {
            this.unlike(twitter, user);
        } else {
            this.like(twitter, user);
        }
        return !exists;
    }


}
