package com.treinamento.transaction.user.domain;


import org.springframework.data.annotation.Id;

public class User {

    @Id
    private Long id;
    private String name;
    private String login;

    public User() {}

    public User(String name, String login) {
        this.name = name;
        this.login = login;
    }

    public User(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
