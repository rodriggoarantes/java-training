package com.training.cardgame.application.impl;

import com.training.cardgame.application.GameService;
import com.training.cardgame.domain.game.Game;
import com.training.cardgame.domain.game.GamePlayerRepository;
import com.training.cardgame.domain.game.GameRepository;
import com.training.cardgame.infra.Logger;
import com.training.cardgame.infra.exception.NotFoundException;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
public class GameServiceImpl implements Logger, GameService {

    private final GameRepository repository;
    private final GamePlayerRepository gamePlayerRepository;

    @Autowired
    public GameServiceImpl(GameRepository repository,
                           GamePlayerRepository gamePlayerRepository) {
        this.repository = repository;
        this.gamePlayerRepository = gamePlayerRepository;
    }

    @Override
    public Game obter(@NonNull Long id) {
        log("obter: " + id);
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Game não encontrado com ID %s", id)));
    }

    @Override
    public Game inserir(@NonNull Game game) {
        log("inserir: " + game);
        validate(game);
        game.setData(LocalDateTime.now());

        repository.save(game);

        if (!CollectionUtils.isEmpty(game.getPlayers())) {
            game.getPlayers().forEach(player ->
                    gamePlayerRepository.adicionarJogador(game.getId(), player));
        }

        return game;
    }

    @Override
    public Game alterar(@NonNull Game param) {
        log("alterar: " + param);
        validate(param);
        Validate.notNull(param.getId(), "Dados do Game incompleto");
        return repository.save(param);
    }

    @Override
    public void deletar(@NonNull Long id) {
        repository.deleteById(id);
    }

    @Override
    public Iterable<Game> listar() {
        return repository.findAll();
    }

    private static void validate(Game obj) {
        Validate.notNull(obj, "Dados do Game não informado");
        Validate.notNull(obj.getNome(), "Nome do Game não informado");
    }
}
