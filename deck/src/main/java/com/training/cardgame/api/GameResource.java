package com.training.cardgame.api;

import com.training.cardgame.application.GameService;
import com.training.cardgame.domain.game.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping(value={"games", "rooms"})
public class GameResource {

    @Autowired
    private GameService service;

    @GetMapping
    public Iterable<Game> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Game obter(@PathVariable Long id) {
        return service.obter(id);
    }

    @PostMapping
    public Game inserir(@RequestBody Game game) {
        return service.inserir(game);
    }

    @PostMapping("/players/{id}")
    public Game iniciarNovo(@RequestBody Game game, @PathVariable Long id) {
        game.setPlayers(Collections.singletonList(id));
        return service.inserir(game);
    }

}
