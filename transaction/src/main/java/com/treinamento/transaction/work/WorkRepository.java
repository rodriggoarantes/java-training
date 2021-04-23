package com.treinamento.transaction.work;

import org.springframework.data.repository.CrudRepository;

public interface WorkRepository extends CrudRepository<Work, Long> {
    boolean existsByName(String name);
}
