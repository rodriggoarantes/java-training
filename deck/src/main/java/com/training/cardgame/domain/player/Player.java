package com.training.cardgame.domain.player;

import com.training.cardgame.infra.SavedTime;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public class Player implements SavedTime {
    @Id
    private Long id;
    private String nome;
    private String login;
    private LocalDateTime saved;

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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public LocalDateTime getSaved() {
        return saved;
    }

    private void setSaved(LocalDateTime saved) {
        this.saved = saved;
    }

    @Override
    public void time() {
        setSaved(LocalDateTime.now());
    }
}
