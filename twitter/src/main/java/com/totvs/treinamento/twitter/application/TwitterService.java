package com.totvs.treinamento.twitter.application;

import com.totvs.treinamento.twitter.domain.twitter.Twitter;
import com.totvs.treinamento.twitter.domain.user.User;
import org.springframework.lang.NonNull;

public interface TwitterService {
    Twitter get(@NonNull Long id);
    Twitter save(@NonNull Twitter tweet);
    Iterable<Twitter> list();
    Iterable<Twitter> findByUser(@NonNull Long id);
    void deleteByOwner(@NonNull Long id, @NonNull Long user);
}
