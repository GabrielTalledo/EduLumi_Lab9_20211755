package com.edulumi.edulumi.Daos;

import com.edulumi.edulumi.Beans.Rol;
import com.edulumi.edulumi.Beans.Usuario;

import java.sql.*;

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

        String sql = "SELECT u.idusuario FROM usuario u WHERE correo = ? AND password = ?";

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
        usuario.setFechaEdicion(rs.getString(8));
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
}
