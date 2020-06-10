package com.training.agendalive.domain.live;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LiveRepository extends CrudRepository<Live, Long> {
    Live findByNome(String nome);
    List<Live> findByNomeContains(String nome);
}
