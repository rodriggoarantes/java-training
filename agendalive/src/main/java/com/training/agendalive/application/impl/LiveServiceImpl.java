package com.training.agendalive.application.impl;

import com.training.agendalive.application.LiveService;
import com.training.agendalive.domain.autor.AutorRepository;
import com.training.agendalive.domain.live.Live;
import com.training.agendalive.domain.live.LiveRepository;
import com.training.agendalive.infra.Logger;
import com.training.agendalive.infra.exception.NotFoundException;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LiveServiceImpl implements Logger, LiveService {

    private final LiveRepository repository;
    private final AutorRepository autorRepository;

    @Autowired
    public LiveServiceImpl(LiveRepository repository,
                           AutorRepository autorRepository) {
        this.repository = repository;
        this.autorRepository = autorRepository;
    }

    @Override
    public Live obter(@NonNull Long id) {
        log("obter: " + id);
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Usuário não encontrado com ID %s", id)));
    }

    @Override
    public Live inserir(@NonNull Live live) {
        log("obter: " + live);
        validate(live);

        Optional.ofNullable(live.getAutor().getId()).ifPresent(id -> {
            autorRepository.findById(id).ifPresent(live::setAutor);
        });
        return repository.save(live);
    }

    @Override
    public Live alterar(@NonNull Live live) {
        validate(live);
        Validate.notNull(live.getId(), "Dados da Live incompleto");
        return repository.save(live);
    }

    @Override
    public void deletar(@NonNull Long id) {
        repository.deleteById(id);
    }

    @Override
    public Iterable<Live> listar() {
        return repository.findAll();
    }

    @Override
    public List<Live> pesquisar(String nome) {
        return repository.findByNomeContains(nome);
    }

    private static void validate(Live obj) {
        Validate.notNull(obj, "Dados da live não informado");
        Validate.notNull(obj.getNome(), "Nome da Live não informado");
        Validate.notNull(obj.getVideo(), "Dados do video da Live não informado");
        Validate.notNull(obj.getVideo().getLink(), "Link do video da Live não informado");
    }
}
