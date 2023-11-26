package com.edulumi.edulumi.Daos;

import com.edulumi.edulumi.Beans.Evaluacion;
import com.edulumi.edulumi.Beans.Semestre;
import com.edulumi.edulumi.Beans.Usuario;
import com.edulumi.edulumi.Dtos.CursoDocente;

import java.sql.*;
import java.util.ArrayList;

public class DaoEvaluacion extends DaoBase{

    public ArrayList<Evaluacion> getListaEvaluaciones(int idCurso,int idSemestre){

        String aux = idSemestre==0?"":" AND e.idsemestre="+idSemestre+" ";

        String sql = "SELECT e.*,s.* FROM evaluaciones e INNER JOIN semestre s ON (e.idsemestre=s.idsemestre) WHERE e.idcurso = ?"+aux+"ORDER BY e.fecha_registro DESC;";

        ArrayList<Evaluacion> listaEvaluaciones = new ArrayList<>();
        DaoCurso daoCurso = new DaoCurso();

        try (Connection conn = this.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, idCurso);

            try (ResultSet rs = pstmt.executeQuery();) {
                while(rs.next()){
                    CursoDocente curso = daoCurso.getCursoPorId(idCurso);
                    Evaluacion evaluacion = new Evaluacion();

                    evaluacion.setCurso(curso);

                    evaluacion.setIdevaluaciones(rs.getInt(1));
                    evaluacion.setNombreEstudiantes(rs.getString(2));
                    evaluacion.setCodigoEstudiantes(rs.getString(3));
                    evaluacion.setCorreoEstudiantes(rs.getString(4));
                    evaluacion.setNota(rs.getInt(5));
                    evaluacion.setFechaRegistro(rs.getString(8));
                    evaluacion.setFechaEdicion(rs.getString(9));

                    Semestre semestre = new Semestre();

                    semestre.setIdSemestre(rs.getInt(10));
                    semestre.setNombre(rs.getString(11));
                    semestre.setIdAdministrador(rs.getInt(12));
                    semestre.setHabilitado(rs.getBoolean(13));
                    semestre.setFechaRegistro(rs.getString(14));
                    semestre.setFechaEdicion(rs.getString(15));

                    evaluacion.setSemestre(semestre);

                    listaEvaluaciones.add(evaluacion);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listaEvaluaciones;
    }

    public int getUltimoId(){

        int ultimoId = 0;
        String sql = "SELECT idevaluaciones FROM evaluaciones ORDER BY idevaluaciones DESC LIMIT 1";

        try (Connection conn = this.getConection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                ultimoId = rs.getInt(1);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return ultimoId;
    }

    public Evaluacion getEvaluacionPorId(int idEvaluacion){

        String sql = "SELECT e.*,s.* FROM evaluaciones e INNER JOIN semestre s ON (e.idsemestre=s.idsemestre) WHERE e.idevaluaciones = ?;";

        Evaluacion evaluacion = new Evaluacion();
        DaoCurso daoCurso = new DaoCurso();

        try (Connection conn = this.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, idEvaluacion);

            try (ResultSet rs = pstmt.executeQuery();) {
                while(rs.next()){
                    CursoDocente curso = daoCurso.getCursoPorId(rs.getInt(6));

                    evaluacion.setCurso(curso);

                    evaluacion.setIdevaluaciones(rs.getInt(1));
                    evaluacion.setNombreEstudiantes(rs.getString(2));
                    evaluacion.setCodigoEstudiantes(rs.getString(3));
                    evaluacion.setCorreoEstudiantes(rs.getString(4));
                    evaluacion.setNota(rs.getInt(5));
                    evaluacion.setFechaRegistro(rs.getString(8));
                    evaluacion.setFechaEdicion(rs.getString(9));

                    Semestre semestre = new Semestre();

                    semestre.setIdSemestre(rs.getInt(10));
                    semestre.setNombre(rs.getString(11));
                    semestre.setIdAdministrador(rs.getInt(12));
                    semestre.setHabilitado(rs.getBoolean(13));
                    semestre.setFechaRegistro(rs.getString(14));
                    semestre.setFechaEdicion(rs.getString(15));

                    evaluacion.setSemestre(semestre);

                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return evaluacion;
    }

    public void newEvaluacion(String nombre, String codigo, String email, int nota, int idCurso, int idSemestre){
        String sql = "INSERT INTO evaluaciones(idevaluaciones,nombre_estudiantes,codigo_estudiantes,correo_estudiantes,nota,idcurso,idsemestre,fecha_registro,fecha_edicion) VALUES (?,?,?,?,?,?,?,now(),now())";

        int idEvaluacion = this.getUltimoId()+1;

        try (Connection conn = this.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, idEvaluacion);
            pstmt.setString(2,nombre);
            pstmt.setString(3,codigo);
            pstmt.setString(4,email);
            pstmt.setInt(5,nota);
            pstmt.setInt(6,idCurso);
            pstmt.setInt(7,idSemestre);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void editEvaluacion(String nombre, String codigo, String email, int nota, int idEvaluacion){
        String sql = "UPDATE evaluaciones SET nombre_estudiantes=?,codigo_estudiantes=?,correo_estudiantes=?,nota=?,fecha_edicion=now() WHERE idevaluaciones=?";

        try (Connection conn = this.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, codigo);
            pstmt.setString(3, email);
            pstmt.setInt(4,nota);
            pstmt.setInt(5,idEvaluacion);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteEvaluacion(int idEvaluacion){
        String sql = "DELETE FROM evaluaciones WHERE idevaluaciones = ?";
        try (Connection conn = this.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, idEvaluacion);
            pstmt.executeUpdate();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

}
