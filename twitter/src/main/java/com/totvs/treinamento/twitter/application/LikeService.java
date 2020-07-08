package com.totvs.treinamento.twitter.application;

import com.totvs.treinamento.twitter.domain.likes.Like;
import com.totvs.treinamento.twitter.domain.twitter.Twitter;
import org.springframework.lang.NonNull;

public interface LikeService {
    Iterable<Like> findByTwitter(@NonNull Long twitter);
    Like like(@NonNull Long twitter, @NonNull Long user);
    void unlike(@NonNull Long twitter, @NonNull Long user);
    boolean toggleLike(@NonNull Long twitter, @NonNull Long user);

}
