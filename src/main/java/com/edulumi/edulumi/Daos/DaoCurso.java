package com.edulumi.edulumi.Daos;

import com.edulumi.edulumi.Beans.Curso;
import com.edulumi.edulumi.Beans.Usuario;
import com.edulumi.edulumi.Dtos.CursoDocente;
import com.edulumi.edulumi.Dtos.Docente;

import java.sql.*;
import java.util.ArrayList;

public class DaoCurso extends DaoBase{

    public ArrayList<CursoDocente> getListaCursos(int idfacultad){

        String sql = "SELECT d.idusuario,c.* FROM curso c INNER JOIN curso_has_docente cd ON (cd.idcurso=c.idcurso) INNER JOIN usuario d ON (d.idusuario=cd.iddocente) WHERE c.idfacultad=?";

        ArrayList<CursoDocente> listaCursos = new ArrayList<>();
        DaoUsuario daoUsuario = new DaoUsuario();

        try (Connection conn = this.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, idfacultad);

            try (ResultSet rs = pstmt.executeQuery();) {
                while(rs.next()){
                    Usuario usuario = daoUsuario.getUsuarioPorId(rs.getInt(1));
                    CursoDocente curso = new CursoDocente();
                    curso.setIdCurso(rs.getInt(2));
                    curso.setCodigo(rs.getString(3));
                    curso.setNombre(rs.getString(4));
                    curso.setFechaRegistro(rs.getString(6));
                    curso.setFechaEdicion(rs.getString(7));
                    curso.setUsuario(usuario);
                    listaCursos.add(curso);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listaCursos;

    }

    public CursoDocente getCursoPorId(int idcurso){

        String sql = "SELECT d.idusuario,c.* FROM curso c INNER JOIN curso_has_docente cd ON (cd.idcurso=c.idcurso) INNER JOIN usuario d ON (d.idusuario=cd.iddocente) WHERE c.idcurso=?";

        CursoDocente curso = new CursoDocente();
        DaoUsuario daoUsuario = new DaoUsuario();

        try (Connection conn = this.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, idcurso);

            try (ResultSet rs = pstmt.executeQuery();) {
                if(rs.next()){
                    Usuario usuario = daoUsuario.getUsuarioPorId(rs.getInt(1));
                    curso.setIdCurso(rs.getInt(2));
                    curso.setCodigo(rs.getString(3));
                    curso.setNombre(rs.getString(4));
                    curso.setFechaRegistro(rs.getString(6));
                    curso.setFechaEdicion(rs.getString(7));
                    curso.setUsuario(usuario);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return curso;
    }

    public int getUltimoId(){

        int ultimoId = 0;
        String sql = "SELECT idcurso FROM curso ORDER BY idcurso DESC LIMIT 1";

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

    public void newCurso(String codigo, String nombre, int idDocente, int idFacultad){
        String sql = "INSERT INTO curso(idcurso,codigo,nombre,idfacultad,fecha_registro,fecha_edicion) VALUES (?,?,?,?,now(),now())";

        int idCurso = this.getUltimoId()+1;

        try (Connection conn = this.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, idCurso);
            pstmt.setString(2,codigo);
            pstmt.setString(3,nombre);
            pstmt.setInt(4,idFacultad);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        String sql2 = "INSERT INTO curso_has_docente(idcurso,iddocente) VALUES (?,?)";

        try (Connection conn2 = this.getConection();
             PreparedStatement pstmt2 = conn2.prepareStatement(sql2);) {
            pstmt2.setInt(1, idCurso);
            pstmt2.setInt(2,idDocente);
            pstmt2.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void editCursoNombre(String nombre, int idCurso){
        String sql = "UPDATE curso SET nombre=?,fecha_edicion=now() WHERE idCurso=?";

        try (Connection conn = this.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, nombre);
            pstmt.setInt(2,idCurso);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteCurso(int idCurso){
        String sql = "DELETE FROM curso WHERE idcurso = ?";
        try (Connection conn = this.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, idCurso);
            pstmt.executeUpdate();
        }catch (SQLException ex){
            ex.printStackTrace();
        }

        String sql2 = "DELETE FROM curso_has_docente WHERE idcurso = ?";
        try (Connection conn2 = this.getConection();
             PreparedStatement pstmt2 = conn2.prepareStatement(sql2);) {
            pstmt2.setInt(1, idCurso);
            pstmt2.executeUpdate();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public boolean verifyCursoNoTieneEvaluaciones(int idcurso){
        boolean validacion = true;

        String sql ="SELECT e.* FROM evaluaciones e INNER JOIN curso c ON (e.idcurso=c.idcurso) WHERE c.idcurso = ? LIMIT 1;";

        try(Connection conn = this.getConection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setInt(1,idcurso);

            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    validacion = false;
                }
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }

        return validacion;
    }

}
