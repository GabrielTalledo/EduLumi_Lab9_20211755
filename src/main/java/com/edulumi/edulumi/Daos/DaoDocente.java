package com.edulumi.edulumi.Daos;

import com.edulumi.edulumi.Beans.Usuario;
import com.edulumi.edulumi.Dtos.Docente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DaoDocente extends DaoBase{
    public ArrayList<Docente> getListaDocentes(int idfacultad){
        ArrayList<Docente> listaDocentes = new ArrayList<>();
        DaoUsuario daoUsuario = new DaoUsuario();

        String sql = "SELECT u.idusuario FROM usuario u LEFT JOIN curso_has_docente cd ON (cd.iddocente=u.idusuario) LEFT JOIN curso c ON (cd.idcurso=c.idcurso) LEFT JOIN facultad f ON (f.idfacultad=c.idfacultad) WHERE u.idrol=4 AND (f.idfacultad=? OR f.idfacultad is null);";

        try (Connection conn = this.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, idfacultad);

            try (ResultSet rs = pstmt.executeQuery();) {
                while(rs.next()){
                    Usuario usuario = daoUsuario.getUsuarioPorId(rs.getInt(1));
                    Docente docente = daoUsuario.getDocenteCurso(usuario);
                    listaDocentes.add(docente);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listaDocentes;
    }

    public void newDocente(String nombre, String email, String password){
        String sql = "INSERT INTO usuario(idusuario,nombre,correo,password,idrol,ultimo_ingreso,cantidad_ingresos,fecha_registro,fecha_edicion) VALUES (?,?,?,sha2(?,256),4,null,0,now(),now())";

        try (Connection conn = this.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, new DaoUsuario().getUltimoId()+1);
            pstmt.setString(2,nombre);
            pstmt.setString(3,email);
            pstmt.setString(4,password);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void editDocenteNombre(String nombre, int idDocente){
        String sql = "UPDATE usuario SET nombre=?,fecha_edicion=now() WHERE idusuario=?";

        try (Connection conn = this.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, nombre);
            pstmt.setInt(2,idDocente);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteDocente(int idDocente){
        String sql = "DELETE FROM usuario WHERE idusuario = ?";
        try (Connection conn = this.getConection();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, idDocente);
            pstmt.executeUpdate();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}
