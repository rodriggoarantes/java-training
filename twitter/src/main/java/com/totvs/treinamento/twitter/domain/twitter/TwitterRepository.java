package com.totvs.treinamento.twitter.domain.twitter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TwitterRepository extends CrudRepository<Twitter, Long> {
    Iterable<Twitter> findByUserId(Long id);

    @Query("select t, count(l) as likes, count(c) as comments from Twitter t " +
            "left join Like l on t.id = l.twitter.id " +
            "left join Comment c on t.id = c.twitter.id " +
            "group by t.id " +
            "order by t.date ")
    Iterable<Twitter> list();

    Iterable<Twitter> findAll(Sort sort);

    Page<Twitter> findAll(Pageable pageable);
}
