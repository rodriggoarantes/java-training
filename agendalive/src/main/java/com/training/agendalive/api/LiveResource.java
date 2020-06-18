package com.training.agendalive.api;

import com.training.agendalive.application.LiveService;
import com.training.agendalive.domain.live.Live;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"lives", "schedules"})
public class LiveResource {

    @Autowired
    private LiveService service;

    @GetMapping
    public Iterable<Live> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Live obter(@PathVariable Long id) {
        return service.obter(id);
    }

    @PostMapping
    public Live inserir(@RequestBody Live live) {
        return service.inserir(live);
    }

    @PutMapping
    public Live alterar(@RequestBody Live param) {
        return service.alterar(param);
    }
}
