package com.edulumi.edulumi.Dtos;

import com.edulumi.edulumi.Beans.Facultad;
import com.edulumi.edulumi.Beans.Usuario;

public class Decano extends Usuario {
    private Facultad facultad;

    public Facultad getFacultad() {
        return facultad;
    }

    public void setFacultad(Facultad facultad) {
        this.facultad = facultad;
    }
}
