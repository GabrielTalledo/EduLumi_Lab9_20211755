package com.edulumi.edulumi.Daos;

import com.edulumi.edulumi.Beans.Evaluacion;
import com.edulumi.edulumi.Beans.Semestre;
import com.edulumi.edulumi.Dtos.CursoDocente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DaoSemestre extends DaoBase{

    public ArrayList<Semestre> getListaSemestres(){
        String sql = "SELECT e.* FROM semestre e;";

        ArrayList<Semestre> listaSemestres = new ArrayList<>();

        try (Connection conn = this.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            try (ResultSet rs = pstmt.executeQuery();) {
                while(rs.next()){
                    Semestre semestre = new Semestre();

                    semestre.setIdSemestre(rs.getInt(1));
                    semestre.setNombre(rs.getString(2));
                    semestre.setIdAdministrador(rs.getInt(3));
                    semestre.setHabilitado(rs.getBoolean(4));
                    semestre.setFechaRegistro(rs.getString(5));
                    semestre.setFechaEdicion(rs.getString(6));

                    listaSemestres.add(semestre);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listaSemestres;
    }

    public int getUltimoSemestreHabilitado(){
        String sql = "SELECT idsemestre FROM semestre WHERE habilitado=true ORDER BY fecha_edicion DESC LIMIT 1;";


        int idSemestre = 0;

        try (Connection conn = this.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            try (ResultSet rs = pstmt.executeQuery();) {
                if(rs.next()){
                    idSemestre = rs.getInt(1);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return idSemestre;
    }
}
