package com.edulumi.edulumi.Controllers;

import com.edulumi.edulumi.Beans.Usuario;
import com.edulumi.edulumi.Daos.DaoDocente;
import com.edulumi.edulumi.Daos.DaoUsuario;
import com.edulumi.edulumi.Dtos.Decano;
import com.edulumi.edulumi.Dtos.Docente;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "DocentesServlet", value = "/DocentesServlet")
public class DocentesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        String action = request.getParameter("action") == null ? "list" : request.getParameter("action");

        RequestDispatcher view;
        DaoDocente daoDocente = new DaoDocente();
        DaoUsuario daoUsuario = new DaoUsuario();

        //Variables auxiliares:
        String idDocenteStr;
        int idDocente = 0;

        switch (action) {
            case "list":
                request.setAttribute("listaDocentes", daoDocente.getListaDocentes(((Decano)usuario).getFacultad().getIdFacultad()));
                view = request.getRequestDispatcher("decano/listDocente.jsp");
                view.forward(request, response);
                break;
            case "new":
                view = request.getRequestDispatcher("decano/newDocente.jsp");
                view.forward(request, response);
                break;
            case "edit":
                if (request.getParameter("id") != null) {
                    idDocenteStr = request.getParameter("id");
                    try {
                        idDocente = Integer.parseInt(idDocenteStr);
                        Usuario usuarioAux = daoUsuario.getUsuarioPorId(idDocente);

                        if (usuarioAux != null) {
                            request.setAttribute("idDocente",idDocenteStr);
                            request.setAttribute("nombreDocente",usuarioAux.getNombre());
                            view = request.getRequestDispatcher("decano/editDocente.jsp");
                            view.forward(request, response);
                        } else {
                            response.sendRedirect(request.getContextPath()+"/DocentesServlet");
                        }
                    } catch (NumberFormatException ex) {
                        response.sendRedirect(request.getContextPath()+"/DocentesServlet");
                    }
                } else {
                    response.sendRedirect(request.getContextPath()+"/DocentesServlet");
                }

                break;
            case "delete":
                if (request.getParameter("id") != null) {
                    idDocenteStr = request.getParameter("id");
                    try {
                        idDocente = Integer.parseInt(idDocenteStr);
                        Usuario usuarioAux = daoUsuario.getUsuarioPorId(idDocente);

                        if (usuarioAux != null && ((Docente)usuarioAux).getCurso().getIdCurso()==0) {
                            daoDocente.deleteDocente(idDocente);
                            session.setAttribute("msg","Docente eliminado exitosamente!!!");
                            response.sendRedirect(request.getContextPath()+"/DocentesServlet");
                        } else {
                            response.sendRedirect(request.getContextPath()+"/DocentesServlet");
                        }
                    } catch (NumberFormatException ex) {
                        response.sendRedirect(request.getContextPath()+"/DocentesServlet");
                    }
                } else {
                    response.sendRedirect(request.getContextPath()+"/DocentesServlet");
                }

                break;

            default:
                response.sendRedirect("DocentesServlet");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        String action = request.getParameter("action") == null ? "new" : request.getParameter("action");

        DaoUsuario daoUsuario = new DaoUsuario();
        DaoDocente daoDocente = new DaoDocente();

        switch (action){

            case "new":
                String nombre = request.getParameter("newNombre");
                String email = request.getParameter("newEmail");
                String password = request.getParameter("newPassword");

                if(nombre==null || email==null || password==null || nombre.isEmpty() || email.isEmpty() || password.isEmpty()){
                    session.setAttribute("error","Las credenciales no pueden estar vacías");
                    response.sendRedirect(request.getContextPath()+"/DocentesServlet?action=new");
                }else {
                    if(daoUsuario.verifyEmailRepetido(email)){
                        session.setAttribute("error","El correo electrónico ingresado ya existe en la plataforma");
                        response.sendRedirect(request.getContextPath()+"/DocentesServlet?action=new");
                    }else{
                        daoDocente.newDocente(nombre,email,password);
                        session.setAttribute("msg","Docente creado exitosamente!!!");
                        response.sendRedirect(request.getContextPath()+"/DocentesServlet");
                    }
                }

                break;

            case "edit":
                String idDocenteStr = request.getParameter("idDocente");
                String editNombre = request.getParameter("editNombre");

                if(editNombre==null  || editNombre.isEmpty()){
                    session.setAttribute("error","Las credenciales no pueden estar vacías");
                    response.sendRedirect(request.getContextPath()+"/DocentesServlet?action=edit&id="+idDocenteStr);
                }else {
                    if (idDocenteStr != null) {
                        int idDocenteAux = 0;
                        try {
                            idDocenteAux = Integer.parseInt(idDocenteStr);
                            Usuario usuarioAux = daoUsuario.getUsuarioPorId(idDocenteAux);

                            if (usuarioAux != null) {
                                daoDocente.editDocenteNombre(editNombre,idDocenteAux);
                                session.setAttribute("msg","Docente editado exitosamente!!!");
                                response.sendRedirect(request.getContextPath()+"/DocentesServlet");
                            } else {
                                response.sendRedirect(request.getContextPath()+"/DocentesServlet");
                            }
                        } catch (NumberFormatException ex) {
                            response.sendRedirect(request.getContextPath()+"/DocentesServlet");
                        }
                    } else {
                        response.sendRedirect(request.getContextPath()+"/DocentesServlet");
                    }
                }
                break;
        }
    }
}
