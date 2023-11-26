package com.edulumi.edulumi.Controllers;

import com.edulumi.edulumi.Beans.Usuario;
import com.edulumi.edulumi.Daos.DaoUsuario;
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

        if (email == null || password == null) {
            request.setAttribute("error", "Credenciales no pueden ser vacías");
            request.setAttribute("email",email);
            RequestDispatcher view = request.getRequestDispatcher("login.jsp");
            view.forward(request, response);
        }else{
            DaoUsuario daoUsuario = new DaoUsuario();
            Usuario usuario = daoUsuario.getUsuarioLogIn(email, password);
            if (usuario != null) {
                daoUsuario.refreshUsuario(usuario.getIdUsuario());
                HttpSession session = request.getSession();
                session.setAttribute("usuario", usuario);
                session.setMaxInactiveInterval(5 * 60); // 5 minutos

                if(usuario.getRol().getNombre().equals("Decano")){
                    System.out.println("uwu");
                    response.sendRedirect(request.getContextPath() + "/CursosServlet");
                }else{
                    System.out.println("owo");
                    response.sendRedirect(request.getContextPath() + "/EvaluacionesServlet");
                }

            } else {
                request.setAttribute("error", "Credenciales incorrectas");
                request.setAttribute("email",email);
                RequestDispatcher view = request.getRequestDispatcher("login.jsp");
                view.forward(request, response);
            }
        }
    }
}
