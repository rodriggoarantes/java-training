package com.training.mvc.domain;

import com.training.mvc.infra.Logger;

public class AlunoRepository implements Logger {

    public String findAluno() {
        this.log("findAluno");
        return "Rodrigo Arantes";
    }
}
