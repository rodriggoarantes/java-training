package com.totvs.treinamento.twitter.application;

import com.totvs.treinamento.twitter.domain.twitter.Twitter;
import org.springframework.lang.NonNull;

public interface TwitterService {
    Twitter get(@NonNull Long id);
    Twitter save(@NonNull Twitter tweet);
    Iterable<Twitter> list();
    Iterable<Twitter> findByUser(@NonNull Long id);
}
