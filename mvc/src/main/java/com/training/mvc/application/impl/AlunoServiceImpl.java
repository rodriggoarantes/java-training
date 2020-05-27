package com.training.mvc.application.impl;

import com.training.mvc.application.AlunoService;
import com.training.mvc.domain.AlunoRepository;
import com.training.mvc.infra.Logger;

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
