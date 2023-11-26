package com.edulumi.edulumi.Controllers;

import com.edulumi.edulumi.Beans.Usuario;
import com.edulumi.edulumi.Daos.DaoCurso;
import com.edulumi.edulumi.Daos.DaoDocente;
import com.edulumi.edulumi.Daos.DaoUsuario;
import com.edulumi.edulumi.Dtos.CursoDocente;
import com.edulumi.edulumi.Dtos.Decano;
import com.edulumi.edulumi.Dtos.Docente;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "CursosServlet", value = "/CursosServlet")
public class CursosServlet extends HttpServlet {
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
        DaoCurso daoCurso = new DaoCurso();

        //Variables auxiliares:
        String idCursoStr;
        int idCurso = 0;

        switch (action) {
            case "list":
                request.setAttribute("listaCursos", daoCurso.getListaCursos(((Decano)usuario).getFacultad().getIdFacultad()));
                view = request.getRequestDispatcher("decano/listCurso.jsp");
                view.forward(request, response);
                break;
            case "new":
                request.setAttribute("listaDocentesDisponibles",daoUsuario.getListaDocentesDisponibles());
                view = request.getRequestDispatcher("decano/newCurso.jsp");
                view.forward(request, response);
                break;
            case "edit":
                if (request.getParameter("id") != null) {
                    idCursoStr = request.getParameter("id");
                    try {
                        idCurso = Integer.parseInt(idCursoStr);
                        CursoDocente cursoAux = daoCurso.getCursoPorId(idCurso);
                        if (cursoAux != null) {
                            request.setAttribute("curso",cursoAux);
                            view = request.getRequestDispatcher("decano/editCurso.jsp");
                            view.forward(request, response);
                        } else {
                            response.sendRedirect(request.getContextPath()+"/CursosServlet");
                        }
                    } catch (NumberFormatException ex) {
                        response.sendRedirect(request.getContextPath()+"/CursosServlet");
                    }
                } else {
                    response.sendRedirect(request.getContextPath()+"/CursosServlet");
                }

                break;
            case "delete":
                if (request.getParameter("id") != null) {
                    idCursoStr = request.getParameter("id");
                    try {
                        idCurso = Integer.parseInt(idCursoStr);
                        CursoDocente cursoAux = daoCurso.getCursoPorId(idCurso);

                        if (cursoAux != null && daoCurso.verifyCursoNoTieneEvaluaciones(idCurso)) {
                            daoCurso.deleteCurso(idCurso);
                            session.setAttribute("msg","Curso eliminado exitosamente!!!");
                            response.sendRedirect(request.getContextPath()+"/CursosServlet");
                        } else {
                            session.setAttribute("msg","Curso no se puede eliminar pues posee evaluaciones registradas!!!");
                            response.sendRedirect(request.getContextPath()+"/CursosServlet");
                        }
                    } catch (NumberFormatException ex) {
                        response.sendRedirect(request.getContextPath()+"/CursosServlet");
                    }
                } else {
                    response.sendRedirect(request.getContextPath()+"/CursosServlet");
                }

                break;

            default:
                response.sendRedirect("CursosServlet");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        String action = request.getParameter("action") == null ? "new" : request.getParameter("action");

        DaoUsuario daoUsuario = new DaoUsuario();
        DaoDocente daoDocente = new DaoDocente();
        DaoCurso daoCurso = new DaoCurso();

        // Variables auxiliares:
        ArrayList<Usuario> listaDocentesDisponibles = daoUsuario.getListaDocentesDisponibles();
        Usuario docenteAux = new Usuario();
        int idCurso=0;
        int idDocente = 0;

        switch (action) {

            case "new":
                String codigo = request.getParameter("newCodigo");
                String nombre = request.getParameter("newNombre");
                String idDocenteStr = request.getParameter("newIdDocente");

                if (codigo == null || nombre == null || idDocenteStr == null || codigo.isEmpty() || nombre.isEmpty() || idDocenteStr.isEmpty()) {
                    session.setAttribute("error", "Las credenciales no pueden estar vacías");
                    response.sendRedirect(request.getContextPath() + "/CursosServlet?action=new");
                } else {

                    boolean validarDocente = true;
                    try {
                        idDocente = Integer.parseInt(idDocenteStr);
                        docenteAux = daoUsuario.getUsuarioPorId(idDocente);

                        if(docenteAux==null){
                            validarDocente = false;
                        }else{
                            validarDocente = false;
                            for (Usuario u : listaDocentesDisponibles) {
                                if (u.getIdUsuario() == docenteAux.getIdUsuario()) {
                                    validarDocente = true;
                                    break;
                                }
                            }
                        }

                    } catch (NumberFormatException ex) {
                        validarDocente = false;
                    }

                    if (!validarDocente) {
                        session.setAttribute("error", "El docente elegido no es válido");
                        response.sendRedirect(request.getContextPath() + "/CursosServlet?action=new");
                    } else {
                        daoCurso.newCurso(codigo, nombre, idDocente, ((Decano) usuario).getFacultad().getIdFacultad());
                        session.setAttribute("msg", "Curso creado exitosamente!!!");
                        response.sendRedirect(request.getContextPath() + "/CursosServlet");
                    }
                }

                break;

            case "edit":
                String idCursoStr = request.getParameter("idCurso");
                String editNombre = request.getParameter("editNombre");

                if(editNombre==null  || editNombre.isEmpty()){
                    session.setAttribute("error","El nombre no puede estar vacío");
                    response.sendRedirect(request.getContextPath()+"/CursosServlet?action=edit&id="+idCursoStr);
                }else {
                    if (idCursoStr != null) {
                        try {
                            idCurso = Integer.parseInt(idCursoStr);
                            CursoDocente cursoAux = daoCurso.getCursoPorId(idCurso);

                            if (cursoAux != null) {
                                daoCurso.editCursoNombre(editNombre,idCurso);
                                session.setAttribute("msg","Curso editado exitosamente!!!");
                                response.sendRedirect(request.getContextPath()+"/CursosServlet");
                            } else {
                                response.sendRedirect(request.getContextPath()+"/CursosServlet");
                            }
                        } catch (NumberFormatException ex) {
                            response.sendRedirect(request.getContextPath()+"/CursosServlet");
                        }
                    } else {
                        response.sendRedirect(request.getContextPath()+"/CursosServlet");
                    }
                }
                break;
        }
    }
}
