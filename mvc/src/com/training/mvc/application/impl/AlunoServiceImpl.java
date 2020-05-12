package com.training.mvc.application.impl;

import com.training.mvc.application.AlunoService;
import com.training.mvc.application.Logger;
import com.training.mvc.domain.AlunoRepository;

public class AlunoServiceImpl implements AlunoService, Logger {

    private final AlunoRepository repository;

    public AlunoServiceImpl() {
        this.repository = new AlunoRepository();
    }

    @Override
    public String build() {
        this.log("build");
        return String.format("Olá %s", repository.findAluno());
    }
}
