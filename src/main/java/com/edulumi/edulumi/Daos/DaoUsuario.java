package com.edulumi.edulumi.Daos;

import com.edulumi.edulumi.Beans.Curso;
import com.edulumi.edulumi.Beans.Facultad;
import com.edulumi.edulumi.Beans.Rol;
import com.edulumi.edulumi.Beans.Usuario;
import com.edulumi.edulumi.Dtos.Decano;
import com.edulumi.edulumi.Dtos.Docente;

import java.sql.*;
import java.util.ArrayList;

public class DaoUsuario extends DaoBase{

    public int getUltimoId(){

        int ultimoId = 0;
        String sql = "SELECT idusuario FROM usuario ORDER BY idusuario DESC LIMIT 1";

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

    public Usuario getUsuarioLogIn(String email, String password){

        Usuario usuario = null;

        String sql = "SELECT u.idusuario FROM usuario u WHERE correo = ? AND password = sha2(?,256)";

        try (Connection conn = this.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery();) {
                if (rs.next()) {
                    int idusuario = rs.getInt(1);
                    usuario = this.getUsuarioPorId(idusuario);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return usuario;
    }

    public Usuario getUsuarioPorId(int idusuario){
        Usuario usuario = null;

        String sql = "SELECT * FROM usuario u INNER JOIN rol r ON (u.idrol=r.idrol) WHERE u.idusuario = ?";

        try (Connection conn = this.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, idusuario);

            try (ResultSet rs = pstmt.executeQuery();) {
                if (rs.next()) {
                    usuario = this.fillUsuario(rs);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return usuario;
    }

    public Usuario fillUsuario(ResultSet rs) throws SQLException{
        Usuario usuario = new Usuario();
        Rol rol = new Rol();
        usuario.setIdUsuario(rs.getInt(1));
        usuario.setNombre(rs.getString(2));
        usuario.setCorreo(rs.getString(3));

        rol.setIdRol(rs.getInt(10));
        rol.setNombre(rs.getString(11));
        usuario.setRol(rol);

        usuario.setUltimoIngreso(rs.getString(6));
        usuario.setCantidadIngresos(rs.getInt(7));
        usuario.setFechaRegistro(rs.getString(8));
        usuario.setFechaEdicion(rs.getString(9));

        return usuario;
    }

    public void refreshUsuario(int idusuario){

        String sql = "UPDATE usuario SET ultimo_ingreso=now(), cantidad_ingresos=cantidad_ingresos+1 WHERE idusuario = ?";

        try (Connection conn = this.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, idusuario);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Métodos para el Decano:

    public Decano getDecanoFacultad(Usuario usuario){
        Decano decano = new Decano();
        Facultad facultad = new Facultad();

        String sql = "SELECT f.* FROM facultad f INNER JOIN facultad_has_decano fc ON (f.idfacultad=fc.idfacultad) WHERE fc.iddecano=?";

        try (Connection conn = this.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, usuario.getIdUsuario());

            try (ResultSet rs = pstmt.executeQuery();) {
                if (rs.next()) {
                    facultad.setIdFacultad(rs.getInt(1));
                    facultad.setNombre(rs.getString(2));
                    facultad.setIdUniversidad(rs.getInt(3));
                    facultad.setFechaRegistro(rs.getString(4));
                    facultad.setFechaEdicion(rs.getString(5));
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        decano.setIdUsuario(usuario.getIdUsuario());
        decano.setNombre(usuario.getNombre());
        decano.setCorreo(usuario.getCorreo());
        decano.setRol(usuario.getRol());
        decano.setUltimoIngreso(usuario.getUltimoIngreso());
        decano.setCantidadIngresos(usuario.getCantidadIngresos());
        decano.setFechaRegistro(usuario.getFechaRegistro());
        decano.setFechaEdicion(usuario.getFechaEdicion());
        decano.setFacultad(facultad);
        return decano;
    }

    // Métodos para el Docente:

    public Docente getDocenteCurso(Usuario usuario){
        Docente docente = new Docente();
        Curso curso = new Curso();
        Facultad facultad = new Facultad();

        String sql = "SELECT c.*,f.* FROM curso c INNER JOIN facultad f ON (f.idfacultad=c.idfacultad) LEFT JOIN curso_has_docente cd ON (cd.idcurso=c.idcurso) WHERE cd.iddocente=?";

        try (Connection conn = this.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, usuario.getIdUsuario());

            try (ResultSet rs = pstmt.executeQuery();) {
                if (rs.next()) {
                    curso.setIdCurso(rs.getInt(1));
                    curso.setCodigo(rs.getString(2));
                    curso.setNombre(rs.getString(3));
                    curso.setFechaRegistro(rs.getString(5));
                    curso.setFechaEdicion(rs.getString(6));

                    facultad.setIdFacultad(rs.getInt(7));
                    facultad.setNombre(rs.getString(8));
                    facultad.setIdUniversidad(rs.getInt(9));
                    facultad.setFechaRegistro(rs.getString(10));
                    facultad.setFechaEdicion(rs.getString(11));

                    curso.setFacultad(facultad);

                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        docente.setIdUsuario(usuario.getIdUsuario());
        docente.setNombre(usuario.getNombre());
        docente.setCorreo(usuario.getCorreo());
        docente.setRol(usuario.getRol());
        docente.setUltimoIngreso(usuario.getUltimoIngreso());
        docente.setCantidadIngresos(usuario.getCantidadIngresos());
        docente.setFechaRegistro(usuario.getFechaRegistro());
        docente.setFechaEdicion(usuario.getFechaEdicion());
        docente.setCurso(curso);
        return docente;
    }

    public ArrayList<Usuario> getListaDocentesDisponibles(){

        ArrayList<Usuario> listaDocentesDisponibles = new ArrayList<>();
        DaoUsuario daoUsuario = new DaoUsuario();

        String sql = "SELECT d.idusuario FROM usuario d LEFT JOIN curso_has_docente cd ON (cd.iddocente=d.idusuario) WHERE cd.idcurso is null AND d.idrol=4;";

        try (Connection conn = this.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {

            try (ResultSet rs = pstmt.executeQuery();) {
                while(rs.next()){
                    Usuario usuario = daoUsuario.getUsuarioPorId(rs.getInt(1));
                    listaDocentesDisponibles.add(usuario);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listaDocentesDisponibles;
    }

    public boolean verifyEmailRepetido(String email){

        String sql = "SELECT correo FROM usuario WHERE correo=?";
        boolean validacion = false;

        try (Connection conn = this.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery();) {
                if (rs.next()) {
                    validacion=true;
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return validacion;
    }

}
