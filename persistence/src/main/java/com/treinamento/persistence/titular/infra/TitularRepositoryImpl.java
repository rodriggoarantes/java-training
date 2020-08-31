package com.treinamento.persistence.titular.infra;

import com.treinamento.persistence.titular.domain.Titular;
import com.treinamento.persistence.titular.domain.TitularId;
import com.treinamento.persistence.titular.domain.TitularRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TitularRepositoryImpl implements TitularRepository {

    private final TitularCrudRepository crudRepository;

    @Autowired
    public TitularRepositoryImpl(TitularCrudRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public List<Titular> findAll() {
        final List<Titular> list = new ArrayList<>();
        crudRepository.findAll().forEach(list::add);
        return list;
    }

    @Override
    public Titular save(Titular entity) {
        return crudRepository.save(entity);
    }

    @Override
    public Titular findBy(TitularId id) {
        return crudRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nenhuma registro encontrado"));
    }
}
