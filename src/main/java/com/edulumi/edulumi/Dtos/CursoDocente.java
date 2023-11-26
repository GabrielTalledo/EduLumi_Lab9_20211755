package com.edulumi.edulumi.Dtos;

import com.edulumi.edulumi.Beans.Curso;
import com.edulumi.edulumi.Beans.Usuario;

public class CursoDocente extends Curso {
    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
