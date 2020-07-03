package com.totvs.treinamento.twitter.application.impl;

import com.totvs.treinamento.twitter.application.TwitterService;
import com.totvs.treinamento.twitter.application.UserService;
import com.totvs.treinamento.twitter.domain.twitter.Twitter;
import com.totvs.treinamento.twitter.domain.twitter.TwitterRepository;
import com.totvs.treinamento.twitter.domain.user.User;
import com.totvs.treinamento.twitter.infra.exception.BusinessException;
import com.totvs.treinamento.twitter.infra.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TwitterServiceImpl implements TwitterService {

    private final TwitterRepository repository;
    private final UserService userService;

    @Autowired
    public TwitterServiceImpl(TwitterRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    @Override
    public Twitter get(@NonNull Long id) {
        return repository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public Twitter save(@NonNull Twitter param) {
        return repository.save(param);
    }

    @Override
    public Iterable<Twitter> list() {
        return repository.findAll();
    }

    @Override
    public Iterable<Twitter> findByUser(@NonNull Long id) {
        return repository.findByUserId(id);
    }

    @Override
    public void deleteByOwner(@NonNull Long id, @NonNull Long user) {
        final Twitter twitter = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Twitter não encontrado"));
        final User owner = userService.find(user);

        if (owner.getId().equals(twitter.getUser().getId())) {
            repository.delete(twitter);
        } else {
            throw new BusinessException("Somente o proprietario pode deletar a postagem");
        }
    }


}
