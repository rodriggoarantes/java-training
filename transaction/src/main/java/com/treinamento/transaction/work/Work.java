package com.treinamento.transaction.work;

import org.springframework.data.annotation.Id;

public class Work {
    @Id
    private Long id;
    private String name;

    public Work() {
    }

    public Work(String name) {
        this.name = name;
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
}
