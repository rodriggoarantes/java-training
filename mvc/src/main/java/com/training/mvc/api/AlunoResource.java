package com.training.mvc.api;

import com.training.mvc.application.AlunoService;
import com.training.mvc.application.impl.AlunoServiceImpl;
import com.training.mvc.infra.Logger;

public class AlunoResource implements Logger {

    private final AlunoService service;

    public AlunoResource() {
        this.service = new AlunoServiceImpl();
    }

    public boolean print() {
        this.log("print");
        final String mensagem = service.build();
        System.out.println(mensagem);
        return true;
    }
}
