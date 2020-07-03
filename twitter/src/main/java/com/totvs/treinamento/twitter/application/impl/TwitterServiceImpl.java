package com.totvs.treinamento.twitter.application.impl;

import com.totvs.treinamento.twitter.application.TwitterService;
import com.totvs.treinamento.twitter.domain.twitter.Twitter;
import com.totvs.treinamento.twitter.domain.twitter.TwitterRepository;
import com.totvs.treinamento.twitter.infra.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class TwitterServiceImpl implements TwitterService {

    private final TwitterRepository repository;

    @Autowired
    public TwitterServiceImpl(TwitterRepository repository) {
        this.repository = repository;
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
}
