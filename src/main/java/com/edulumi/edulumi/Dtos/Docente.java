package com.edulumi.edulumi.Dtos;

import com.edulumi.edulumi.Beans.Curso;
import com.edulumi.edulumi.Beans.Usuario;

public class Docente extends Usuario {
    private Curso curso;

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
}
