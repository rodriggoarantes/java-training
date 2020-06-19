package com.training.agendalive.api;

import com.training.agendalive.domain.autor.Autor;
import com.training.agendalive.domain.autor.AutorRepository;
import com.training.agendalive.domain.live.Live;
import com.training.agendalive.infra.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("authors")
public class AutorResource {

    @Autowired
    private AutorRepository repository;

    @GetMapping
    public Iterable<Autor> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Autor obter(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Autor nao encontrado"));
    }

    @PostMapping
    public Autor inserir(@RequestBody Autor param) {
        return repository.save(param);
    }

    @PutMapping
    public Autor alterar(@RequestBody Autor param) {
        return repository.save(param);
    }
}
