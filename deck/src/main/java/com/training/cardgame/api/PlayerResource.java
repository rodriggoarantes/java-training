package com.training.cardgame.api;

import com.training.cardgame.domain.player.Player;
import com.training.cardgame.domain.player.PlayerRepository;
import com.training.cardgame.infra.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("players")
public class PlayerResource {

    @Autowired
    private PlayerRepository repository;

    @GetMapping
    public Iterable<Player> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Player obter(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Jogador não encontrado"));
    }

    @PostMapping
    public Player inserir(@RequestBody Player param) {
        return repository.save(param);
    }

}
