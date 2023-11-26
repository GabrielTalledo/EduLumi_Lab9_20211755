package com.edulumi.edulumi.Controllers;

import com.edulumi.edulumi.Beans.Usuario;
import com.edulumi.edulumi.Daos.DaoUsuario;
import com.edulumi.edulumi.Dtos.Decano;
import com.edulumi.edulumi.Dtos.Docente;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action") == null ? "login" : request.getParameter("action");
        HttpSession session = request.getSession();

        switch (action) {
            case "login":
                request.getRequestDispatcher("login.jsp").forward(request,response);
                break;
            case "logout":
                session.invalidate();
                response.sendRedirect(request.getContextPath());
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("inputEmail");
        String password = request.getParameter("inputPassword");
        HttpSession session = request.getSession();

        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            session.setAttribute("error", "Credenciales no pueden ser vacías");
            session.setAttribute("email",email);
            response.sendRedirect(request.getContextPath());
        }else{
            DaoUsuario daoUsuario = new DaoUsuario();
            Usuario usuario = daoUsuario.getUsuarioLogIn(email, password);
            if (usuario != null) {
                daoUsuario.refreshUsuario(usuario.getIdUsuario());
                session.setMaxInactiveInterval(5 * 60);

                if(usuario.getRol().getNombre().equals("Decano")){
                    Decano decano = daoUsuario.getDecanoFacultad(usuario);
                    session.setAttribute("usuario", decano);
                    response.sendRedirect(request.getContextPath() + "/CursosServlet");
                }else{
                    Docente docente = daoUsuario.getDocenteCurso(usuario);
                    session.setAttribute("usuario", docente);
                    response.sendRedirect(request.getContextPath() + "/EvaluacionesServlet");
                }

            } else {
                session.setAttribute("error", "Credenciales incorrectas");
                session.setAttribute("email",email);
                response.sendRedirect(request.getContextPath());
            }
        }
    }
}
