package com.training.cardgame.domain.game;

import com.training.cardgame.domain.player.Player;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

public class Game {

    @Id
    private Long id;
    private String nome;
    private LocalDateTime data;
    private LocalDateTime saved;
    private List<Player> players;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public LocalDateTime getSaved() {
        return saved;
    }

    public void setSaved(LocalDateTime saved) {
        this.saved = saved;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    @Override
    public String toString() {
        return "Game{" + "id=" + id + '}';
    }
}
